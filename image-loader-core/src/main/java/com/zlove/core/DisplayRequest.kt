package com.zlove.core

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.annotation.DrawableRes
import android.support.annotation.Nullable
import com.zlove.core.listener.BlurProcessListener
import com.zlove.core.listener.ImageDisplayListener
import com.zlove.core.listener.ImageLoadListener

class DisplayRequest(builder: DisplayRequestBuilder) {

    var mUri: Uri
    var mContext: Context
    var mAutoPlayAnimations = false
    var mAutoRotate = false
    var mDecodeAllFrames = false
    var mProgressiveRendering = false
    var mWidth = 0
    var mHeight = 0
    @DrawableRes
    var mPlaceholder = 0
    @DrawableRes
    var mFailureImage = 0
    var mBitmapConfig: Bitmap.Config

    @Nullable
    var mActualImageScaleType: ScaleType? = null

    var mUrlList: List<String>?

    @Nullable
    var mCircleOptions: CircleOptions? = null

    @Nullable
    var mBlurOptions: BlurOptions? = null

    @Nullable
    var mCropOptions: CropOptions? = null

    @Nullable
    var mImagePipelinePriority: ImagePipelinePriority? = null

    @Nullable
    var mImageDisplayListener: ImageDisplayListener? = null

    @Nullable
    var mImageLoadListener: ImageLoadListener? = null

    @Nullable
    var mBlurProcessListener: BlurProcessListener? = null

    init {
        mUri = builder.mUri
        mContext = builder.mContext
        mAutoPlayAnimations = builder.mAutoPlayAnimations
        mAutoRotate = builder.mAutoRotate
        mDecodeAllFrames = builder.mDecodeAllFrames
        mProgressiveRendering = builder.mProgressiveRendering
        mWidth = builder.mWidth
        mHeight = builder.mHeight
        mPlaceholder = builder.mPlaceholder
        mFailureImage = builder.mFailureImage
        mBitmapConfig = builder.mBitmapConfig
        mActualImageScaleType = builder.mActualImageScaleType
        mUrlList = builder.mUrlList
        mCircleOptions = builder.mCircleOptions
        mBlurOptions = builder.mBlurOptions
        mCropOptions = builder.mCropOptions
        mImagePipelinePriority = builder.mImagePipelinePriority
        mImageDisplayListener = builder.mImageDisplayListener
        mImageLoadListener = builder.mImageLoadListener
        mBlurProcessListener = builder.mBlurProcessListener
    }
}