package com.zlove.core.delegate

import com.zlove.core.ImageLoaderDelegate

class FrescoDelegate {

    companion object {
        fun findDelegate(): ImageLoaderDelegate? {
            try {
                val frescoDelegateClass = Class.forName("com.zlove.fresco.loader.FrescoImageLoaderDelegate")
                        .asSubclass<ImageLoaderDelegate>(ImageLoaderDelegate::class.java)
                val frescoDelegateConstructor = frescoDelegateClass.getConstructor()
                return frescoDelegateConstructor.newInstance()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

    }
}