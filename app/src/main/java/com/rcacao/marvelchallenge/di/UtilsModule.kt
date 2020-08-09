package com.rcacao.marvelchallenge.di

import com.rcacao.marvelchallenge.data.ApiHelper
import com.rcacao.marvelchallenge.data.ApiHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class UtilsModule {
    @Binds
    abstract fun bindApiHelper(apiHelperImpl: ApiHelperImpl): ApiHelper
}
