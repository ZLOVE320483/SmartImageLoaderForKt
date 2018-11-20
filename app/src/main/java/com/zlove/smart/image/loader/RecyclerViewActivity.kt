package com.zlove.smart.image.loader

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_view.*
import java.util.*

class RecyclerViewActivity: AppCompatActivity() {

    lateinit var mUrlList: MutableList<ImageModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        addDataList()
        val imageAdapter = ImageAdapter(this, mUrlList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = imageAdapter
    }

    private fun addDataList() {
        mUrlList = ArrayList()
        mUrlList.add(ImageModel("https://images.unsplash.com/photo-1538592487700-be96de73306f?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=848b04992148fe8218e979c9ca976036&auto=format&fit=crop&w=1950&q=80"))
        mUrlList.add(ImageModel("https://images.unsplash.com/photo-1502786129293-79981df4e689?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=ca8929ea79462f07dc08e1245b63aa75&auto=format&fit=crop&w=2089&q=80"))
        mUrlList.add(ImageModel("https://images.unsplash.com/photo-1437209484568-e63b90a34f8b?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=b250a2e715b60e123c6b5ba13b299e7e&auto=format&fit=crop&w=1966&q=80"))
        mUrlList.add(ImageModel("https://images.unsplash.com/photo-1526995410062-b9e684e28d8f?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=9c96ee874ad97f925e6f1eb6356bafa6&auto=format&fit=crop&w=1950&q=80"))
        mUrlList.add(ImageModel("https://images.unsplash.com/photo-1540028317582-ab90fe7c343f?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=476d592569280c1930ea145466dd8154&auto=format&fit=crop&w=1950&q=80"))
        mUrlList.add(ImageModel("https://images.unsplash.com/photo-1512441933491-7b8cc442ed32?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=63b08328f0d0c24064c5198d620e05fc&auto=format&fit=crop&w=1950&q=80"))
        mUrlList.add(ImageModel("https://images.unsplash.com/photo-1539946309076-4daf2ea73899?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=04826f76a0299f17d856c70ddf879ea9&auto=format&fit=crop&w=1950&q=80"))
        mUrlList.add(ImageModel("https://images.unsplash.com/photo-1532274402911-5a369e4c4bb5?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=81a5f1725ca68c549e0054dcfdf269de&auto=format&fit=crop&w=1950&q=80"))
        mUrlList.add(ImageModel("https://images.unsplash.com/photo-1539689816072-86231273b4d6?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjEyMDd9&s=0eb0fa1263e88a83dd44d1fb3b9df81f&auto=format&fit=crop&w=1950&q=80"))
        mUrlList.add(ImageModel("https://images.unsplash.com/photo-1539673433035-50b0c3110f1b?ixlib=rb-0.3.5&s=3681e6c7ce23cf8465f209866e8357fd&auto=format&fit=crop&w=1951&q=80"))
    }
}