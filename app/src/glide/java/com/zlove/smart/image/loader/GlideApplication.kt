package com.zlove.smart.image.loader

import android.app.Application
import com.zlove.core.ImageLoader
import com.zlove.core.listener.ConfigProvider
import java.io.File

class GlideApplication: MainApplication() {

    override fun initImageLoader() {
        val configProvider = object : ConfigProvider {

            override fun getApplication(): Application {
                return this@GlideApplication
            }

            override fun getDiskCacheDir(): File? {
                return FileUtils.getExternalPictureCacheDir()
            }
        }

        ImageLoader.init(configProvider)
    }
}