package com.zlove.core

import android.content.Context
import android.net.Uri
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import com.zlove.core.listener.ConfigProvider
import java.io.File

interface ImageLoaderDelegate {
    
    fun init(@NonNull configProvider: ConfigProvider)
    
    fun from(@DrawableRes resourceId: Int): DisplayRequestBuilder?

    fun from(file: File): DisplayRequestBuilder?

    fun from(url: String): DisplayRequestBuilder?

    fun from(uri: Uri): DisplayRequestBuilder?

    fun from(urlList: List<String>): DisplayRequestBuilder?

    fun display(imageView: ISmartImageView, displayRequest: DisplayRequest)

    fun load(displayRequest: DisplayRequest)

    fun load(imageView: ISmartImageView, displayRequest: DisplayRequest)

    fun load(imageView: ISmartImageView, context: Context, url: String)

    fun clearMemoryCache(configProvider: ConfigProvider)

    fun clearDiskCache(context: Context)
}