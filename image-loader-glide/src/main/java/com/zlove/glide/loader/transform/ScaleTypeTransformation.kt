package com.zlove.glide.loader.transform

import android.graphics.*
import android.widget.ImageView
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.zlove.glide.loader.ScaleTypeTransformUtils
import com.zlove.glide.loader.ScaleTypeUtils
import java.security.MessageDigest

class ScaleTypeTransformation: BitmapTransformation {

    companion object {
        private val ID = CircleTransformation::class.java.name
        private val ID_BYTES = ID.toByteArray(Key.CHARSET)

        private fun getNonNullConfig(bitmap: Bitmap): Bitmap.Config {
            return if (bitmap.config != null) bitmap.config else Bitmap.Config.ARGB_8888
        }

        private fun clear(canvas: Canvas) {
            canvas.setBitmap(null)
        }
    }

    private val mPaint = Paint()
    private var mScaleType: ScaleTypeUtils.ScaleType? = null
    private val mTempMatrix = Matrix()

    constructor(scaleType: Int) {
        mScaleType = ScaleTypeTransformUtils.transformScaleType(scaleType)
    }

    constructor(scaleType: ImageView.ScaleType) {
        mScaleType = ScaleTypeTransformUtils.transformImageScaleType(scaleType)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val result = pool.get(outWidth, outHeight, getNonNullConfig(toTransform))
        TransformationUtils.setAlpha(toTransform, result)

        val rect = Rect(0, 0, outWidth, outHeight)
        mScaleType?.let {
            val matrix = it.getTransform(mTempMatrix, rect, toTransform.width, toTransform.height, 0.5f, 0.5f)
            matrix.let { applyMatrix(toTransform, result, it) }
        }
        return result
    }

    override fun equals(obj: Any?): Boolean {
        return obj is CircleTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    private fun applyMatrix(inBitmap: Bitmap, targetBitmap: Bitmap, matrix: Matrix) {
        val canvas = Canvas(targetBitmap)
        canvas.drawBitmap(inBitmap, matrix, mPaint)
        clear(canvas)
    }
}