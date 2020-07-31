package com.rcacao.marvelchallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rcacao.marvelchallenge.data.CharactersResponse
import com.rcacao.marvelchallenge.data.api.MarvelService
import com.rcacao.marvelchallenge.data.utils.MarvelApiHelper
import com.rcacao.marvelchallenge.utils.extensions.toMD5
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //para testes do serviço
        val service = MarvelService.create()

        val helper = MarvelApiHelper()
        val ts: String = helper.timeStamp()
        val hash: String = (ts + BuildConfig.PRIVATE_APIKEY + BuildConfig.APIKEY).toMD5()
        service.loadCharacters(ts, hash).enqueue(MyCallBack())

    }
}

class MyCallBack : Callback<CharactersResponse> {
    override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
        //TODO("Not yet implemented")
    }

    override fun onResponse(
        call: Call<CharactersResponse>,
        response: Response<CharactersResponse>
    ) {
        //TODO("Not yet implemented")
    }

}