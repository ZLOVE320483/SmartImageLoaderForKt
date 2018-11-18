package com.zlove.core

class BlurOptions {

    companion object {
        const val DEFAULT_BLUR_RADIUS = 5
        const val DEFAULT_SCALE_RATIO = 1
    }

    var mBlurRadius: Int? = null
    var mScaleRatio: Int? = null
    var mEqualScale: Float? = null

    constructor() {
        this.mBlurRadius = DEFAULT_BLUR_RADIUS
        this.mEqualScale = DEFAULT_SCALE_RATIO.toFloat()
        this.mScaleRatio = DEFAULT_SCALE_RATIO
    }

    constructor(blurRadius: Int, equalScale: Float) {
        this.mBlurRadius = blurRadius
        this.mEqualScale = equalScale
        this.mScaleRatio = DEFAULT_SCALE_RATIO
    }

    constructor(blurRadius: Int, equalScale: Float, scaleRatio: Int) {
        this.mBlurRadius = blurRadius
        this.mEqualScale = equalScale
        this.mScaleRatio = scaleRatio
    }

}