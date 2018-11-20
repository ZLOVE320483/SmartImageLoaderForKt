package com.zlove.smart.image.loader

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zlove.core.DisplayRequest
import com.zlove.core.ImageLoader
import com.zlove.core.listener.ImageLoadListener
import kotlinx.android.synthetic.fresco.item_image.view.*

class ImageAdapter(private val mContext: Context, private val dataList: List<ImageModel>): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    companion object {
        const val RATIO_OF_LARGE = 3
        const val HW_RATIO = 3
    }

    init {
        for (model in dataList) {
            val builder = ImageLoader.from(model.mUrl)?.with(mContext)
            val imageLoadListener = object : ImageLoadListener {

                override fun onCompleted(bitmap: Bitmap?) {
                    bitmap?.let {
                        model.width = it.width
                        model.height = it.height
                        model.mAspectRatio = it.width / it.height.toFloat()
                    }
                }

                override fun onCanceled() {
                }

                override fun onFailed(throwable: Throwable?) {
                }

                override fun onProgress(progress: Float) {
                }
            }
            builder?.let {
                it.imageLoadListener(imageLoadListener)
                ImageLoader.load(it.build())
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_image, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val displayRequest = ImageLoader.from(dataList[i].mUrl)?.with(mContext)?.placeholder(R.mipmap.loading)?.build()
        holder.bind(displayRequest, dataList[i].width, dataList[i].height)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(displayRequest: DisplayRequest?, width: Int, height: Int) {
            displayRequest?.let {
                val params = itemView.smart_image_view.layoutParams
                aspectRatio(params, width, height)
                itemView.layoutParams = params
                ImageLoader.display(itemView.smart_image_view, it)
            }
        }

        private fun aspectRatio(params: ViewGroup.LayoutParams, srcWidth: Int, srcHeight: Int) {
            if (srcWidth > srcHeight) {
                val srcWHRatio = srcWidth.toFloat() / srcHeight
                // 宽图
                if (srcWHRatio > RATIO_OF_LARGE) {
                    params.width = srcHeight * HW_RATIO
                    params.height = srcHeight
                }
            } else {
                val srcHWRatio = srcHeight.toFloat() / srcWidth
                // 长图
                if (srcHWRatio > RATIO_OF_LARGE) {
                    params.width = srcWidth
                    params.height = srcWidth * HW_RATIO
                }
            }
        }
    }
}