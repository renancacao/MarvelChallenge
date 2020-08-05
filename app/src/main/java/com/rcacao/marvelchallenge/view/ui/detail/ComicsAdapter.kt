package com.rcacao.marvelchallenge.view.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.rcacao.marvelchallenge.databinding.ComicsItemBinding
import com.rcacao.marvelchallenge.view.viewmodel.ComicsViewModel
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ComicsAdapter @Inject constructor(@ActivityContext private val context: Context) :
    RecyclerView.Adapter<ComicsAdapter.ComicsViewHolder>() {

    var viewModel: ComicsViewModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsViewHolder {
        val binding: ComicsItemBinding =
            ComicsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ComicsViewHolder(binding)
    }

    override fun getItemCount(): Int = viewModel?.comicsList?.value?.size ?: 0

    override fun onBindViewHolder(holder: ComicsViewHolder, position: Int) {
        holder.binding.comics = viewModel?.comicsList?.value?.get(position)
    }

    class ComicsViewHolder(val binding: ComicsItemBinding) : RecyclerView.ViewHolder(binding.root)
}