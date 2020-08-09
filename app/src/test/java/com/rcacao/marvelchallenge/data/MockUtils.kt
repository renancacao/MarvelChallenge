package com.rcacao.marvelchallenge.data

import androidx.paging.PagingData
import com.rcacao.marvelchallenge.data.database.Character
import com.rcacao.marvelchallenge.data.model.character.CharacterResponse
import com.rcacao.marvelchallenge.data.model.comics.ComicsResponse
import com.rcacao.marvelchallenge.data.model.series.SeriesResponse
import com.rcacao.marvelchallenge.data.model.thumbnail.ThumbnailResponse
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockUtils {
    companion object {

        fun getSeriesResponseList(): ArrayList<SeriesResponse> {
            val response1: SeriesResponse = getSeriesResponse(1)
            val response2: SeriesResponse = getSeriesResponse(2)

            val list = ArrayList<SeriesResponse>()
            list.add(response1)
            list.add(response2)
            return list
        }

        private fun getSeriesResponse(id: Int): SeriesResponse {
            val response = SeriesResponse()
            response.id = "id$id"
            response.title = "title$id"
            response.thumbnail = getThumb(id)
            return response
        }

        private fun getThumb(id: Int): ThumbnailResponse {
            val thumb = ThumbnailResponse()
            thumb.path = "path$id"
            thumb.extension = "ext$id"
            return thumb
        }

        fun getCharacterList(): List<Character> {
            val list = ArrayList<Character>()
            list.add(getCharacter(1))
            list.add(getCharacter(2))
            return list
        }

        private fun getCharacter(id: Int): Character {
            return Character(
                id.toString(),
                "name$id",
                "urlList$id",
                "urlDetails$id",
                "desc$id"
            )
        }

        fun getComicsResponseList(): ArrayList<ComicsResponse> {
            val response1: ComicsResponse = getComicsResponse(1)
            val response2: ComicsResponse = getComicsResponse(2)

            val list = ArrayList<ComicsResponse>()
            list.add(response1)
            list.add(response2)
            return list
        }

        private fun getComicsResponse(id: Int): ComicsResponse {
            val response = ComicsResponse()
            response.id = "id$id"
            response.title = "title$id"
            response.thumbnail = getThumb(id)
            return response
        }

        fun getCharacterModel(): CharacterModel {
            return CharacterModel(
                "id",
                "name",
                "imageListUrl",
                "imageDetailUrl",
                "description",
                true
            )
        }

        fun getPagingDataFlow(): Flow<Any> {
            val pagingData: PagingData<CharacterResponse> =
                PagingData.from(getCharacterResponseList())
            return flow<PagingData<CharacterResponse>> { emit(pagingData) }
        }

        private fun getCharacterResponseList(): List<CharacterResponse> {
            val list = ArrayList<CharacterResponse>()
            list.add(getCharacterResponse(1))
            list.add(getCharacterResponse(2))
            return list
        }

        private fun getCharacterResponse(id: Int): CharacterResponse {
            val response = CharacterResponse()
            response.id = id.toString()
            response.name = "name$id"
            response.description = "description$id"
            response.thumbnail = getThumb(id)
            return response
        }


    }
}