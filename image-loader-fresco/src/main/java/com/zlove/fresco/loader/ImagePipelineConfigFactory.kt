package com.zlove.fresco.loader

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import com.facebook.cache.disk.DiskCacheConfig
import com.facebook.common.disk.NoOpDiskTrimmableRegistry
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.zlove.core.listener.ConfigProvider
import okhttp3.OkHttpClient

class ImagePipelineConfigFactory {

    companion object {
        private const val IMAGE_PIPELINE_CACHE_DIR = "fresco_cache"

        fun getImagePipelineConfig(configProvider: ConfigProvider): ImagePipelineConfig {
            val application = configProvider.getApplication()

            val activityManager = application.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val configBuilder = OkHttpImagePipelineConfigFactory
                    .newBuilder(application, OkHttpClient())
                    .setBitmapMemoryCacheParamsSupplier(FrescoMemoryCacheParamsSupplier(activityManager))
                    .setMemoryTrimmableRegistry(FrescoMemoryTrimmableRegistry.get())

            configureDiskCaches(configBuilder, configProvider)

            configBuilder.setBitmapsConfig(Bitmap.Config.RGB_565).isDownsampleEnabled = true

            return configBuilder.build()
        }

        private fun configureDiskCaches(configBuilder: ImagePipelineConfig.Builder, configProvider: ConfigProvider) {
            val diskCacheConfig = DiskCacheConfig.newBuilder(configProvider.getApplication())
                    .setBaseDirectoryPath(configProvider.getDiskCacheDir())
                    .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)
                    .setDiskTrimmableRegistry(NoOpDiskTrimmableRegistry.getInstance())
                    .build()

            configBuilder.setMainDiskCacheConfig(diskCacheConfig)
        }
    }
}