package com.zlove.core.listener

import android.app.Application
import java.io.File

interface ConfigProvider {

    fun getApplication(): Application
    fun getDiskCacheDir(): File?
}