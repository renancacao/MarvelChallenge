package com.rcacao.marvelchallenge.view.ui.list

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.paging.PagingDataAdapter
import com.rcacao.marvelchallenge.databinding.CharacterItemBinding
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.view.ui.ItemSizeHelper
import com.rcacao.marvelchallenge.view.viewmodel.SharedViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ActivityScoped
class CharactersAdapter @Inject constructor(
    @ActivityContext private val context: Context,
    itemSizeHelper: ItemSizeHelper,
    diffUtilCallBack: DiffUtilCallBack
) :
    PagingDataAdapter<CharacterModel, CharacterViewHolder>(diffUtilCallBack) {

    private var itemSize: Int = itemSizeHelper(context as Activity)
    private val sharedViewModel: SharedViewModel by (context as ComponentActivity).viewModels()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: CharacterItemBinding =
            CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bindPost(itemSize, sharedViewModel, it, position) }
    }

    fun changeFav(position: Int, value: Boolean) {
        getItem(position)?.let {
            it.isFavorite = value
            notifyItemChanged(position)
        }
    }

}