package com.zlove.core

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import com.zlove.core.listener.BlurProcessListener
import com.zlove.core.listener.ImageDisplayListener
import com.zlove.core.listener.ImageLoadListener
import com.zlove.core.utils.Utils

class DisplayRequestBuilder {

    lateinit var mUri: Uri
    lateinit var mContext: Context
    var mAutoPlayAnimations = false
    var mAutoRotate = true
    var mDecodeAllFrames = false
    var mProgressiveRendering = false
    var mWidth = 0
    var mHeight = 0
    var mPlaceholder = 0
    var mFailureImage = 0
    var mBitmapConfig: Bitmap.Config = Bitmap.Config.ARGB_8888
    var mActualImageScaleType = ScaleType.CENTER_CROP
    var mUrlList: List<String>? = null
    var mCircleOptions: CircleOptions? = null
    var mBlurOptions: BlurOptions? = null
    var mCropOptions: CropOptions? = null
    var mImagePipelinePriority: ImagePipelinePriority? = null
    var mImageDisplayListener: ImageDisplayListener? = null
    var mImageLoadListener: ImageLoadListener? = null
    var mBlurProcessListener: BlurProcessListener? = null

    constructor()

    constructor(@NonNull uri: Uri): this() {
        this.mUri = uri
    }

    constructor(@NonNull url: String): this() {
        this.mUri = Utils.fromUrl(url)
    }

    constructor(resourceId: Int): this() {
        this.mUri = Utils.fromResourceId(ImageLoader.sPkgName, resourceId)
    }

    constructor(@NonNull urlList: List<String>): this() {
        this.mUrlList = urlList
    }

    fun with(context: Context): DisplayRequestBuilder {
        this.mContext = context
        return this
    }

    fun uri(uri: Uri): DisplayRequestBuilder {
        this.mUri = uri
        return this
    }

    fun autoPlayAnimations(autoPlayAnimations: Boolean): DisplayRequestBuilder {
        this.mAutoPlayAnimations = autoPlayAnimations
        return this
    }

    fun autoRotate(autoRotate: Boolean): DisplayRequestBuilder {
        this.mAutoRotate = autoRotate
        return this
    }

    fun decodeAllFrames(decodeAllFrames: Boolean): DisplayRequestBuilder {
        this.mDecodeAllFrames = decodeAllFrames
        return this
    }

    fun progressiveRendering(progressiveRendering: Boolean): DisplayRequestBuilder {
        this.mProgressiveRendering = progressiveRendering
        return this
    }

    fun resize(width: Int, height: Int): DisplayRequestBuilder {
        this.mWidth = width
        this.mHeight = height
        return this
    }

    fun placeholder(@DrawableRes placeholder: Int): DisplayRequestBuilder {
        this.mPlaceholder = placeholder
        return this
    }

    fun failureImage(@DrawableRes failureImage: Int): DisplayRequestBuilder {
        this.mFailureImage = failureImage
        return this
    }

    fun bitmapConfig(bitmapConfig: Bitmap.Config): DisplayRequestBuilder {
        this.mBitmapConfig = bitmapConfig
        return this
    }

    fun actualImageScaleType(actualImageScaleType: ScaleType): DisplayRequestBuilder {
        this.mActualImageScaleType = actualImageScaleType
        return this
    }

    fun circle(circleOptions: CircleOptions?): DisplayRequestBuilder {
        this.mCircleOptions = circleOptions
        return this
    }

    fun blur(blurOptions: BlurOptions?): DisplayRequestBuilder {
        this.mBlurOptions = blurOptions
        return this
    }

    fun crop(cropOptions: CropOptions?): DisplayRequestBuilder {
        this.mCropOptions = cropOptions
        return this
    }

    fun imagePipelinePriority(imagePipelinePriority: ImagePipelinePriority?): DisplayRequestBuilder {
        this.mImagePipelinePriority = imagePipelinePriority
        return this
    }

    fun imageDisplayListener(imageDisplayListener: ImageDisplayListener?): DisplayRequestBuilder {
        this.mImageDisplayListener = imageDisplayListener
        return this
    }

    fun imageLoadListener(imageLoadListener: ImageLoadListener?): DisplayRequestBuilder {
        this.mImageLoadListener = imageLoadListener
        return this
    }

    fun blurProcessListener(blurProcessListener: BlurProcessListener?): DisplayRequestBuilder {
        this.mBlurProcessListener = blurProcessListener
        return this
    }

    fun build(): DisplayRequest {
        return DisplayRequest(this)
    }
}