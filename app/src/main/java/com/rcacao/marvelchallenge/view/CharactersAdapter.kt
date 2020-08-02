package com.rcacao.marvelchallenge.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rcacao.marvelchallenge.GlideApp
import com.rcacao.marvelchallenge.R
import com.rcacao.marvelchallenge.data.CharacterResponse
import kotlinx.android.synthetic.main.item_character.view.*

class CharactersAdapter() :
    PagedListAdapter<CharacterResponse, CharactersAdapter.CharacterViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bindPost(it) }
    }

    class CharacterViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {

        private val txtName: TextView = itemView.txtName
        private val imgChar: ImageView = itemView.imgChar

        fun bindPost(character: CharacterResponse) {
            with(character) {
                txtName.text = this.name
            }
            GlideApp.with(context)
                .load(character.thumbnail.path + "/portrait_xlarge" + "." + character.thumbnail.extension)
                .into(imgChar)
        }
    }

}