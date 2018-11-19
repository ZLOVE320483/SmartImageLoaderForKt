package com.zlove.fresco.loader

import com.facebook.drawee.generic.RoundingParams
import com.zlove.core.CircleOptions

class Utils {

    companion object {
        fun roundingMethodTransfer(roundingMethod: CircleOptions.RoundingMethod?): RoundingParams.RoundingMethod {
            when(roundingMethod) {
                CircleOptions.RoundingMethod.BITMAP_ONLY -> return RoundingParams.RoundingMethod.BITMAP_ONLY
                CircleOptions.RoundingMethod.OVERLAY_COLOR -> return RoundingParams.RoundingMethod.OVERLAY_COLOR
                else -> return RoundingParams.RoundingMethod.BITMAP_ONLY
            }
        }
    }
}