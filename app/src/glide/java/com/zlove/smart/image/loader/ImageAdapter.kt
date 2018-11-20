package com.zlove.smart.image.loader

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zlove.core.DisplayRequest
import com.zlove.core.ImageLoader
import kotlinx.android.synthetic.glide.item_image.view.*

class ImageAdapter(private val mContext: Context, private val dataList: List<ImageModel>): RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_image, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val displayRequest = ImageLoader.from(dataList[i].mUrl)?.with(mContext)?.placeholder(R.mipmap.loading)?.build()
        holder.bind(displayRequest)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(displayRequest: DisplayRequest?) {
            displayRequest?.let {
                val params = itemView.smart_image_view.layoutParams
                itemView.layoutParams = params
                ImageLoader.display(itemView.smart_image_view, it)
            }
        }
    }
}