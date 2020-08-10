package com.rcacao.marvelchallenge.view.ui.favorites

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.rcacao.marvelchallenge.databinding.FavoriteItemBinding
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.view.ui.ItemSizeHelper
import com.rcacao.marvelchallenge.view.viewmodel.SharedViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class FavoritesAdapter @Inject constructor(
    @ActivityContext private val context: Context,
    itemSizeHelper: ItemSizeHelper
) :
    RecyclerView.Adapter<FavoriteViewHolder>() {

    private var itemSize: Int = itemSizeHelper(context as Activity)
    private val sharedViewModel: SharedViewModel by (context as ComponentActivity).viewModels()

    var data: ArrayList<CharacterModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding: FavoriteItemBinding =
            FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int = data?.count() ?: 0

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        data?.get(position)?.let { holder.bindPost(itemSize, sharedViewModel, it, position) }
    }

}
