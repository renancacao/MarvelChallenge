package com.rcacao.marvelchallenge.view.ui.list

import androidx.recyclerview.widget.RecyclerView
import com.rcacao.marvelchallenge.databinding.CharacterItemBinding
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.view.viewmodel.SharedViewModel

class CharacterViewHolder(private val binding: CharacterItemBinding) :
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
