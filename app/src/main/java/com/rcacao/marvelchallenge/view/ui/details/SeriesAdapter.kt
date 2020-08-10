package com.rcacao.marvelchallenge.view.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rcacao.marvelchallenge.databinding.SeriesItemBinding
import com.rcacao.marvelchallenge.view.viewmodel.DetailsViewModel
import javax.inject.Inject

class SeriesAdapter @Inject constructor() :
    RecyclerView.Adapter<SeriesAdapter.SeriesViewHolder>() {

    var viewModel: DetailsViewModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val binding: SeriesItemBinding =
            SeriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeriesViewHolder(binding)
    }

    override fun getItemCount(): Int = viewModel?.seriesList?.value?.size ?: 0

    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        holder.binding.series = viewModel?.seriesList?.value?.get(position)
    }

    class SeriesViewHolder(val binding: SeriesItemBinding) : RecyclerView.ViewHolder(binding.root)
}
