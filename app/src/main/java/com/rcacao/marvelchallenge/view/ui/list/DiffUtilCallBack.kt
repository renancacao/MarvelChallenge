package com.rcacao.marvelchallenge.view.ui.list

import androidx.recyclerview.widget.DiffUtil
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import javax.inject.Inject

class DiffUtilCallBack @Inject constructor() : DiffUtil.ItemCallback<CharacterModel>() {

    override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CharacterModel,
        newItem: CharacterModel
    ): Boolean {
        //TODO: adicinar parametros do objeto no futuro
        return oldItem.name == newItem.name
                && oldItem.imageListUrl == newItem.imageListUrl
    }

}