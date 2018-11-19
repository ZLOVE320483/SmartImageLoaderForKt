package com.zlove.glide.loader

class SmartImageViewAttrs(builder: Builder) {

    val mSmartActualImageUri: String?
    val mActualImageResource: Int
    val mFadeDuration: Int
    val mViewAspectRatio: Float

    val mPlaceholderImage: Int
    val mPlaceholderImageScaleType: Int

    val mRetryImage: Int
    val mRetryImageScaleType: Int

    val mFailureImage: Int
    val mFailureImageScaleType: Int

    val mProgressBarImage: Int
    val mProgressBarImageScaleType: Int

    val mProgressBarAutoRotateInterval: Int
    val mActualImageScaleType: Int

    val mBackgroundImage: Int
    val mOverlayImage: Int
    val mPressedStateOverlayImage: Int

    val mRoundAsCircle: Boolean
    val mRoundedCornerRadius: Int
    val mRoundTopLeft: Boolean
    val mRoundTopRight: Boolean
    val mRoundBottomRight: Boolean
    val mRoundBottomLeft: Boolean
    val mRoundTopStart: Boolean
    val mRoundTopEnd: Boolean
    val mRoundBottomStart: Boolean
    val mRoundBottomEnd: Boolean
    val mRoundWithOverlayColor: Int
    val mRoundingBorderWidth: Int
    val mRoundingBorderColor: Int
    val mRoundingBorderPadding: Int
    
    init {
        mSmartActualImageUri = builder.mSmartActualImageUri
        mActualImageResource = builder.mActualImageResource
        mFadeDuration = builder.mFadeDuration
        mViewAspectRatio = builder.mViewAspectRatio
        mPlaceholderImage = builder.mPlaceholderImage
        mPlaceholderImageScaleType = builder.mPlaceholderImageScaleType
        mRetryImage = builder.mRetryImage
        mRetryImageScaleType = builder.mRetryImageScaleType
        mFailureImage = builder.mFailureImage
        mFailureImageScaleType = builder.mFailureImageScaleType
        mProgressBarImage = builder.mProgressBarImage
        mProgressBarImageScaleType = builder.mProgressBarImageScaleType
        mProgressBarAutoRotateInterval = builder.mProgressBarAutoRotateInterval
        mActualImageScaleType = builder.mActualImageScaleType
        mBackgroundImage = builder.mBackgroundImage
        mOverlayImage = builder.mOverlayImage
        mPressedStateOverlayImage = builder.mPressedStateOverlayImage
        mRoundAsCircle = builder.mRoundAsCircle
        mRoundedCornerRadius = builder.mRoundedCornerRadius
        mRoundTopLeft = builder.mRoundTopLeft
        mRoundTopRight = builder.mRoundTopRight
        mRoundBottomRight = builder.mRoundBottomRight
        mRoundBottomLeft = builder.mRoundBottomLeft
        mRoundTopStart = builder.mRoundTopStart
        mRoundTopEnd = builder.mRoundTopEnd
        mRoundBottomStart = builder.mRoundBottomStart
        mRoundBottomEnd = builder.mRoundBottomEnd
        mRoundWithOverlayColor = builder.mRoundWithOverlayColor
        mRoundingBorderWidth = builder.mRoundingBorderWidth
        mRoundingBorderColor = builder.mRoundingBorderColor
        mRoundingBorderPadding = builder.mRoundingBorderPadding
    }
    
    class Builder {
        var mSmartActualImageUri: String? = null
        var mActualImageResource: Int = 0
        var mFadeDuration: Int = 0
        var mViewAspectRatio: Float = 0.toFloat()
        var mPlaceholderImage: Int = 0
        var mPlaceholderImageScaleType: Int = 0
        var mRetryImage: Int = 0
        var mRetryImageScaleType: Int = 0
        var mFailureImage: Int = 0
        var mFailureImageScaleType: Int = 0
        var mProgressBarImage: Int = 0
        var mProgressBarImageScaleType: Int = 0
        var mProgressBarAutoRotateInterval: Int = 0
        var mActualImageScaleType = -1
        var mBackgroundImage: Int = 0
        var mOverlayImage: Int = 0
        var mPressedStateOverlayImage: Int = 0
        var mRoundAsCircle: Boolean = false
        var mRoundedCornerRadius: Int = 0
        var mRoundTopLeft = true
        var mRoundTopRight = true
        var mRoundBottomRight = true
        var mRoundBottomLeft = true
        var mRoundTopStart = true
        var mRoundTopEnd = true
        var mRoundBottomStart = true
        var mRoundBottomEnd = true
        var mRoundWithOverlayColor: Int = 0
        var mRoundingBorderWidth: Int = 0
        var mRoundingBorderColor: Int = 0
        var mRoundingBorderPadding: Int = 0


