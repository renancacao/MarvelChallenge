package com.rcacao.marvelchallenge.di

import com.rcacao.marvelchallenge.utils.ApiHelper
import com.rcacao.marvelchallenge.utils.ApiHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class UtilsModule {
    @Binds
    abstract fun bindApiHelperImpl(apiHelperImpl: ApiHelperImpl): ApiHelper
}
