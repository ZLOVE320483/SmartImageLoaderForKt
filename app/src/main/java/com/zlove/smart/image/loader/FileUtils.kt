package com.zlove.smart.image.loader

import android.content.Context
import android.os.Environment
import android.os.Environment.MEDIA_MOUNTED
import java.io.File
import java.lang.reflect.Method

object FileUtils {

    fun getExternalPictureCacheDir(): File? {
        if (!isSdcardAvailable() || !isSdcardWritable()) {
            return null
        }
        val pictureCacheDir = File(getCacheDir(MainApplication.mContext), "picture")
        ensureDirExists(pictureCacheDir)
        return pictureCacheDir
    }

    private fun ensureDirExists(dir: File) {
        if (!dir.exists()) {
            dir.mkdirs()
        }
    }

    private fun getCacheDir(context: Context): File {
        var cacheDir = context.externalCacheDir
        if (cacheDir == null) {
            cacheDir = context.cacheDir
        }
        return cacheDir
    }

    private fun isSdcardAvailable(): Boolean {
        var result: Boolean
        try {
            val state = Environment.getExternalStorageState()
            result = Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
        } catch (e: Exception) {
            result = isSdcardReadable()
        }

        return result
    }

    private fun isSdcardReadable(): Boolean {
        val storageManager = MainApplication.mContext.getSystemService(Context.STORAGE_SERVICE)
        var methodGetVolumeList: Method? = null
        var methodGetVolumeState: Method? = null
        try {
            methodGetVolumeList = storageManager.javaClass.getMethod("getVolumeList")
            methodGetVolumeState = storageManager.javaClass.getMethod("getVolumeState", String::class.java)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }

        if (null == methodGetVolumeList || null == methodGetVolumeState) {
            return false
        }
        try {
            val arrayOfStorageVolume = methodGetVolumeList.invoke(storageManager) as Array<Any>
            if (arrayOfStorageVolume.isEmpty()) {
                return false
            }
            val methodGetPath = arrayOfStorageVolume[0].javaClass.getMethod("getPath")
            val methodIsEmulated = arrayOfStorageVolume[0].javaClass.getMethod("isEmulated")
            if (null == methodGetPath || null == methodIsEmulated) {
                return false
            }
            var volumePath: String?
            for (anArrayOfStorageVolume in arrayOfStorageVolume) {
                if (!(methodIsEmulated.invoke(anArrayOfStorageVolume) as Boolean)) {
                    continue
                }
                volumePath = methodGetPath.invoke(anArrayOfStorageVolume) as String
                if (methodGetVolumeState.invoke(storageManager, *arrayOf<Any>(volumePath)) == "mounted") {
                    return true
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return false
    }

    private fun isSdcardWritable(): Boolean {
        try {
            return MEDIA_MOUNTED == Environment.getExternalStorageState()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}