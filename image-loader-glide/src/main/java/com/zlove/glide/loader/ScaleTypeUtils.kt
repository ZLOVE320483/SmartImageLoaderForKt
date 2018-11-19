package com.zlove.glide.loader

import android.graphics.Matrix
import android.graphics.Rect

class ScaleTypeUtils {

    interface ScaleType {

        /**
         * Gets transformation matrix based on the scale type.
         * @param outTransform out matrix to store result
         * @param parentBounds parent bounds
         * @param childWidth child width
         * @param childHeight child height
         * @param focusX focus point x coordinate, relative [0...1]
         * @param focusY focus point y coordinate, relative [0...1]
         * @return same reference to the out matrix for convenience
         */
        fun getTransform(
                outTransform: Matrix,
                parentBounds: Rect,
                childWidth: Int,
                childHeight: Int,
                focusX: Float,
                focusY: Float): Matrix

        companion object {

            /**
             * Scales width and height independently, so that the child matches the parent exactly.
             * This may change the aspect ratio of the child.
             */
            val FIT_XY = ScaleTypeFitXY.INSTANCE

            /**
             * Scales the child so that it fits entirely inside the parent. At least one dimension (width or
             * height) will fit exactly. Aspect ratio is preserved.
             * Child is aligned to the top-left corner of the parent.
             */
            val FIT_START = ScaleTypeFitStart.INSTANCE

            /**
             * Scales the child so that it fits entirely inside the parent. At least one dimension (width or
             * height) will fit exactly. Aspect ratio is preserved.
             * Child is centered within the parent's bounds.
             */
            val FIT_CENTER = ScaleTypeFitCenter.INSTANCE

            /**
             * Scales the child so that it fits entirely inside the parent. At least one dimension (width or
             * height) will fit exactly. Aspect ratio is preserved.
             * Child is aligned to the bottom-right corner of the parent.
             */
            val FIT_END = ScaleTypeFitEnd.INSTANCE

            /**
             * Performs no scaling.
             * Child is centered within parent's bounds.
             */
            val CENTER = ScaleTypeCenter.INSTANCE

            /**
             * Scales the child so that it fits entirely inside the parent. Unlike FIT_CENTER, if the child
             * is smaller, no up-scaling will be performed. Aspect ratio is preserved.
             * Child is centered within parent's bounds.
             */
            val CENTER_INSIDE = ScaleTypeCenterInside.INSTANCE

            /**
             * Scales the child so that both dimensions will be greater than or equal to the corresponding
             * dimension of the parent. At least one dimension (width or height) will fit exactly.
             * Child is centered within parent's bounds.
             */
            val CENTER_CROP = ScaleTypeCenterCrop.INSTANCE

            /**
             * Scales the child so that both dimensions will be greater than or equal to the corresponding
             * dimension of the parent. At least one dimension (width or height) will fit exactly.
             * The child's focus point will be centered within the parent's bounds as much as possible
             * without leaving empty space.
             * It is guaranteed that the focus point will be visible and centered as much as possible.
             * If the focus point is set to (0.5f, 0.5f), result will be equivalent to CENTER_CROP.
             */
            val FOCUS_CROP = ScaleTypeFocusCrop.INSTANCE
        }
    }

    abstract class AbstractScaleType : ScaleType {

        override fun getTransform(
                outTransform: Matrix,
                parentRect: Rect,
                childWidth: Int,
                childHeight: Int,
                focusX: Float,
                focusY: Float): Matrix {
            val sX = parentRect.width().toFloat() / childWidth.toFloat()
            val sY = parentRect.height().toFloat() / childHeight.toFloat()
            getTransformImpl(outTransform, parentRect, childWidth, childHeight, focusX, focusY, sX, sY)
            return outTransform
        }

        abstract fun getTransformImpl(
                outTransform: Matrix,
                parentRect: Rect,
                childWidth: Int,
                childHeight: Int,
                focusX: Float,
                focusY: Float,
                scaleX: Float,
                scaleY: Float)
    }

    private class ScaleTypeFitXY : AbstractScaleType() {

        override fun getTransformImpl(
                outTransform: Matrix,
                parentRect: Rect,
                childWidth: Int,
                childHeight: Int,
                focusX: Float,
                focusY: Float,
                scaleX: Float,
                scaleY: Float) {
            val dx = parentRect.left.toFloat()
            val dy = parentRect.top.toFloat()
            outTransform.setScale(scaleX, scaleY)
            outTransform.postTranslate((dx + 0.5f).toInt().toFloat(), (dy + 0.5f).toInt().toFloat())
        }

