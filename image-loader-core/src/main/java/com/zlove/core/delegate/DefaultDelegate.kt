package com.zlove.core.delegate

import android.content.Context
import android.net.Uri
import com.zlove.core.DisplayRequest
import com.zlove.core.DisplayRequestBuilder
import com.zlove.core.ISmartImageView
import com.zlove.core.ImageLoaderDelegate
import com.zlove.core.listener.ConfigProvider
import java.io.File

class DefaultDelegate {

    companion object {
        fun findDelegate(): ImageLoaderDelegate? {

            return object : ImageLoaderDelegate {
                override fun init(configProvider: ConfigProvider) {
                }

                override fun from(resourceId: Int): DisplayRequestBuilder? {
                    return null
                }

                override fun from(file: File): DisplayRequestBuilder? {
                    return null
                }

                override fun from(url: String): DisplayRequestBuilder? {
                    return null
                }

                override fun from(uri: Uri): DisplayRequestBuilder? {
                    return null
                }

                override fun from(urlList: List<String>): DisplayRequestBuilder? {
                    return null
                }

                override fun display(imageView: ISmartImageView, displayRequest: DisplayRequest) {
                }

                override fun load(displayRequest: DisplayRequest) {
                }

                override fun load(imageView: ISmartImageView, displayRequest: DisplayRequest) {
                }

                override fun load(imageView: ISmartImageView, context: Context, url: String) {
                }

                override fun clearMemoryCache(configProvider: ConfigProvider) {
                }

                override fun clearDiskCache(context: Context) {
                }
            }

        }
    }

}