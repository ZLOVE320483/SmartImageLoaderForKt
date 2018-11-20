package com.zlove.smart.image.loader

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.zlove.core.CircleOptions
import com.zlove.core.ImageLoader
import kotlinx.android.synthetic.fresco.activity_main.*
import kotlinx.android.synthetic.fresco.include_content.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        setFABClickListener()

        displayMainImage()
        displayCircleImage()
        displayRoundCornerImage()
        displayGifImage()
        displayWebpImage()
    }

    private fun displayMainImage() {
        val displayRequest = ImageLoader.from(R.mipmap.image2)?.with(this)?.build()
        displayRequest?.let { ImageLoader.display(main_image, it) }
    }

    private fun displayCircleImage() {
        val url = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4024775826,1444293055&fm=26&gp=0.jpg"
        val displayRequest = ImageLoader.from(url)?.with(this)?.placeholder(R.mipmap.loading)?.circle(buildCircleOptions())?.build()
        displayRequest?.let { ImageLoader.display(content_container.glide_circle, it) }
    }

    private fun buildCircleOptions(): CircleOptions {
        val builder = CircleOptions.Builder()
                .roundAsCircle(true)
                .borderWidth(Utils.dp2px(this, 6.0f).toFloat())
                .borderColor(ContextCompat.getColor(this, R.color.brown))
        return builder.build()
    }

    private fun displayRoundCornerImage() {
        val displayRequest = ImageLoader.from(R.mipmap.image1)?.with(this)?.placeholder(R.mipmap.loading)?.circle(buildRoundCorner())?.build()
        displayRequest?.let { ImageLoader.display(content_container.glide_round_corner, it) }
    }

    private fun buildRoundCorner(): CircleOptions {
        val builder = CircleOptions.Builder()
                .cornersRadius(Utils.dp2px(this, 10.0f).toFloat())
                .borderWidth(Utils.dp2px(this, 6.0f).toFloat())
                .borderColor(ContextCompat.getColor(this, R.color.brown))
        return builder.build()
    }

    private fun displayGifImage() {
        val displayRequest = ImageLoader.from(R.mipmap.image15)?.with(this)?.autoPlayAnimations(true)?.build()
        displayRequest?.let { ImageLoader.display(content_container.glide_gif, it) }
    }

    private fun displayWebpImage() {
        val displayRequest = ImageLoader.from(R.mipmap.image16)?.with(this)?.autoPlayAnimations(true)?.build()
        displayRequest?.let { ImageLoader.display(content_container.glide_webp, it) }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        collapsing_toolbar.title = resources.getString(R.string.app_name)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
    }

    private fun setFABClickListener() {
        main_fab.setOnClickListener { startActivity(Intent(this@MainActivity, RecyclerViewActivity::class.java)) }
    }
}