        override fun toString(): String {
            return "fit_xy"
        }

        companion object {

            val INSTANCE: ScaleType = ScaleTypeFitXY()
        }
    }

    private class ScaleTypeFitStart : AbstractScaleType() {

        override fun getTransformImpl(
                outTransform: Matrix,
                parentRect: Rect,
                childWidth: Int,
                childHeight: Int,
                focusX: Float,
                focusY: Float,
                scaleX: Float,
                scaleY: Float) {
            val scale = Math.min(scaleX, scaleY)
            val dx = parentRect.left.toFloat()
            val dy = parentRect.top.toFloat()
            outTransform.setScale(scale, scale)
            outTransform.postTranslate((dx + 0.5f).toInt().toFloat(), (dy + 0.5f).toInt().toFloat())
        }

        override fun toString(): String {
            return "fit_start"
        }

        companion object {

            val INSTANCE: ScaleType = ScaleTypeFitStart()
        }
    }

    private class ScaleTypeFitCenter : AbstractScaleType() {

        override fun getTransformImpl(
                outTransform: Matrix,
                parentRect: Rect,
                childWidth: Int,
                childHeight: Int,
                focusX: Float,
                focusY: Float,
                scaleX: Float,
                scaleY: Float) {
            val scale = Math.min(scaleX, scaleY)
            val dx = parentRect.left + (parentRect.width() - childWidth * scale) * 0.5f
            val dy = parentRect.top + (parentRect.height() - childHeight * scale) * 0.5f
            outTransform.setScale(scale, scale)
            outTransform.postTranslate((dx + 0.5f).toInt().toFloat(), (dy + 0.5f).toInt().toFloat())
        }

        override fun toString(): String {
            return "fit_center"
        }

        companion object {

            val INSTANCE: ScaleType = ScaleTypeFitCenter()
        }
    }

    private class ScaleTypeFitEnd : AbstractScaleType() {

        override fun getTransformImpl(
                outTransform: Matrix,
                parentRect: Rect,
                childWidth: Int,
                childHeight: Int,
                focusX: Float,
                focusY: Float,
                scaleX: Float,
                scaleY: Float) {
            val scale = Math.min(scaleX, scaleY)
            val dx = parentRect.left + (parentRect.width() - childWidth * scale)
            val dy = parentRect.top + (parentRect.height() - childHeight * scale)
            outTransform.setScale(scale, scale)
            outTransform.postTranslate((dx + 0.5f).toInt().toFloat(), (dy + 0.5f).toInt().toFloat())
        }

        override fun toString(): String {
            return "fit_end"
        }

        companion object {

            val INSTANCE: ScaleType = ScaleTypeFitEnd()
        }
    }

    private class ScaleTypeCenter : AbstractScaleType() {

        override fun getTransformImpl(
                outTransform: Matrix,
                parentRect: Rect,
                childWidth: Int,
                childHeight: Int,
                focusX: Float,
                focusY: Float,
                scaleX: Float,
                scaleY: Float) {
            val dx = parentRect.left + (parentRect.width() - childWidth) * 0.5f
            val dy = parentRect.top + (parentRect.height() - childHeight) * 0.5f
            outTransform.setTranslate((dx + 0.5f).toInt().toFloat(), (dy + 0.5f).toInt().toFloat())
        }

        override fun toString(): String {
            return "center"
        }

        companion object {

            val INSTANCE: ScaleType = ScaleTypeCenter()
        }
    }

    private class ScaleTypeCenterInside : AbstractScaleType() {

        override fun getTransformImpl(
                outTransform: Matrix,
                parentRect: Rect,
                childWidth: Int,
                childHeight: Int,
                focusX: Float,
                focusY: Float,
                scaleX: Float,
                scaleY: Float) {
            val scale = Math.min(Math.min(scaleX, scaleY), 1.0f)
            val dx = parentRect.left + (parentRect.width() - childWidth * scale) * 0.5f
            val dy = parentRect.top + (parentRect.height() - childHeight * scale) * 0.5f
            outTransform.setScale(scale, scale)
            outTransform.postTranslate((dx + 0.5f).toInt().toFloat(), (dy + 0.5f).toInt().toFloat())
        }

        override fun toString(): String {
            return "center_inside"
        }

        companion object {

            val INSTANCE: ScaleType = ScaleTypeCenterInside()
        }
    }

    private class ScaleTypeCenterCrop : AbstractScaleType() {

