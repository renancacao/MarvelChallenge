package com.rcacao.marvelchallenge.di

import com.rcacao.marvelchallenge.data.ApiHelper
import com.rcacao.marvelchallenge.data.ApiHelperImpl
import com.rcacao.marvelchallenge.utils.ConnectionHelper
import com.rcacao.marvelchallenge.utils.ConnectionHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class UtilsModule {
    @Binds
    abstract fun bindApiHelper(apiHelperImpl: ApiHelperImpl): ApiHelper

    @Binds
    abstract fun bindConnectionHelper(connectionHelperImpl: ConnectionHelperImpl): ConnectionHelper
}
