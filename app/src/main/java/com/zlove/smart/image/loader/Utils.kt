package com.zlove.smart.image.loader

import android.content.Context
import android.util.TypedValue

object Utils {

    fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.resources.displayMetrics).toInt()
    }

}