package com.zlove.core.listener

import android.content.Context
import android.net.Uri
import com.zlove.core.DisplayRequest

interface ISmartImageView {

    fun display(displayRequest: DisplayRequest)
    fun display(resourceId: Int)
    fun display(url: String)
    fun display(uri: Uri)
    fun load(context: Context, url: String?)
    fun load(displayRequest: DisplayRequest)
}