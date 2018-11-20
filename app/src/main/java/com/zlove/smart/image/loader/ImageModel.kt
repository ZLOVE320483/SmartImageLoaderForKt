package com.zlove.smart.image.loader
 
class ImageModel {

    lateinit var mUrl:String
    var mUrlList: List<String>? = null
    var mAspectRatio: Float = 0.0f
    var width: Int = 0
    var height: Int = 0

    constructor(mUrl: String) {
        this.mUrl = mUrl
    }

    constructor( mUrl: String,  mUrlList: List<String>,
                 mAspectRatio: Float,  width: Int,
                 height: Int): this(mUrl) {
        this.mUrlList = mUrlList
        this.mAspectRatio = mAspectRatio
        this.width = width
        this.height = height
    }
}