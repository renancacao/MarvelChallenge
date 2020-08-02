package com.rcacao.marvelchallenge.view

import androidx.recyclerview.widget.DiffUtil
import com.rcacao.marvelchallenge.data.CharacterResponse

class DiffUtilCallBack : DiffUtil.ItemCallback<CharacterResponse>() {

    override fun areItemsTheSame(oldItem: CharacterResponse, newItem: CharacterResponse): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CharacterResponse,
        newItem: CharacterResponse
    ): Boolean {
        //TODO: adicinar parametros do objeto no futuro
        return oldItem.name == newItem.name
    }

}