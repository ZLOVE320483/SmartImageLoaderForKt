package com.zlove.smart.image.loader

import android.app.Application
import android.content.Context

abstract class MainApplication: Application() {

    companion object {
        lateinit var mContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
        initImageLoader()
    }

    protected abstract fun initImageLoader()
}