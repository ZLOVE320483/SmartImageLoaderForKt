package com.zlove.glide.loader

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Looper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.zlove.core.DisplayRequest
import com.zlove.core.DisplayRequestBuilder
import com.zlove.core.ImageLoaderDelegate
import com.zlove.core.listener.ConfigProvider
import com.zlove.core.listener.ISmartImageView
import java.io.File

class GlideImageLoaderDelegate: ImageLoaderDelegate {

    override fun init(configProvider: ConfigProvider) {
    }

    override fun from(resourceId: Int): DisplayRequestBuilder? {
        return DisplayRequestBuilder(resourceId)
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
        val imageLoadListener = displayRequest.mImageLoadListener
        val requestBuilder = Glide.with(displayRequest.mContext).asBitmap()
        requestBuilder.load(displayRequest.mUri)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        imageLoadListener?.let { it.onCompleted(resource) }
                    }
                })
    }

    override fun load(imageView: ISmartImageView, displayRequest: DisplayRequest) {
        imageView.load(displayRequest)
    }

    override fun load(imageView: ISmartImageView, context: Context, url: String) {
        imageView.load(context, url)
    }

    override fun clearMemoryCache(configProvider: ConfigProvider) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Glide.get(configProvider.getApplication()).clearMemory()
        }
    }

    override fun clearDiskCache(context: Context) {
        Glide.get(context).clearDiskCache()
    }
}