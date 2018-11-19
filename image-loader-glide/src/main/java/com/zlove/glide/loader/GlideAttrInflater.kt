package com.zlove.glide.loader

import android.content.Context
import android.util.AttributeSet

class GlideAttrInflater {

    companion object {

        fun inflateAttr(context: Context, attrs: AttributeSet?): SmartImageViewAttrs? {
            val builder = SmartImageViewAttrs.Builder()
            attrs?.let {
                val typedArray = context.obtainStyledAttributes(it, R.styleable.SmartImageView)
                try {
                    val indexCount = typedArray.indexCount
                    for (i in 0..indexCount) {
                        val attr = typedArray.getIndex(i)
                        when(attr) {
                            R.styleable.SmartImageView_actualImageUri -> builder.mSmartActualImageUri = typedArray.getString(attr)
                            R.styleable.SmartImageView_actualImageResource -> builder.mActualImageResource = typedArray.getResourceId(attr, -1)
                            R.styleable.SmartImageView_fadeDuration -> builder.mFadeDuration = typedArray.getInt(attr, 0)
                            R.styleable.SmartImageView_viewAspectRatio -> builder.mViewAspectRatio = typedArray.getFloat(attr, 0.0f)
                            R.styleable.SmartImageView_placeholderImage -> builder.mPlaceholderImage = typedArray.getResourceId(attr, 0)
                            R.styleable.SmartImageView_placeholderImageScaleType -> builder.mPlaceholderImageScaleType = typedArray.getInt(attr, 0)
                            R.styleable.SmartImageView_retryImage -> builder.mRetryImage = typedArray.getResourceId(attr, 0)
                            R.styleable.SmartImageView_retryImageScaleType -> builder.mRetryImageScaleType = typedArray.getInt(attr, 0)
                            R.styleable.SmartImageView_failureImage -> builder.mFailureImage = typedArray.getResourceId(attr, 0)
                            R.styleable.SmartImageView_failureImageScaleType -> builder.mFailureImageScaleType = typedArray.getInt(attr, 0)
                            R.styleable.SmartImageView_progressBarImage -> builder.mProgressBarImage = typedArray.getResourceId(attr, 0)
                            R.styleable.SmartImageView_progressBarImageScaleType -> builder.mProgressBarImageScaleType = typedArray.getInt(attr, 0)
                            R.styleable.SmartImageView_progressBarAutoRotateInterval -> builder.mProgressBarAutoRotateInterval = typedArray.getInt(attr, 0)
                            R.styleable.SmartImageView_actualImageScaleType -> builder.mActualImageScaleType = typedArray.getInt(attr, -1)
                            R.styleable.SmartImageView_backgroundImage -> builder.mBackgroundImage =typedArray.getResourceId(attr, 0)
                            R.styleable.SmartImageView_overlayImage -> builder.mOverlayImage = typedArray.getResourceId(attr, 0)
                            R.styleable.SmartImageView_pressedStateOverlayImage -> builder.mPressedStateOverlayImage = typedArray.getResourceId(attr, 0) 
                            R.styleable.SmartImageView_roundAsCircle -> builder.mRoundAsCircle = typedArray.getBoolean(attr, false)
                            R.styleable.SmartImageView_roundedCornerRadius -> builder.mRoundedCornerRadius = typedArray.getDimensionPixelSize(attr, 0)
                            R.styleable.SmartImageView_roundTopRight -> builder.mRoundTopRight = typedArray.getBoolean(attr, true)
                            R.styleable.SmartImageView_roundBottomRight -> builder.mRoundBottomRight = typedArray.getBoolean(attr, true)
                            R.styleable.SmartImageView_roundBottomLeft -> builder.mRoundBottomLeft = typedArray.getBoolean(attr, true)
                            R.styleable.SmartImageView_roundTopStart -> builder.mRoundTopStart = typedArray.getBoolean(attr, true)
                            R.styleable.SmartImageView_roundTopEnd -> builder.mRoundTopEnd = typedArray.getBoolean(attr, true)
                            R.styleable.SmartImageView_roundBottomStart -> builder.mRoundBottomStart = typedArray.getBoolean(attr, true)
                            R.styleable.SmartImageView_roundBottomEnd -> builder.mRoundBottomEnd = typedArray.getBoolean(attr, true)
                            R.styleable.SmartImageView_roundWithOverlayColor -> builder.mRoundWithOverlayColor = typedArray.getColor(attr, 0)
                            R.styleable.SmartImageView_roundingBorderWidth -> builder.mRoundingBorderWidth = typedArray.getDimensionPixelSize(attr, 0)
                            R.styleable.SmartImageView_roundingBorderColor -> builder.mRoundingBorderColor = typedArray.getColor(attr, 0)
                            R.styleable.SmartImageView_roundingBorderPadding -> builder.mRoundingBorderPadding = typedArray.getDimensionPixelSize(attr, 0)
                        }
                    }
                } finally {
                    typedArray.recycle()
                }
                return builder.build()
            }

            return null
        }
    }
}