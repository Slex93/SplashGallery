package com.stslex.splashgallery.mapper

import com.stslex.splashgallery.data.model.PagesModel
import com.stslex.splashgallery.data.model.RemotePagesModel

class PagesMapper : BaseMapper<RemotePagesModel, PagesModel> {
    private val imageMapper = ImageMapper()
    override fun transformToDomain(type: RemotePagesModel): PagesModel = PagesModel(
        image = type.image.map { imageMapper.transformToDomain(it) }
    )
}