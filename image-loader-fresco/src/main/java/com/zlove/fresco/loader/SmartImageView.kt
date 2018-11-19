package com.zlove.fresco.loader

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.AttributeSet
import com.facebook.common.executors.UiThreadImmediateExecutorService
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.generic.GenericDraweeHierarchy
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.generic.RoundingParams
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory
import com.facebook.imagepipeline.common.ImageDecodeOptions
import com.facebook.imagepipeline.common.ImageDecodeOptionsBuilder
import com.facebook.imagepipeline.common.Priority
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.core.ImagePipelineFactory
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.zlove.core.CircleOptions
import com.zlove.core.DisplayRequest
import com.zlove.core.ImagePipelinePriority
import com.zlove.core.listener.ISmartImageView
import com.zlove.core.listener.ImageLoadListener

class SmartImageView: SimpleDraweeView, ISmartImageView {

    constructor(context: Context?, hierarchy: GenericDraweeHierarchy?) : super(context, hierarchy) {
        setHierarchy(hierarchy)
    }
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun display(displayRequest: DisplayRequest) {
        if (!displayRequest.mUrlList.isEmpty()) {
            val requests = buildImageRequests(displayRequest, displayRequest.mUrlList)
            buildControllerForImageRequests(displayRequest, requests)

        } else {
            //setHierarchy(displayRequest);
            val imageRequest = buildImageRequest(displayRequest)
            buildPipelineDraweeController(displayRequest, imageRequest)
        }
    }

