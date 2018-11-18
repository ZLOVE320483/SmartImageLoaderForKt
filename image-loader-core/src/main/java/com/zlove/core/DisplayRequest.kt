package com.zlove.core

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.support.annotation.DrawableRes
import com.zlove.core.listener.BlurProcessListener
import com.zlove.core.listener.ImageDisplayListener
import com.zlove.core.listener.ImageLoadListener

class DisplayRequest(builder: DisplayRequestBuilder) {

    private var mUri: Uri? = null
    private var mContext: Context? = null
    private var mAutoPlayAnimations = false
    private var mAutoRotate = false
    private var mDecodeAllFrames = false
    private var mProgressiveRendering = false
    private var mWidth = 0
    private var mHeight = 0
    @DrawableRes
    private var mPlaceholder = 0
    @DrawableRes
    private var mFailureImage = 0
    private var mBitmapConfig: Bitmap.Config? = null
    private var mActualImageScaleType: ScaleType? = null
    private var mUrlList: List<String>? = null
    private var mCircleOptions: CircleOptions? = null
    private var mBlurOptions: BlurOptions? = null
    private var mCropOptions: CropOptions? = null
    private var mImagePipelinePriority: ImagePipelinePriority? = null
    private var mImageDisplayListener: ImageDisplayListener? = null
    private var mImageLoadListener: ImageLoadListener? = null
    private var mBlurProcessListener: BlurProcessListener? = null

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