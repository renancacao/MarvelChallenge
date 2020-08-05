package com.rcacao.marvelchallenge.data.api

import com.rcacao.marvelchallenge.data.model.character.CharactersDataResponse
import com.rcacao.marvelchallenge.data.model.comics.ComicsDataResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelWebService {

    @GET("/v1/public/characters")
    suspend fun loadCharacters(
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("orderBy") orderBy: String,
        @Query("apikey") apikey: String
    ): CharactersDataResponse

    @GET("/v1/public/characters")
    suspend fun loadCharactersByName(
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
        @Query("orderBy") orderBy: String,
        @Query("apikey") apikey: String,
        @Query("nameStartsWith") nameStartWith: String = ""
    ): CharactersDataResponse

    @GET("/v1/public/characters/{charId}/comics")
    suspend fun loadComics(
        @Path("charId") charId: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("apikey") apikey: String
    ): ComicsDataResponse

    companion object {
        private const val BASE_URL = "https://gateway.marvel.com/"

        fun create(): MarvelWebService {
            val logger: HttpLoggingInterceptor =
                HttpLoggingInterceptor().apply { level = Level.BODY }

            val client: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MarvelWebService::class.java)
        }
    }
}
