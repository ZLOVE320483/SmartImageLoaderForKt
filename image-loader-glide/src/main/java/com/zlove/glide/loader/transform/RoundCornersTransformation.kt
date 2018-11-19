package com.zlove.glide.loader.transform

import android.graphics.*
import android.os.Build
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.util.Synthetic
import com.zlove.core.CircleOptions
import com.zlove.glide.loader.SmartImageViewAttrs
import java.security.MessageDigest
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class RoundCornersTransformation: BitmapTransformation {

    companion object {

        private val ID = CircleTransformation::class.java.name
        private val ID_BYTES = ID.toByteArray(Key.CHARSET)
        const val PAINT_FLAGS = Paint.DITHER_FLAG or Paint.FILTER_BITMAP_FLAG
        private const val CIRCLE_CROP_PAINT_FLAGS = PAINT_FLAGS or Paint.ANTI_ALIAS_FLAG

        // 画形状的Paint
        private val CIRCLE_CROP_SHAPE_PAINT = Paint(CIRCLE_CROP_PAINT_FLAGS)
        // 画边框的Paint
        private val CIRCLE_CROP_BORDER_SHAPE_PAINT = Paint(CIRCLE_CROP_PAINT_FLAGS)
        // 画Bitmap的Paint
        private val CIRCLE_CROP_BITMAP_PAINT: Paint

        private val MODELS_REQUIRING_BITMAP_LOCK = HashSet(
                Arrays.asList(
                        // Moto X gen 2
                        "XT1085",
                        "XT1092",
                        "XT1093",
                        "XT1094",
                        "XT1095",
                        "XT1096",
                        "XT1097",
                        "XT1098",
                        // Moto G gen 1
                        "XT1031",
                        "XT1028",
                        "XT937C",
                        "XT1032",
                        "XT1008",
                        "XT1033",
                        "XT1035",
                        "XT1034",
                        "XT939G",
                        "XT1039",
                        "XT1040",
                        "XT1042",
                        "XT1045",
                        // Moto G gen 2
                        "XT1063",
                        "XT1064",
                        "XT1068",
                        "XT1069",
                        "XT1072",
                        "XT1077",
                        "XT1078",
                        "XT1079"
                )
        )

        private val BITMAP_DRAWABLE_LOCK = if (MODELS_REQUIRING_BITMAP_LOCK.contains(Build.MODEL))
            ReentrantLock()
        else
            NoLock()

        init {
            CIRCLE_CROP_BITMAP_PAINT = Paint(CIRCLE_CROP_PAINT_FLAGS)
        }

        private fun getAlphaSafeBitmap(
                pool: BitmapPool, maybeAlphaSafe: Bitmap): Bitmap {
            val safeConfig = getAlphaSafeConfig(maybeAlphaSafe)
            if (safeConfig == maybeAlphaSafe.config) {
                return maybeAlphaSafe
            }

            val argbBitmap = pool.get(maybeAlphaSafe.width, maybeAlphaSafe.height, safeConfig)
            Canvas(argbBitmap).drawBitmap(maybeAlphaSafe, 0f /*left*/, 0f /*top*/, null /*paint*/)

            // We now own this Bitmap. It's our responsibility to replace it in the pool outside this method
            // when we're finished with it.
            return argbBitmap
        }

        private fun getAlphaSafeConfig(inBitmap: Bitmap): Bitmap.Config {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // Avoid short circuiting the sdk check.
                if (Bitmap.Config.RGBA_F16 == inBitmap.config) { // NOPMD
                    return Bitmap.Config.RGBA_F16
                }
            }

            return Bitmap.Config.ARGB_8888
        }

        private fun clear(canvas: Canvas) {
            canvas.setBitmap(null)
        }
    }

    private var mCornersRadius = 0f
    private var mBorderWidth = 0f
    private var mBorderColor = Color.TRANSPARENT
    private var mOverlayColor = Color.TRANSPARENT
    private var mPadding = 0f

    private var roundTopLeft = true
    private var roundTopRight = true
    private var roundBottomLeft = true
    private var roundBottomRight = true

    private val mRadii = FloatArray(8)
    internal val mBorderRadii = FloatArray(8)

    constructor(circleOptions: CircleOptions) {
        mBorderWidth = circleOptions.mBorderWidth
        mBorderColor = circleOptions.mBorderColor
        mOverlayColor = circleOptions.mOverlayColor
        mPadding = circleOptions.mPadding
        mCornersRadius = circleOptions.mCornersRadius
        // CornersRadiiOptions
        if (circleOptions.mCornersRadiiOptions != null) {
            circleOptions.mCornersRadiiOptions?.let { setCornersRadii(it.topLeft, it.topRight, it.bottomRight, it.bottomLeft) }
        } else {
            setCornersRadii(if (roundTopLeft) mCornersRadius else 0.0f,
                    if (roundTopRight) mCornersRadius else 0.0f,
                    if (roundBottomRight) mCornersRadius else 0.0f,
                    if (roundBottomLeft) mCornersRadius else 0.0f)
        }
    }

    constructor(attrs: SmartImageViewAttrs) {
        mBorderWidth = attrs.mRoundingBorderWidth.toFloat()
        mBorderColor = attrs.mRoundingBorderColor
        mOverlayColor = attrs.mRoundWithOverlayColor
        mPadding = attrs.mRoundingBorderPadding.toFloat()
        mCornersRadius = attrs.mRoundedCornerRadius.toFloat()
        // 通过资源设置圆角
        setRoundCorner(attrs)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return roundedCorners(pool, toTransform, outWidth, outHeight)
    }

    private fun setRoundCorner(attrs: SmartImageViewAttrs) {
        roundTopLeft = attrs.mRoundTopLeft
        roundTopRight = attrs.mRoundTopRight
        roundBottomLeft = attrs.mRoundBottomLeft
        roundBottomRight = attrs.mRoundBottomRight
        setCornersRadii(if (roundTopLeft) mCornersRadius else 0.0f,
                if (roundTopRight) mCornersRadius else 0.0f,
                if (roundBottomRight) mCornersRadius else 0.0f,
                if (roundBottomLeft) mCornersRadius else 0.0f)
    }

    fun roundedCorners(pool: BitmapPool, inBitmap: Bitmap, destWidth: Int, destHeight: Int): Bitmap {

        val toTransform = getAlphaSafeBitmap(pool, inBitmap)

        val outConfig = getAlphaSafeConfig(inBitmap)
        val result = pool.get(destWidth, destHeight, outConfig)
        result.setHasAlpha(true)

        val rect = RectF(0f, 0f, inBitmap.width.toFloat(), inBitmap.height.toFloat())

        BITMAP_DRAWABLE_LOCK.lock()
        try {
            val canvas = Canvas(result)

            //rect.inset((mBorderWidth), (mBorderWidth));
            val path = Path()
            path.addRoundRect(rect, mRadii, Path.Direction.CW)
            canvas.clipPath(path)

            canvas.drawBitmap(toTransform, null, rect, CIRCLE_CROP_BITMAP_PAINT)

            val borderPath = Path()
            for (i in mBorderRadii.indices) {
                mBorderRadii[i] = mRadii[i] + mBorderWidth / 2
            }
            borderPath.addRoundRect(rect, mBorderRadii, Path.Direction.CW)
            if (mBorderColor != Color.TRANSPARENT) {
                CIRCLE_CROP_SHAPE_PAINT.style = Paint.Style.STROKE
                CIRCLE_CROP_SHAPE_PAINT.color = mBorderColor
                CIRCLE_CROP_SHAPE_PAINT.strokeWidth = 2 * mBorderWidth
                path.fillType = Path.FillType.EVEN_ODD
                canvas.drawPath(borderPath, CIRCLE_CROP_SHAPE_PAINT)
            }

            clear(canvas)
        } finally {
            BITMAP_DRAWABLE_LOCK.unlock()
        }

        if (toTransform != inBitmap) {
            pool.put(toTransform)
        }

        return result
    }

    fun setCornersRadii(topLeft: Float, topRight: Float, bottomRight: Float, bottomLeft: Float) {
        mRadii[1] = topLeft
        mRadii[0] = mRadii[1]
        mRadii[3] = topRight
        mRadii[2] = mRadii[3]
        mRadii[5] = bottomRight
        mRadii[4] = mRadii[5]
        mRadii[7] = bottomLeft
        mRadii[6] = mRadii[7]
    }

    override fun equals(obj: Any?): Boolean {
        return obj is RoundCornersTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    private class NoLock @Synthetic
    internal constructor() : Lock {

        override fun lock() {
            // do nothing
        }

        @Throws(InterruptedException::class)
        override fun lockInterruptibly() {
            // do nothing
        }

        override fun tryLock(): Boolean {
            return true
        }

        @Throws(InterruptedException::class)
        override fun tryLock(time: Long, unit: TimeUnit): Boolean {
            return true
        }

        override fun unlock() {
            // do nothing
        }

        override fun newCondition(): Condition {
            throw UnsupportedOperationException("Should not be called")
        }
    }
}