package com.zlove.core

import android.graphics.Color
import android.support.annotation.ColorInt

class CircleOptions(builder: Builder) {
    enum class RoundingMethod {
        OVERLAY_COLOR, BITMAP_ONLY
    }

    var mBorderWidth: Float

    @ColorInt
    var mBorderColor: Int

    @ColorInt
    var mOverlayColor: Int

    var mRoundAsCircle: Boolean

    var mCornersRadius: Float
    var mPadding: Float
    var mCornersRadiiOptions: CornersRadiiOptions?
    var mRoundingMethod: RoundingMethod? = null

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
        lateinit var mCornersRadiiOptions: CornersRadiiOptions
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

    class CornersRadiiOptions(val topLeft: Float, val topRight: Float, val bottomRight: Float, val bottomLeft: Float)
}