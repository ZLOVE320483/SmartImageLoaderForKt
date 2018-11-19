package com.zlove.fresco.loader

import android.app.ActivityManager
import android.os.Build
import com.facebook.common.internal.Supplier
import com.facebook.common.util.ByteConstants
import com.facebook.imagepipeline.cache.MemoryCacheParams

class FrescoMemoryCacheParamsSupplier(private val mActivityManager: ActivityManager): Supplier<MemoryCacheParams> {


    override fun get(): MemoryCacheParams {
        val maxCacheSize = getMaxCacheSize()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            MemoryCacheParams(maxCacheSize, 128, maxCacheSize / 12, Integer.MAX_VALUE, Integer.MAX_VALUE)
        } else {
            MemoryCacheParams(maxCacheSize, 256, maxCacheSize / 8, Integer.MAX_VALUE, Integer.MAX_VALUE)
        }
    }

    private fun getMaxCacheSize(): Int {
        val maxMemory = Math.min(mActivityManager.memoryClass * ByteConstants.MB, Integer.MAX_VALUE)
        return if (maxMemory < 32 * ByteConstants.MB) {
            4 * ByteConstants.MB
        } else if (maxMemory < 64 * ByteConstants.MB) {
            6 * ByteConstants.MB
        } else {
            maxMemory / 4
        }
    }
}