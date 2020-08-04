package com.rcacao.marvelchallenge.view.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rcacao.marvelchallenge.GlideApp
import com.rcacao.marvelchallenge.R
import com.rcacao.marvelchallenge.data.CharacterResponse
import kotlinx.android.synthetic.main.character_item.view.*
import javax.inject.Inject

class CharactersAdapter @Inject constructor(diffUtilCallBack: DiffUtilCallBack) :
    PagingDataAdapter<CharacterResponse, CharactersAdapter.CharacterViewHolder>(diffUtilCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bindPost(it) }
    }

    class CharacterViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val txtName: TextView = itemView.txtName
        private val imgChar: ImageView = itemView.imgChar

        fun bindPost(character: CharacterResponse) {
            with(character) {
                txtName.text = this.name
            }
            GlideApp.with(itemView.context)
                .load(character.thumbnail.path + "/portrait_xlarge" + "." + character.thumbnail.extension)
                .into(imgChar)
        }
    }

}