package com.zlove.glide.loader

import android.widget.ImageView

class ScaleTypeTransformUtils {

    companion object {
        fun transformScaleType(scaleType: Int): ScaleTypeUtils.ScaleType? {
            when (scaleType) {
                -1 -> return null // non
                0 -> return ScaleTypeUtils.ScaleType.FIT_XY  // fitXY
                1 -> return ScaleTypeUtils.ScaleType.FIT_START // fitStart
                2 -> return ScaleTypeUtils.ScaleType.FIT_CENTER  // fitCenter
                3 -> return ScaleTypeUtils.ScaleType.FIT_END  // fitEnd
                4 -> return ScaleTypeUtils.ScaleType.CENTER  // center
                5 -> return ScaleTypeUtils.ScaleType.CENTER_INSIDE  // centerInside
                6 -> return ScaleTypeUtils.ScaleType.CENTER_CROP  // centerCrop
                7 -> return ScaleTypeUtils.ScaleType.FOCUS_CROP // focusCrop
                else -> return null
            }
        }

        fun transformImageScaleType(scaleType: ImageView.ScaleType): ScaleTypeUtils.ScaleType {
            when (scaleType) {
                ImageView.ScaleType.FIT_END -> return ScaleTypeUtils.ScaleType.FIT_END
                ImageView.ScaleType.FIT_START -> return ScaleTypeUtils.ScaleType.FIT_START
                ImageView.ScaleType.FIT_XY -> return ScaleTypeUtils.ScaleType.FIT_XY
                ImageView.ScaleType.CENTER -> return ScaleTypeUtils.ScaleType.CENTER
                ImageView.ScaleType.MATRIX -> return ScaleTypeUtils.ScaleType.CENTER_CROP
                ImageView.ScaleType.FIT_CENTER -> return ScaleTypeUtils.ScaleType.FIT_CENTER
                ImageView.ScaleType.CENTER_CROP -> return ScaleTypeUtils.ScaleType.CENTER_CROP
                ImageView.ScaleType.CENTER_INSIDE -> return ScaleTypeUtils.ScaleType.CENTER_INSIDE
                else -> return ScaleTypeTransformUtils.transformImageScaleType(scaleType)
            }
        }
    }


}