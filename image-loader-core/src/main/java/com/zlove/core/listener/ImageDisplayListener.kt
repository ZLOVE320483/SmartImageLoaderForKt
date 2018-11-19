package com.zlove.core.listener

import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View

interface ImageDisplayListener {
    fun onStart(uri: Uri?, view: View?)
    fun onComplete(uri: Uri?, view: View?, drawable:Drawable?)
    fun onFailed(uri: Uri?, view: View?, throwable: Throwable?)
}