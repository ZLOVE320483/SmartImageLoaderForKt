package com.zlove.core

enum class ImagePipelinePriority {

    LOW, MEDIUM, HIGH;

    companion object {
        fun getHigherPriority(
                priority1: ImagePipelinePriority?,
                priority2: ImagePipelinePriority?): ImagePipelinePriority? {
            if (priority1 == null) {
                return priority2
            }
            if (priority2 == null) {
                return priority1
            }
            return if (priority1.ordinal > priority2.ordinal) {
                priority1
            } else {
                priority2
            }
        }
    }

}