        override fun getTransformImpl(
                outTransform: Matrix,
                parentRect: Rect,
                childWidth: Int,
                childHeight: Int,
                focusX: Float,
                focusY: Float,
                scaleX: Float,
                scaleY: Float) {
            val scale: Float
            val dx: Float
            val dy: Float
            if (scaleY > scaleX) {
                scale = scaleY
                dx = parentRect.left + (parentRect.width() - childWidth * scale) * 0.5f
                dy = parentRect.top.toFloat()
            } else {
                scale = scaleX
                dx = parentRect.left.toFloat()
                dy = parentRect.top + (parentRect.height() - childHeight * scale) * 0.5f
            }
            outTransform.setScale(scale, scale)
            outTransform.postTranslate((dx + 0.5f).toInt().toFloat(), (dy + 0.5f).toInt().toFloat())
        }

        override fun toString(): String {
            return "center_crop"
        }

        companion object {

            val INSTANCE: ScaleType = ScaleTypeCenterCrop()
        }
    }

    private class ScaleTypeFocusCrop : AbstractScaleType() {

        override fun getTransformImpl(
                outTransform: Matrix,
                parentRect: Rect,
                childWidth: Int,
                childHeight: Int,
                focusX: Float,
                focusY: Float,
                scaleX: Float,
                scaleY: Float) {
            val scale: Float
            var dx: Float
            var dy: Float
            if (scaleY > scaleX) {
                scale = scaleY
                dx = parentRect.width() * 0.5f - childWidth.toFloat() * scale * focusX
                dx = parentRect.left + Math.max(Math.min(dx, 0f), parentRect.width() - childWidth * scale)
                dy = parentRect.top.toFloat()
            } else {
                scale = scaleX
                dx = parentRect.left.toFloat()
                dy = parentRect.height() * 0.5f - childHeight.toFloat() * scale * focusY
                dy = parentRect.top + Math.max(Math.min(dy, 0f), parentRect.height() - childHeight * scale)
            }
            outTransform.setScale(scale, scale)
            outTransform.postTranslate((dx + 0.5f).toInt().toFloat(), (dy + 0.5f).toInt().toFloat())
        }

        override fun toString(): String {
            return "focus_crop"
        }

        companion object {

            val INSTANCE: ScaleType = ScaleTypeFocusCrop()
        }
    }

    /**
     * Scaletypes that have some internal state and are not static.
     */
    interface StatefulScaleType {

        /**
         * Returns the internal state. The returned object must be immutable!
         *
         * The returned state may be used for caching the result of `ScaleType.getTransform`.
         * If null state is returned, the result will not be cached. If non-null state is returned,
         * the old transformation may be used if produced with an equal state.
         */
        val state: Any
    }

    /**
     * Scale type that interpolates transform of the two underlying scale types.
     */
    class InterpolatingScaleType @JvmOverloads constructor(
            val scaleTypeFrom: ScaleType,
            val scaleTypeTo: ScaleType,
            val boundsFrom: Rect? = null,
            val boundsTo: Rect? = null) : ScaleType, StatefulScaleType {
        private val mMatrixValuesFrom = FloatArray(9)
        private val mMatrixValuesTo = FloatArray(9)
        private val mMatrixValuesInterpolated = FloatArray(9)

        /**
         * Gets the interpolating value.
         */
        /**
         * Sets the interpolating value.
         *
         * Value of 0.0 will produce the transform same as ScaleTypeFrom.
         * Value of 1.0 will produce the transform same as ScaleTypeTo.
         * Inbetween values will produce a transform that is a linear combination between the two.
         */
        var value: Float = 0.toFloat()

        override val state: Any
            get() = value

        override fun getTransform(
                transform: Matrix,
                parentBounds: Rect,
                childWidth: Int,
                childHeight: Int,
                focusX: Float,
                focusY: Float): Matrix {
            val boundsFrom = this.boundsFrom ?: parentBounds
            val boundsTo = this.boundsTo ?: parentBounds

            scaleTypeFrom.getTransform(transform, boundsFrom, childWidth, childHeight, focusX, focusY)
            transform.getValues(mMatrixValuesFrom)
            scaleTypeTo.getTransform(transform, boundsTo, childWidth, childHeight, focusX, focusY)
            transform.getValues(mMatrixValuesTo)

            for (i in 0..8) {
                mMatrixValuesInterpolated[i] = mMatrixValuesFrom[i] * (1 - value) + mMatrixValuesTo[i] * value
            }
            transform.setValues(mMatrixValuesInterpolated)
            return transform
        }

        override fun toString(): String {
            return String.format(
                    "InterpolatingScaleType(%s -> %s)",
                    scaleTypeFrom.toString(),
                    scaleTypeTo.toString())
        }
    }

}