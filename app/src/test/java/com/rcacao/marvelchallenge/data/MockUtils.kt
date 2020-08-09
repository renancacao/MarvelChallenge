package com.rcacao.marvelchallenge.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import com.rcacao.marvelchallenge.data.database.Character
import com.rcacao.marvelchallenge.data.model.character.CharacterResponse
import com.rcacao.marvelchallenge.data.model.character.CharactersDataResponse
import com.rcacao.marvelchallenge.data.model.character.CharactersListResponse
import com.rcacao.marvelchallenge.data.model.comics.ComicsResponse
import com.rcacao.marvelchallenge.data.model.series.SeriesResponse
import com.rcacao.marvelchallenge.data.model.thumbnail.ThumbnailResponse
import com.rcacao.marvelchallenge.data.repository.characters.CharacterPagingSource
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.utils.ConnectionHelper
import kotlinx.coroutines.flow.Flow

class MockUtils {
    companion object {

        fun getSeriesResponseList(): List<SeriesResponse> =
            listOf(getSeriesResponse(1), getSeriesResponse(2))

        fun getCharactersDataResponse(list: List<CharacterResponse>): CharactersDataResponse {
            val response = CharactersDataResponse()
            response.data = CharactersListResponse()
            response.data!!.characters = list
            return response
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

        fun getCharacterList(): List<Character> = listOf(
            getCharacter(1),
            getCharacter(2)
        )

        private fun getCharacter(id: Int): Character {
            return Character(
                id.toString(),
                "name$id",
                "urlList$id",
                "urlDetails$id",
                "desc$id"
            )
        }

        fun getComicsResponseList(): List<ComicsResponse> =
            listOf(getComicsResponse(1), getComicsResponse(2))

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


        fun getCharacterResponseList(): List<CharacterResponse> =
            listOf(getCharacterResponse(1), getCharacterResponse(2))

        private fun getCharacterResponse(id: Int): CharacterResponse {
            val response = CharacterResponse()
            response.id = id.toString()
            response.name = "name$id"
            response.description = "description$id"
            response.thumbnail = getThumb(id)
            return response
        }

        fun getListLocalIds(): List<String> = listOf("1")

        fun getImageUrl(
            thumbnail: ThumbnailResponse,
            path: String
        ): String {
            return (thumbnail.path
                    + "/" + path + "."
                    + thumbnail.extension)
        }

        fun getFlow(
            webService: MarvelWebService,
            connectionHelper: ConnectionHelper
        ): Flow<PagingData<CharacterResponse>> {
            return Pager(
                getPageConfig(),
                pagingSourceFactory = {
                    CharacterPagingSource(
                        "",
                        webService,
                        ApiHelperImpl(),
                        connectionHelper
                    )
                }
            ).flow
        }

        private fun getPageConfig() = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
            initialLoadSize = 10
        )


    }
}