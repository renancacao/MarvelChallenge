package com.rcacao.marvelchallenge.data.api

import com.rcacao.marvelchallenge.BuildConfig
import com.rcacao.marvelchallenge.data.CharactersResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelWebService {

    @GET("/v1/public/characters")
    fun loadCharacters(
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("orderBy") orderBy: String = "name",
        @Query("limit") limit: Int = 20,
        @Query("apikey") apikey: String = BuildConfig.APIKEY
    ): Call<CharactersResponse>

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
