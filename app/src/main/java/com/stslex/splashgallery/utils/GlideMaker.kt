package com.stslex.splashgallery.utils

import android.widget.ImageView

class GlideMaker(
    val makeGlideImage: (
        url: String,
        imageView: ImageView,
        needCrop: Boolean,
        needCircleCrop: Boolean
    ) -> Unit
) {

    fun makeImage(
        url: String,
        imageView: ImageView,
        needCrop: Boolean,
        needCircleCrop: Boolean
    ) = makeGlideImage(url, imageView, needCrop, needCircleCrop)

}