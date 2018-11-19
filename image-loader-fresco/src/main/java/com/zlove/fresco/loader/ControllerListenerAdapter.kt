package com.zlove.fresco.loader

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.net.Uri
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.drawee.view.GenericDraweeView
import com.facebook.imagepipeline.image.ImageInfo
import com.zlove.core.listener.ImageDisplayListener
import java.lang.ref.WeakReference

class ControllerListenerAdapter: BaseControllerListener<ImageInfo> {

    private var mViewRef: WeakReference<GenericDraweeView>? = null
    private var mImageDisplayListener: ImageDisplayListener? = null
    private var mUri: Uri? = null

    constructor() : super()

    companion object {
        fun with(draweeView: GenericDraweeView, imageDisplayListener: ImageDisplayListener?,
                 uri: Uri?): ControllerListenerAdapter {
            val adapter = ControllerListenerAdapter()
            adapter.mViewRef = WeakReference(draweeView)
            adapter.mImageDisplayListener = imageDisplayListener
            adapter.mUri = uri
            return adapter
        }
    }

    override fun onSubmit(id: String?, callerContext: Any?) {
        mImageDisplayListener?.onStart(mUri, mViewRef?.get())
    }

    override fun onFinalImageSet(id: String?, imageInfo: ImageInfo?, animatable: Animatable?) {
        if (imageInfo == null)
            return

        mImageDisplayListener?.onComplete(mUri, mViewRef?.get(), animatable as Drawable)
    }

    override fun onFailure(id: String?, throwable: Throwable?) {
        mImageDisplayListener?.onFailed(mUri, mViewRef?.get(), throwable)
    }
}