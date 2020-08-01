package com.rcacao.marvelchallenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.rcacao.marvelchallenge.data.CharactersRepository
import com.rcacao.marvelchallenge.data.api.MarvelWebService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var webService: MarvelWebService

    @Inject
    lateinit var repository: CharactersRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycleScope
        CoroutineScope(Dispatchers.IO).launch {
            repository.getCharacters()
        }

    }
}
