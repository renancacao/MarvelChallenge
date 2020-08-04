package com.rcacao.marvelchallenge.view.ui

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.rcacao.marvelchallenge.GlideApp
import com.rcacao.marvelchallenge.domain.model.CharacterModel
import com.rcacao.marvelchallenge.view.viewmodel.CharactersViewModel
import com.rcacao.marvelchallenge.view.viewmodel.SharedViewModel

@BindingAdapter("glideSrc")
fun imageRes(view: ImageView, url: String) {
    GlideApp.with(view.context)
        .load(url)
        .into(view)
}

@BindingAdapter("onSelectChar", "character", "onClickItem", "itemPosition")
fun setCallbacks(
    view: View,
    sharedViewModel: SharedViewModel,
    character: CharacterModel,
    charactersViewModel: CharactersViewModel,
    itemPosition: Int
) {
    view.setOnClickListener {
        sharedViewModel.selectCharacter(character)
        charactersViewModel.savePosition(itemPosition)
    }

}
