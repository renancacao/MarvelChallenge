package com.rcacao.marvelchallenge.di

import com.rcacao.marvelchallenge.view.ui.list.ListFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@Module
@InstallIn(FragmentComponent::class)
object FragmentsModule {

    @ExperimentalCoroutinesApi
    @FlowPreview
    @Provides
    @Singleton
    fun providesListFragment(): ListFragment = ListFragment.newInstance()


}