package com.rcacao.marvelchallenge.di

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.rcacao.marvelchallenge.data.CharacterResponse
import com.rcacao.marvelchallenge.data.datasource.CharactersDataSourceFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object PagedListLiveDataModule {

    @Provides
    fun providesPagedListLiveDAta(): LiveData<PagedList<CharacterResponse>> {
        val config: PagedList.Config = PagedList.Config.Builder()
            .setPageSize(20)
            .setEnablePlaceholders(true)
            .build()
        return LivePagedListBuilder(CharactersDataSourceFactory(), config).build()
    }

}