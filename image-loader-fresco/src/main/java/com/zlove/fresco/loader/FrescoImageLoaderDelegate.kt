package com.zlove.fresco.loader

import android.content.Context
import android.net.Uri
import com.facebook.drawee.backends.pipeline.Fresco
import com.zlove.core.DisplayRequest
import com.zlove.core.DisplayRequestBuilder
import com.zlove.core.ImageLoader
import com.zlove.core.ImageLoaderDelegate
import com.zlove.core.listener.ConfigProvider
import com.zlove.core.listener.ISmartImageView
import java.io.File

class FrescoImageLoaderDelegate: ImageLoaderDelegate {

    override fun init(configProvider: ConfigProvider) {
        Fresco.initialize(configProvider.getApplication(), ImagePipelineConfigFactory.getImagePipelineConfig(configProvider))
    }

    override fun from(resourceId: Int): DisplayRequestBuilder? {
        val uri = Uri.parse("res://${ImageLoader.sPkgName}/$resourceId")
        return DisplayRequestBuilder(uri)
    }

    override fun from(file: File): DisplayRequestBuilder? {
        return DisplayRequestBuilder(Uri.fromFile(file))
    }

    override fun from(url: String): DisplayRequestBuilder? {
        return DisplayRequestBuilder(url)
    }

    override fun from(uri: Uri): DisplayRequestBuilder? {
        return DisplayRequestBuilder(uri)
    }

    override fun from(urlList: List<String>): DisplayRequestBuilder? {
        return DisplayRequestBuilder(urlList)
    }

    override fun display(imageView: ISmartImageView, displayRequest: DisplayRequest) {
        imageView.display(displayRequest)
    }

    override fun load(displayRequest: DisplayRequest) {
    }

    override fun load(imageView: ISmartImageView, displayRequest: DisplayRequest) {
        imageView.load(displayRequest)
    }

    override fun load(imageView: ISmartImageView, context: Context, url: String) {
        imageView.load(context, url)
    }

    override fun clearMemoryCache(configProvider: ConfigProvider) {
        Fresco.getImagePipeline().clearMemoryCaches()
    }

    override fun clearDiskCache(context: Context) {
        Fresco.getImagePipeline().clearDiskCaches()
    }
}