    override fun display(resourceId: Int?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun display(url: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun display(uri: Uri?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun load(context: Context?, url: String?) {
        if (null == url || url.isEmpty()) {
            return
        }
        if (isDownloaded(Uri.parse(url))) {
            return
        }
        val imageRequest = ImageRequest.fromUri(url)
        val imagePipeline = ImagePipelineFactory.getInstance().imagePipeline
        imagePipeline.prefetchToDiskCache(imageRequest, null)
    }

    override fun load(displayRequest: DisplayRequest) {
        val imageLoadListener = displayRequest.mImageLoadListener

        if (!displayRequest.mUrlList.isEmpty()) {
            val urlList = displayRequest.mUrlList
            for (url in urlList) {
                val imageRequest = buildImageRequest(displayRequest, com.zlove.core.utils.Utils.fromUrl(url))
                val imagePipeline = Fresco.getImagePipeline()
                val dataSource = imagePipeline.fetchDecodedImage(imageRequest, null)
                imageLoadListener.let { dataSource.subscribe(createBaseBitmapDataSubscriber(it!!), UiThreadImmediateExecutorService.getInstance()) }
            }
        } else {
            val imageRequest = buildImageRequest(displayRequest, displayRequest.mUri)
            val imagePipeline = Fresco.getImagePipeline()
            val dataSource = imagePipeline.fetchDecodedImage(imageRequest, null)
            imageLoadListener.let { dataSource.subscribe(createBaseBitmapDataSubscriber(it!!), UiThreadImmediateExecutorService.getInstance()) }
        }
    }

    private fun createBaseBitmapDataSubscriber(imageLoadListener: ImageLoadListener): BaseBitmapDataSubscriber {
        return object : BaseBitmapDataSubscriber() {
            override fun onNewResultImpl(bitmap: Bitmap?) {
                imageLoadListener.onCompleted(bitmap)
            }

            override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>) {
                imageLoadListener.onFailed(dataSource.failureCause)
            }

            override fun onProgressUpdate(dataSource: DataSource<CloseableReference<CloseableImage>>?) {
                imageLoadListener.onProgress(dataSource!!.progress)
            }

            override fun onCancellation(dataSource: DataSource<CloseableReference<CloseableImage>>?) {
                imageLoadListener.onCanceled()
            }
        }
    }

    private fun isDownloaded(uri: Uri?): Boolean {
        uri.let {
            val imageRequest = ImageRequest.fromUri(it)
            val cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(imageRequest, null)
            return ImagePipelineFactory.getInstance().mainFileCache.hasKey(cacheKey)
        }
    }

    private fun setHierarchy(displayRequest: DisplayRequest) {
        val builder = GenericDraweeHierarchyBuilder(context.resources)
        displayRequest.mActualImageScaleType.let { builder.actualImageScaleType = ScaleTypeUtils.transferActualScaleType(it!!) }
        hierarchy = builder.build()
    }

    private fun buildImageRequests(displayRequest: DisplayRequest, urlList: List<String>): Array<ImageRequest> {
        val imageRequests = ArrayList<ImageRequest>()
        for (url in urlList) {
            imageRequests.add(buildImageRequest(displayRequest, com.zlove.core.utils.Utils.fromUrl(url)))
        }
        if (imageRequests.isEmpty()) {
            return emptyArray()
        }
        val requests = arrayOfNulls<ImageRequest>(imageRequests.size)
        return imageRequests.toArray(requests)
    }

    private fun buildImageRequest(displayRequest: DisplayRequest): ImageRequest {
        return buildImageRequest(displayRequest, displayRequest.mUri)
    }

    private fun buildImageRequest(displayRequest: DisplayRequest, uri: Uri): ImageRequest {
        val builder = ImageRequestBuilder.newBuilderWithSource(uri)
        builder.setAutoRotateEnabled(displayRequest.mAutoRotate)
        //PostProcessor
        buildCropOptions(builder, displayRequest)
        //RoundingParams
        buildCircleOptions(displayRequest)
        val imageDecodeOptionsBuilder = buildImageDecodeOptions(displayRequest)
        //Priority
        val priority = buildPriority(displayRequest)

        builder.setImageDecodeOptions(imageDecodeOptionsBuilder.build())
                .setRequestPriority(priority)
                .setAutoRotateEnabled(displayRequest.mAutoRotate)

        if (displayRequest.mWidth > 0 || displayRequest.mHeight > 0) {
            val resizeOptions = buildResizeOptions(displayRequest)
            builder.resizeOptions = resizeOptions
        }
        return builder.build()
    }

    private fun buildCropOptions(builder: ImageRequestBuilder, displayRequest: DisplayRequest) {
        displayRequest.mCropOptions.let {
            builder.postprocessor = CropPostProcessor(it!!)
        }
    }

    private fun buildCircleOptions(displayRequest: DisplayRequest) {
        displayRequest.mCircleOptions.let {
            val roundingParams = hierarchy.roundingParams?: RoundingParams()

            it?.mCornersRadiiOptions.let {
                roundingParams.setCornersRadii(it!!.topLeft, it.topRight,
                        it.bottomRight, it.bottomLeft)
            }

            roundingParams.roundAsCircle = it!!.mRoundAsCircle
            roundingParams.setCornersRadius(it.mCornersRadius)
            roundingParams.borderWidth = it.mBorderWidth
            roundingParams.borderColor = it.mBorderColor
            roundingParams.overlayColor = it.mOverlayColor
            roundingParams.padding = it.mPadding
            roundingParams.roundingMethod = Utils.roundingMethodTransfer(it.mRoundingMethod)

            it.mRoundingMethod.let {
                if (it == CircleOptions.RoundingMethod.OVERLAY_COLOR) {
                    roundingParams.roundingMethod = RoundingParams.RoundingMethod.OVERLAY_COLOR
                } else {
                    roundingParams.roundingMethod = RoundingParams.RoundingMethod.BITMAP_ONLY
                }
            }
            hierarchy.roundingParams = roundingParams
        }
    }

    private fun buildImageDecodeOptions(displayRequest: DisplayRequest): ImageDecodeOptionsBuilder {
        val decodeOptionsBuilder = ImageDecodeOptions.newBuilder()
        decodeOptionsBuilder.bitmapConfig = displayRequest.mBitmapConfig
        return decodeOptionsBuilder
    }

    private fun buildResizeOptions(displayRequest: DisplayRequest): ResizeOptions {
        return ResizeOptions(displayRequest.mWidth, displayRequest.mHeight)
    }

    private fun buildPriority(displayRequest: DisplayRequest): Priority {
        when(displayRequest.mImagePipelinePriority) {
            ImagePipelinePriority.LOW -> return Priority.LOW
            ImagePipelinePriority.HIGH -> return Priority.HIGH
            else -> return Priority.MEDIUM
        }
    }

    private fun buildControllerForImageRequests(displayRequest: DisplayRequest, imageRequests: Array<ImageRequest>) {
        val builder = Fresco.newDraweeControllerBuilder()
                .setOldController(controller)
                .setAutoPlayAnimations(displayRequest.mAutoPlayAnimations)
                .setFirstAvailableImageRequests(imageRequests)
        displayRequest.mImageDisplayListener.let { builder.controllerListener = ControllerListenerAdapter.with(this, it, imageRequests[0].sourceUri) }
        val controller = builder.build()
        setController(controller)
    }

    private fun buildPipelineDraweeController(displayRequest: DisplayRequest,
                                              imageRequest: ImageRequest) {
        val builder = Fresco.newDraweeControllerBuilder()
                .setOldController(controller)
                .setAutoPlayAnimations(displayRequest.mAutoPlayAnimations)
                .setImageRequest(imageRequest)

        displayRequest.mImageDisplayListener.let { builder.controllerListener = ControllerListenerAdapter.with(this, it, displayRequest.mUri) }
        controller = builder.build()
    }
}