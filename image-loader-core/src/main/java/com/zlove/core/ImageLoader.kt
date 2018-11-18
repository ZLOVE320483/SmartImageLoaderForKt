package com.zlove.core

import android.content.Context
import android.net.Uri
import android.support.annotation.NonNull
import com.zlove.core.delegate.DefaultDelegate
import com.zlove.core.delegate.FrescoDelegate
import com.zlove.core.delegate.GlideDelegate
import com.zlove.core.listener.ConfigProvider
import java.io.File

class ImageLoader {

    companion object {

        private val DELEGATE: ImageLoaderDelegate? = findDelegate()

        lateinit var sPkgName: String

        fun init(@NonNull provider: ConfigProvider) {
            sPkgName = provider.getApplication().packageName
            DELEGATE?.init(provider)
        }

        private fun findDelegate(): ImageLoaderDelegate? {
            var delegate = FrescoDelegate.findDelegate()
            return delegate?:GlideDelegate.findDelegate()?:DefaultDelegate.findDelegate()
        }

        fun from(resourceId: Int): DisplayRequestBuilder? {
            return DELEGATE?.from(resourceId)
        }

        fun from(file: File): DisplayRequestBuilder? {
            return DELEGATE?.from(file)
        }

        fun from(url: String): DisplayRequestBuilder? {
            return DELEGATE?.from(url)
        }

        fun from(uri: Uri): DisplayRequestBuilder? {
            return DELEGATE?.from(uri)
        }

        fun from(urlList: List<String>): DisplayRequestBuilder? {
            return DELEGATE?.from(urlList)
        }

        fun display(imageView: ISmartImageView, displayRequest: DisplayRequest) {
            DELEGATE?.display(imageView, displayRequest)
        }

        fun load(displayRequest: DisplayRequest) {
            DELEGATE?.load(displayRequest)
        }

        fun load(imageView: ISmartImageView, context: Context, url: String) {
            DELEGATE?.load(imageView, context, url)
        }

        fun load(imageView: ISmartImageView, displayRequest: DisplayRequest) {
            DELEGATE?.load(imageView, displayRequest)
        }

        fun clearMemoryCache(configProvider: ConfigProvider) {
            DELEGATE?.clearMemoryCache(configProvider)
        }

        fun clearDiskCache(context: Context) {
            DELEGATE?.clearDiskCache(context)
        }
    }
}