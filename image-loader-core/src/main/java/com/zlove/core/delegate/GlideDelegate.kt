package com.zlove.core.delegate

import com.zlove.core.ImageLoaderDelegate

class GlideDelegate {
    companion object {
        fun findDelegate(): ImageLoaderDelegate? {
            try {
                val glideDelegateClass = Class.forName("com.zlove.glide.loader.GlideImageLoaderDelegate")
                        .asSubclass<ImageLoaderDelegate>(ImageLoaderDelegate::class.java)
                val glideDelegateConstructor = glideDelegateClass.getConstructor()
                return glideDelegateConstructor.newInstance()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }
    }

}