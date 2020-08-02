package com.rcacao.marvelchallenge.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rcacao.marvelchallenge.R
import com.rcacao.marvelchallenge.data.CharacterResponse
import kotlinx.android.synthetic.main.item_character.view.*

class CharactersAdapter() :
    PagedListAdapter<CharacterResponse, CharactersAdapter.CharacterViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let { holder.bindPost(it) }
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val txtName: TextView = itemView.txtName

        fun bindPost(character: CharacterResponse) {
            with(character) {
                txtName.text = this.name
            }
        }
    }

}