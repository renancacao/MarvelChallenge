package com.rcacao.marvelchallenge.view.ui.list

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rcacao.marvelchallenge.R
import com.rcacao.marvelchallenge.databinding.CharacterItemBinding
import com.rcacao.marvelchallenge.domain.model.CharacterModel
import com.rcacao.marvelchallenge.view.viewmodel.CharactersViewModel
import com.rcacao.marvelchallenge.view.viewmodel.SharedViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CharactersAdapter @Inject constructor(
    @ActivityContext private val context: Context,
    diffUtilCallBack: DiffUtilCallBack) :
    PagingDataAdapter<CharacterModel, CharactersAdapter.CharacterViewHolder>(diffUtilCallBack) {

    var itemSize: Int
    private val sharedViewModel: SharedViewModel by (context as ComponentActivity).viewModels()
    private val charactersViewModel: CharactersViewModel by (context as ComponentActivity).viewModels()

    init {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        itemSize = displayMetrics.widthPixels / context.resources.getInteger(R.integer.item_columns)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: CharacterItemBinding =
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bindPost(it, position) }
    }

    inner class CharacterViewHolder(private val binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindPost(character: CharacterModel, position: Int) {
            binding.imgChar.layoutParams.height = itemSize
            binding.imgChar.layoutParams.width = itemSize
            binding.sharedViewModel = sharedViewModel
            binding.charactersViewModel = charactersViewModel
            binding.position = position
            binding.character = character
        }
    }

}