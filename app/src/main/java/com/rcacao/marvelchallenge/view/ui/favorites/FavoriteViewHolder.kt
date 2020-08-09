package com.rcacao.marvelchallenge.view.ui.favorites

import androidx.recyclerview.widget.RecyclerView
import com.rcacao.marvelchallenge.databinding.FavoriteItemBinding
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.view.viewmodel.SharedViewModel

class FavoriteViewHolder(private val binding: FavoriteItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindPost(
        itemSize: Int,
        sharedViewModel: SharedViewModel,
        character: CharacterModel,
        position: Int
    ) {
        binding.imgChar.layoutParams.height = itemSize
        binding.imgChar.layoutParams.width = itemSize
        binding.sharedViewModel = sharedViewModel
        binding.position = position
        binding.character = character
    }
}
