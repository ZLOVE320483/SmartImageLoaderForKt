package com.zlove.fresco.loader

import com.facebook.drawee.drawable.ScalingUtils
import com.zlove.core.ScaleType

class ScaleTypeUtils {

    companion object {
        fun transferActualScaleType(scaleType: ScaleType): ScalingUtils.ScaleType {
            when(scaleType) {
                ScaleType.FIT_XY -> return ScalingUtils.ScaleType.FIT_XY
                ScaleType.FIT_START -> return ScalingUtils.ScaleType.FIT_START
                ScaleType.FIT_END -> return ScalingUtils.ScaleType.FIT_END
                ScaleType.FIT_CENTER -> return ScalingUtils.ScaleType.FIT_CENTER
                ScaleType.CENTER -> return ScalingUtils.ScaleType.CENTER
                ScaleType.CENTER_CROP -> return ScalingUtils.ScaleType.CENTER_CROP
                ScaleType.CENTER_INSIDE -> return ScalingUtils.ScaleType.CENTER_INSIDE
                ScaleType.FOCUS_CROP -> return ScalingUtils.ScaleType.FOCUS_CROP
                else -> return ScalingUtils.ScaleType.CENTER_CROP
            }
        }
    }

}