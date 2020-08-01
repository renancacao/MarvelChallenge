package com.rcacao.marvelchallenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

@Module
@InstallIn(ApplicationComponent::class)
object CoroutineScopeModule {

    @Provides
    fun providesCoroutineScopeModule(): CoroutineScope = CoroutineScope(Job() + Dispatchers.IO)

}