        fun smartActualImageUri(`val`: String): Builder {
            mSmartActualImageUri = `val`
            return this
        }

        fun actualImageResource(`val`: Int): Builder {
            mActualImageResource = `val`
            return this
        }

        fun fadeDuration(`val`: Int): Builder {
            mFadeDuration = `val`
            return this
        }

        fun viewAspectRatio(`val`: Float): Builder {
            mViewAspectRatio = `val`
            return this
        }

        fun placeholderImage(`val`: Int): Builder {
            mPlaceholderImage = `val`
            return this
        }

        fun placeholderImageScaleType(`val`: Int): Builder {
            mPlaceholderImageScaleType = `val`
            return this
        }

        fun retryImage(`val`: Int): Builder {
            mRetryImage = `val`
            return this
        }

        fun retryImageScaleType(`val`: Int): Builder {
            mRetryImageScaleType = `val`
            return this
        }

        fun failureImage(`val`: Int): Builder {
            mFailureImage = `val`
            return this
        }

        fun failureImageScaleType(`val`: Int): Builder {
            mFailureImageScaleType = `val`
            return this
        }

        fun progressBarImage(`val`: Int): Builder {
            mProgressBarImage = `val`
            return this
        }

        fun progressBarImageScaleType(`val`: Int): Builder {
            mProgressBarImageScaleType = `val`
            return this
        }

        fun progressBarAutoRotateInterval(`val`: Int): Builder {
            mProgressBarAutoRotateInterval = `val`
            return this
        }

        fun actualImageScaleType(`val`: Int): Builder {
            mActualImageScaleType = `val`
            return this
        }

        fun backgroundImage(`val`: Int): Builder {
            mBackgroundImage = `val`
            return this
        }

        fun overlayImage(`val`: Int): Builder {
            mOverlayImage = `val`
            return this
        }

        fun pressedStateOverlayImage(`val`: Int): Builder {
            mPressedStateOverlayImage = `val`
            return this
        }

        fun roundAsCircle(`val`: Boolean): Builder {
            mRoundAsCircle = `val`
            return this
        }

        fun roundedCornerRadius(`val`: Int): Builder {
            mRoundedCornerRadius = `val`
            return this
        }

        fun roundTopLeft(`val`: Boolean): Builder {
            mRoundTopLeft = `val`
            return this
        }

        fun roundTopRight(`val`: Boolean): Builder {
            mRoundTopRight = `val`
            return this
        }

        fun roundBottomRight(`val`: Boolean): Builder {
            mRoundBottomRight = `val`
            return this
        }

        fun roundBottomLeft(`val`: Boolean): Builder {
            mRoundBottomLeft = `val`
            return this
        }

        fun roundTopStart(`val`: Boolean): Builder {
            mRoundTopStart = `val`
            return this
        }

        fun roundTopEnd(`val`: Boolean): Builder {
            mRoundTopEnd = `val`
            return this
        }

        fun roundBottomStart(`val`: Boolean): Builder {
            mRoundBottomStart = `val`
            return this
        }

        fun roundBottomEnd(`val`: Boolean): Builder {
            mRoundBottomEnd = `val`
            return this
        }

        fun roundWithOverlayColor(`val`: Int): Builder {
            mRoundWithOverlayColor = `val`
            return this
        }

        fun mRoundingBorderWidth(`val`: Int): Builder {
            mRoundingBorderWidth = `val`
            return this
        }

        fun roundingBorderColor(`val`: Int): Builder {
            mRoundingBorderColor = `val`
            return this
        }

        fun roundingBorderPadding(`val`: Int): Builder {
            mRoundingBorderPadding = `val`
            return this
        }

        fun build(): SmartImageViewAttrs {
            return SmartImageViewAttrs(this)
        }
    }
}