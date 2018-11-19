package com.zlove.fresco.loader

import android.graphics.Bitmap
import com.facebook.common.references.CloseableReference
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory
import com.facebook.imagepipeline.request.BasePostprocessor
import com.zlove.core.CropOptions

class CropPostProcessor(private val mCropOptions: CropOptions) : BasePostprocessor() {

    override fun process(sourceBitmap: Bitmap, bitmapFactory: PlatformBitmapFactory): CloseableReference<Bitmap>? {
        val bitmapRef = bitmapFactory.createBitmap(sourceBitmap, mCropOptions.x,
                mCropOptions.y, mCropOptions.width, mCropOptions.height)
        return CloseableReference.cloneOrNull(bitmapRef)
    }

}