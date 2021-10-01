package com.stslex.splashgallery.di.module

import com.stslex.splashgallery.core.Abstract
import com.stslex.splashgallery.data.model.download.RemoteDownloadModel
import com.stslex.splashgallery.data.model.image.RemoteImageModel
import com.stslex.splashgallery.data.model.user.RemoteUserModel
import com.stslex.splashgallery.data.photo.DownloadDataMapper
import com.stslex.splashgallery.data.photo.PhotoDataMapper
import com.stslex.splashgallery.data.user.UserDataMapper
import com.stslex.splashgallery.ui.core.UIResult
import com.stslex.splashgallery.ui.model.DownloadModel
import com.stslex.splashgallery.ui.model.image.ImageModel
import com.stslex.splashgallery.ui.model.user.UserModel
import dagger.Module
import dagger.Provides

@Module
class MapperModule {

    @Provides
    fun providesPhotoDataMapper(): Abstract.Mapper.DataToUI<RemoteImageModel, UIResult<ImageModel>> =
        PhotoDataMapper()

    @Provides
    fun providesUserDataMapper(): Abstract.Mapper.DataToUI<RemoteUserModel, UIResult<UserModel>> =
        UserDataMapper()

    @Provides
    fun providesDownloadDataMapper(): Abstract.Mapper.DataToUI<RemoteDownloadModel, UIResult<DownloadModel>> =
        DownloadDataMapper()
}