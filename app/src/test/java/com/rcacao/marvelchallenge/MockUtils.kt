package com.rcacao.marvelchallenge

import androidx.paging.PagingConfig
import com.rcacao.marvelchallenge.data.database.Character
import com.rcacao.marvelchallenge.data.model.character.CharacterResponse
import com.rcacao.marvelchallenge.data.model.comics.ComicsDataResponse
import com.rcacao.marvelchallenge.data.model.comics.ComicsListResponse
import com.rcacao.marvelchallenge.data.model.comics.ComicsResponse
import com.rcacao.marvelchallenge.data.model.series.SeriesDataResponse
import com.rcacao.marvelchallenge.data.model.series.SeriesListResponse
import com.rcacao.marvelchallenge.data.model.series.SeriesResponse
import com.rcacao.marvelchallenge.data.model.thumbnail.ThumbnailResponse
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import com.rcacao.marvelchallenge.domain.model.series.SeriesModel

class MockUtils {
    companion object {

        fun getSeriesResponseList(): List<SeriesResponse> =
            listOf(
                getSeriesResponse(
                    1
                ),
                getSeriesResponse(
                    2
                )
            )

        private fun getSeriesResponse(id: Int): SeriesResponse {
            val response = SeriesResponse()
            response.id = "id$id"
            response.title = "title$id"
            response.thumbnail =
                getThumb(id)
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
            listOf(
                getComicsResponse(1),
                getComicsResponse(2)
            )

        fun getComicsModelList(): List<ComicsModel> =
            listOf(
                getComicsModel(1),
                getComicsModel(2)
            )

        private fun getComicsModel(id: Int): ComicsModel = ComicsModel("url$id", "title$id")


        fun getSeriesModelList(): List<SeriesModel> =
            listOf(
                getSeriesModel(1),
                getSeriesModel(2)
            )

        private fun getSeriesModel(id: Int): SeriesModel = SeriesModel("url$id", "title$id")


        private fun getComicsResponse(id: Int): ComicsResponse {
            val response = ComicsResponse()
            response.id = "id$id"
            response.title = "title$id"
            response.thumbnail =
                getThumb(id)
            return response
        }

        fun getCharacterModel(id: Int): CharacterModel {
            return CharacterModel(
                id.toString(),
                "name$id",
                "imageListUrl$id",
                "imageDetailUrl$id",
                "description$id",
                true
            )
        }

        fun getCharacterModelList(): ArrayList<CharacterModel> =
            arrayListOf(getCharacterModel(1), getCharacterModel(2))


        private fun getCharacterResponse(id: Int): CharacterResponse {
            val response = CharacterResponse()
            response.id = id.toString()
            response.name = "name$id"
            response.description = "description$id"
            response.thumbnail =
                getThumb(id)
            return response
        }

        fun getImageUrl(
            thumbnail: ThumbnailResponse,
            path: String
        ): String {
            return (thumbnail.path
                    + "/" + path + "."
                    + thumbnail.extension)
        }

        private fun getPageConfig() = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
            initialLoadSize = 10
        )

        fun getComicsDataResponse(): ComicsDataResponse {
            val response = ComicsDataResponse()
            response.data = ComicsListResponse()
            response.data!!.comics =
                getComicsResponseList()
            return response
        }

        fun getSeriesDataResponse(): SeriesDataResponse? {
            val response = SeriesDataResponse()
            response.data = SeriesListResponse()
            response.data!!.series =
                getSeriesResponseList()
            return response
        }


    }
}