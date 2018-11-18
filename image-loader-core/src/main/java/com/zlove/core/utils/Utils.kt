package com.zlove.core.utils

import android.content.Context
import android.net.Uri

class Utils {

    companion object {

        fun fromResourceId(pkg: String, resId: Int): Uri {
            return fromUrl("android.resource://$pkg/$resId")
        }

        fun fromAsset(path: String): String {
            return "file:///android_asset/$path"
        }

        fun fromUrl(url: String): Uri {
            return Uri.parse(url)
        }

        fun dip2px(context: Context, dipValue: Float): Float {
            val scale = context.resources.displayMetrics.density;
            return dipValue * scale + 0.5f
        }
    }
}