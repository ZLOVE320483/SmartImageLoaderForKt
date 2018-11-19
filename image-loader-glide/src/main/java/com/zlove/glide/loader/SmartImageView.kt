package com.zlove.glide.loader

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.AttributeSet
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.zlove.core.DisplayRequest
import com.zlove.core.DisplayRequestBuilder
import com.zlove.core.listener.ISmartImageView
import com.zlove.glide.loader.transform.CircleTransformation
import com.zlove.glide.loader.transform.RoundCornersTransformation
import com.zlove.glide.loader.transform.ScaleTypeTransformation
import java.util.*

class SmartImageView: ImageView, ISmartImageView {

    companion object {
        // 长图，宽图比例阈值
        const val RATIO_OF_LARGE = 3
        // 长图截取后的高宽比（宽图截取后的宽高比）
        const val HW_RATIO = 3
    }

    private var mAttrs: SmartImageViewAttrs? = null
    private var mRequestOptions: RequestOptions? = null
    private var mTransformationList: ArrayList<BitmapTransformation>? = null
    private var mIsCircle = false
    private var mIsRoundCorner = false

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @SuppressLint("CheckResult")
    override fun display(displayRequest: DisplayRequest) {
        parseAttr()
        onDisplay(displayRequest)
    }

    override fun display(resourceId: Int) {
        val displayRequest = DisplayRequestBuilder(resourceId).build()
        display(displayRequest)
    }

    override fun display(url: String) {
        val displayRequest = DisplayRequestBuilder(url).build()
        display(displayRequest)
    }

    override fun display(uri: Uri) {
        val displayRequest = DisplayRequestBuilder(uri).build()
        display(displayRequest)
    }

    @SuppressLint("CheckResult")
    override fun load(context: Context, url: String?) {
        if (null == url || url.isEmpty()) {
            return
        }
        Glide.with(context).asFile().load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
    }

    override fun load(displayRequest: DisplayRequest) {
        val imageLoadListener = displayRequest.mImageLoadListener
        val requestBuilder = Glide.with(displayRequest.mContext).asBitmap()
        requestBuilder.load(displayRequest.mUri)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        //resolveBitmap(resource);
                        imageLoadListener?.let {
                            it.onCompleted(resource)
                        }
                    }
                })
    }

    @SuppressLint("CheckResult")
    private fun onDisplay(displayRequest: DisplayRequest) {
        if (mRequestOptions == null) {
            mRequestOptions = RequestOptions()
        }

        //placeholder
        if (displayRequest.mPlaceholder != -1) {
            mRequestOptions?.placeholder(displayRequest.mPlaceholder)
        }
        if (displayRequest.mFailureImage != -1) {
            mRequestOptions?.fallback(displayRequest.mFailureImage)
        }

        //width height
        if (displayRequest.mWidth > 0 && displayRequest.mHeight > 0) {
            mRequestOptions?.override(displayRequest.mWidth, displayRequest.mHeight)
        }

        buildTransformations(displayRequest)

        val requestManager = Glide.with(displayRequest.mContext)
        val requestBuilder = requestManager.load(displayRequest.mUri)
        buildImageDisplayListener(displayRequest, requestBuilder)
        mRequestOptions?.let { requestBuilder.apply(it).into(this) }
    }

    private fun resolveBitmap(resource: Bitmap): Bitmap {
        val srcWidth = resource.width
        val srcHeight = resource.height

        if (srcWidth > srcHeight) {
            val srcWHRatio = srcWidth.toFloat() / srcHeight
            // 宽图
            if (srcWHRatio > RATIO_OF_LARGE) {
                return Bitmap.createBitmap(resource, 0, 0, srcHeight * HW_RATIO, srcHeight)
            }
        } else {
            val srcHWRatio = srcHeight.toFloat() / srcWidth
            // 长图
            if (srcHWRatio > RATIO_OF_LARGE) {
                return Bitmap.createBitmap(resource, 0, 0, srcWidth, srcWidth * HW_RATIO)
            }
        }
        return resource
    }

    //parse attr
    @SuppressLint("CheckResult")
    private fun parseAttr() {
        mAttrs?.let {
            if (mRequestOptions == null) {
                mRequestOptions = RequestOptions()
            }
            //placeholder、failureImage
            if (it.mPlaceholderImage > 0) {
                mRequestOptions?.placeholder(it.mPlaceholderImage)
            }
            if (it.mFailureImage > 0) {
                mRequestOptions?.fallback(it.mFailureImage)
                mRequestOptions?.error(it.mFailureImage)
            }

            //Circle、RoundCorner
            if (it.mRoundAsCircle) {
                mIsCircle = true
                if (mTransformationList == null) {
                    mTransformationList = ArrayList()
                }
                mTransformationList?.add(CircleTransformation(it))
            }
            if (it.mRoundedCornerRadius > 0) {
                mIsRoundCorner = true
                if (mTransformationList == null) {
                    mTransformationList = ArrayList()
                }
                mTransformationList?.add(RoundCornersTransformation(it))
            }
            if (it.mActualImageScaleType >= 0) {
                if (mTransformationList == null) {
                    mTransformationList = ArrayList()
                }
                mTransformationList?.add(ScaleTypeTransformation(it.mActualImageScaleType))
            }
        }
    }

    //image loader
    @SuppressLint("CheckResult")
    private fun buildTransformations(displayRequest: DisplayRequest) {
        if (mTransformationList == null) {
            mTransformationList = ArrayList()
        }
        displayRequest.mCircleOptions?.let {
            when {
                it.mRoundAsCircle && !mIsCircle ->  {mTransformationList?.add(CircleTransformation(it)) }
                !mIsRoundCorner -> {mTransformationList?.add(RoundCornersTransformation(it)) }
                else -> { }
            }
        }
        mTransformationList?.let {
            if (!it.isEmpty()) {
                val transformations = it.toTypedArray()
                mRequestOptions?.transforms(*transformations)
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun buildImageDisplayListener(displayRequest: DisplayRequest, requestBuilder: RequestBuilder<Drawable>) {
        val imageDisplayListener = displayRequest.mImageDisplayListener
        imageDisplayListener?.let {
            requestBuilder.listener(object : RequestListener<Drawable> {

                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    it.onFailed(displayRequest.mUri, this@SmartImageView, e)
                    return false
                }

                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    it.onComplete(displayRequest.mUri, this@SmartImageView, resource)
                    return false
                }
            })
        }
    }
}