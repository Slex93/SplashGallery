package com.stslex.splashgallery.di.module

import androidx.lifecycle.ViewModel
import com.stslex.splashgallery.di.ViewModelKey
import com.stslex.splashgallery.ui.main_screen.MainViewModel
import com.stslex.splashgallery.ui.main_screen_pager.PagerSharedViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @IntoMap
    @Binds
    @ViewModelKey(MainViewModel::class)
    fun bindsViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @Binds
    @ViewModelKey(PagerSharedViewModel::class)
    fun bindsSharedViewModel(viewModel: PagerSharedViewModel): ViewModel
}