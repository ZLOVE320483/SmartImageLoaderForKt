package com.zlove.core

import android.graphics.Color
import android.support.annotation.ColorInt

class CircleOptions(builder: Builder) {
    enum class RoundingMethod {
        OVERLAY_COLOR, BITMAP_ONLY
    }

    private var mBorderWidth: Float? = null

    @ColorInt
    private var mBorderColor: Int? = null

    @ColorInt
    private var mOverlayColor: Int? = null

    private var mRoundAsCircle: Boolean? = null

    private var mCornersRadius: Float? = null
    private var mPadding: Float? = null
    private var mCornersRadiiOptions: CornersRadiiOptions? = null
    private var mRoundingMethod: RoundingMethod? = null

    init {
        mBorderWidth = builder.mBorderWidth
        mBorderColor= builder.mBorderColor
        mOverlayColor = builder.mOverlayColor
        mRoundAsCircle = builder.mRoundAsCircle
        mCornersRadius = builder.mCornersRadius
        mPadding = builder.mPadding
        mCornersRadiiOptions = builder.mCornersRadiiOptions
        mRoundingMethod = builder.mRoundingMethod
    }

    class Builder {
        var mRoundAsCircle = false
        var mBorderWidth = 0f
        var mBorderColor = Color.TRANSPARENT
        var mOverlayColor = Color.TRANSPARENT
        var mCornersRadius = 0f
        var mPadding = 0f
        var mCornersRadiiOptions: CornersRadiiOptions? = null
        var mRoundingMethod = RoundingMethod.BITMAP_ONLY

        fun border(@ColorInt borderColor: Int, borderWidth: Float): Builder {
            this.mBorderColor = borderColor
            this.mBorderWidth = borderWidth
            return this
        }

        fun borderColor(@ColorInt borderColor: Int): Builder {
            this.mBorderColor = borderColor
            return this
        }

        fun borderWidth(borderWidth: Float): Builder {
            this.mBorderWidth = borderWidth
            return this
        }

        fun roundAsCircle(roundAsCircle: Boolean): Builder {
            this.mRoundAsCircle = roundAsCircle
            return this
        }

        fun overlayColor(@ColorInt overlayColor: Int): Builder {
            this.mOverlayColor = overlayColor
            return this
        }

        fun cornersRadius(cornersRadius: Float): Builder {
            this.mCornersRadius = cornersRadius
            return this
        }

        fun padding(padding: Float): Builder {
            this.mPadding = padding
            return this
        }

        fun cornersRadiiOptions(cornersRadiiOptions: CornersRadiiOptions): Builder {
            this.mCornersRadiiOptions = cornersRadiiOptions
            return this
        }

        fun roundingMethod(roundingMethod: RoundingMethod): Builder {
            this.mRoundingMethod = roundingMethod
            return this
        }

        fun build(): CircleOptions {
            return CircleOptions(this)
        }
    }

    class CornersRadiiOptions(topLeft: Float, topRight: Float,
                              bottomRight: Float, bottomLeft: Float)
}