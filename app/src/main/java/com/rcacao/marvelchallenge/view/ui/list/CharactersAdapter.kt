package com.rcacao.marvelchallenge.view.ui.list

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rcacao.marvelchallenge.GlideApp
import com.rcacao.marvelchallenge.R
import com.rcacao.marvelchallenge.domain.model.CharacterModel
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.android.synthetic.main.character_item.view.*
import javax.inject.Inject

@ActivityScoped
class CharactersAdapter @Inject constructor(
    @ActivityContext private val context: Context,
    diffUtilCallBack: DiffUtilCallBack
) :
    PagingDataAdapter<CharacterModel, CharactersAdapter.CharacterViewHolder>(diffUtilCallBack) {

    var itemSize: Int

    init {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        itemSize = displayMetrics.widthPixels / context.resources.getInteger(R.integer.item_columns)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bindPost(it) }
    }

    inner class CharacterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val txtName: TextView = itemView.txtName
        private val imgChar: ImageView = itemView.imgChar

        fun bindPost(character: CharacterModel) {
            with(character) {
                txtName.text = this.name
            }
            imgChar.layoutParams.height = itemSize
            imgChar.layoutParams.width = itemSize

            GlideApp.with(itemView.context)
                .load(character.listImageUrl)
                .into(imgChar)
        }
    }

}