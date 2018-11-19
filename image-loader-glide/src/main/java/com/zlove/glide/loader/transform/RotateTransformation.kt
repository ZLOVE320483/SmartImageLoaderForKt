package com.zlove.glide.loader.transform

import android.graphics.Bitmap
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import java.security.MessageDigest

class RotateTransformation(private val rotateAngle: Int): BitmapTransformation() {

    companion object {
        private val ID = CircleTransformation::class.java.name
        private val ID_BYTES = ID.toByteArray(Key.CHARSET)
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(ID_BYTES)
    }

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        return TransformationUtils.rotateImage(toTransform, rotateAngle)
    }

    override fun equals(obj: Any?): Boolean {
        return obj is RotateTransformation
    }

    override fun hashCode(): Int {
        return ID.hashCode()
    }
}