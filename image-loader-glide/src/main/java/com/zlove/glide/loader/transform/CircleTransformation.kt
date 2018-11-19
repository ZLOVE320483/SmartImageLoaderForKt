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

class CircleTransformation: BitmapTransformation {

    companion object {
        private val ID = CircleTransformation::class.java.name
        private val ID_BYTES = ID.toByteArray(Key.CHARSET)
        const val PAINT_FLAGS = Paint.DITHER_FLAG or Paint.FILTER_BITMAP_FLAG
        private const val CIRCLE_CROP_PAINT_FLAGS = PAINT_FLAGS or Paint.ANTI_ALIAS_FLAG
        private val CIRCLE_CROP_SHAPE_PAINT = Paint(CIRCLE_CROP_PAINT_FLAGS)
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
            CIRCLE_CROP_BITMAP_PAINT.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
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

    private var mBorderWidth = 0f
    private var mBorderColor = Color.TRANSPARENT
    private var mOverlayColor = Color.TRANSPARENT
    private var mPadding = 0f

    constructor()

    constructor(circleOptions: CircleOptions): this() {
        mBorderWidth = circleOptions.mBorderWidth
        mBorderColor = circleOptions.mBorderColor
        mOverlayColor = circleOptions.mOverlayColor
        mPadding = circleOptions.mPadding
    }

    constructor(attrs: SmartImageViewAttrs): this() {
        mBorderWidth = attrs.mRoundingBorderWidth.toFloat()
        mBorderColor = attrs.mRoundingBorderColor
        mOverlayColor = attrs.mRoundWithOverlayColor
        mPadding = attrs.mRoundingBorderPadding.toFloat()
    }


    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return circleCrop(pool, toTransform, outWidth, outHeight)
    }

    private fun circleCrop(pool: BitmapPool, inBitmap: Bitmap,
                           destWidth: Int, destHeight: Int): Bitmap {
        val destMinEdge = Math.min(destWidth, destHeight)
        var radius = destMinEdge / 2f
        val borderRadius = radius

        val srcWidth = inBitmap.width
        val srcHeight = inBitmap.height

        val scaleX = destMinEdge / srcWidth.toFloat()
        val scaleY = destMinEdge / srcHeight.toFloat()
        val maxScale = Math.max(scaleX, scaleY)

        val scaledWidth = maxScale * srcWidth
        val scaledHeight = maxScale * srcHeight
        val left = (destMinEdge - scaledWidth) / 2f
        val top = (destMinEdge - scaledHeight) / 2f

        val destRect = RectF(left, top, left + scaledWidth, top + scaledHeight)

        // Alpha is required for this transformation.
        val toTransform = getAlphaSafeBitmap(pool, inBitmap)

        val outConfig = getAlphaSafeConfig(inBitmap)
        val result = pool.get(destMinEdge, destMinEdge, outConfig)
        result.setHasAlpha(true)

        BITMAP_DRAWABLE_LOCK.lock()
        try {
            val canvas = Canvas(result)

            // Draw a circle
            radius -= mBorderWidth
            canvas.drawCircle(borderRadius, borderRadius, radius, CIRCLE_CROP_SHAPE_PAINT)

            // Draw the bitmap in the circle
            destRect.inset(mBorderWidth, mBorderWidth)
            canvas.drawBitmap(toTransform, null, destRect, CIRCLE_CROP_BITMAP_PAINT)

            // 增加圆形边框
            CIRCLE_CROP_SHAPE_PAINT.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
            CIRCLE_CROP_SHAPE_PAINT.color = mBorderColor
            canvas.drawCircle(borderRadius, borderRadius, borderRadius, CIRCLE_CROP_SHAPE_PAINT)

            clear(canvas)
        } finally {
            BITMAP_DRAWABLE_LOCK.unlock()
        }

        if (toTransform != inBitmap) {
            pool.put(toTransform)
        }

        return result
    }

    override fun equals(other: Any?): Boolean {
        return other is CircleTransformation
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