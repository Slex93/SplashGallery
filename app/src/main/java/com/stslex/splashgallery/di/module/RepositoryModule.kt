package com.stslex.splashgallery.di.module

import com.stslex.splashgallery.data.collections.CollectionRepository
import com.stslex.splashgallery.data.photo.PhotoRepository
import com.stslex.splashgallery.data.photos.PhotosRepository
import com.stslex.splashgallery.data.repository.impl.DownloadRepositoryImpl
import com.stslex.splashgallery.data.repository.impl.UserRepositoryImpl
import com.stslex.splashgallery.data.repository.interf.DownloadRepository
import com.stslex.splashgallery.data.repository.interf.UserRepository
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
interface RepositoryModule {
    @Binds
    fun bindsPhotoRepository(repository: PhotoRepository.Base): PhotoRepository

    @Binds
    fun bindsCollectionRepository(repository: CollectionRepository.Base): CollectionRepository

    @Binds
    fun bindsUserRepository(repository: UserRepositoryImpl): UserRepository

    @Binds
    fun bindsDownloadRepository(repository: DownloadRepositoryImpl): DownloadRepository

    @Binds
    fun bindsAllPhotosRepository(repository: PhotosRepository.Base): PhotosRepository
}