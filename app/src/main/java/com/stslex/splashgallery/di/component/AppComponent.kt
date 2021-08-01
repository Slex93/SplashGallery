package com.stslex.splashgallery.di.component

import com.stslex.splashgallery.di.module.*
import com.stslex.splashgallery.ui.all_photos.AllPhotosFragment
import com.stslex.splashgallery.ui.single_collection.SingleCollectionFragment
import com.stslex.splashgallery.ui.single_photo_screen.SinglePhotoFragment
import com.stslex.splashgallery.ui.user.UserFragment
import com.stslex.wallpape.ui.main_screen.MainFragment
import dagger.Component

@Component(
    modules = [
        NetworkServiceModule::class,
        ViewModelModule::class,
        ViewModelFactoryModule::class,
        RepositoryModule::class,
        SourceModule::class
    ]
)
interface AppComponent {
    fun inject(fragment: MainFragment)
    fun inject(fragment: SingleCollectionFragment)
    fun inject(fragment: AllPhotosFragment)
    fun inject(fragment: SinglePhotoFragment)
    fun inject(fragment: UserFragment)
}
