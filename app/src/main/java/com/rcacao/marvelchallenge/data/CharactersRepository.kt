package com.rcacao.marvelchallenge.data

import androidx.paging.PositionalDataSource
import com.rcacao.marvelchallenge.data.datasource.CharactersDataSource
import com.rcacao.marvelchallenge.utils.ApiHelper
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    val dataSource: CharactersDataSource,
    val apiHelper: ApiHelper
) {


    fun getCharacters() {
        val param = PositionalDataSource.LoadInitialParams(
            0,
            apiHelper.getLimit(),
            apiHelper.getLimit(),
            true
        )
        dataSource.loadInitial(param, CharacterCallBack())
    }

}

class CharacterCallBack : PositionalDataSource.LoadInitialCallback<CharacterResponse>(){

    override fun onResult(data: MutableList<CharacterResponse>, position: Int, totalCount: Int) {
        TODO("Not yet implemented")
    }

    override fun onResult(data: MutableList<CharacterResponse>, position: Int) {
        TODO("Not yet implemented")
    }

}