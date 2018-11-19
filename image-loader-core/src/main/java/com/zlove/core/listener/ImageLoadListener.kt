package com.zlove.core.listener

import android.graphics.Bitmap

interface ImageLoadListener {
    fun onCompleted(bitmap: Bitmap?)
    fun onCanceled()
    fun onFailed(throwable: Throwable?)
    fun onProgress(progress: Float)
}