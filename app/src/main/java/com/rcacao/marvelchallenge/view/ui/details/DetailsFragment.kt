package com.rcacao.marvelchallenge.view.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rcacao.marvelchallenge.databinding.FragmentDetailsBinding
import com.rcacao.marvelchallenge.view.model.details.comics.ComicsStateUi
import com.rcacao.marvelchallenge.view.model.details.series.SeriesStateUi
import com.rcacao.marvelchallenge.view.viewmodel.DetailsViewModel
import com.rcacao.marvelchallenge.view.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DetailsFragment : Fragment(), OnImageLoadListener {

    private lateinit var binding: FragmentDetailsBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val detailsViewModel: DetailsViewModel by viewModels()

    private lateinit var charId: String

    @Inject
    lateinit var comicsAdapter: ComicsAdapter

    @Inject
    lateinit var seriesAdapter: SeriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding()
    }

    override fun onImageLoad() {
        binding.progresImage.isVisible = false
        setListeners()
        sharedViewModel.configureDetailsToolbar()
        charId = sharedViewModel.selectedCharacter.value?.id ?: ""
        initComicsList()
        initSeriesList()
        observeViewModel()
    }

    private fun setListeners() {
        binding.buttonRetryComics.setOnClickListener { detailsViewModel.getComics(charId) }
        binding.buttonRetrySeries.setOnClickListener { detailsViewModel.getSeries(charId) }
    }

    private fun initComicsList() {
        detailsViewModel.getComics(charId)
        comicsAdapter.viewModel = detailsViewModel
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerComics.layoutManager = layoutManager
        binding.recyclerComics.adapter = comicsAdapter
    }

    private fun initSeriesList() {
        detailsViewModel.getSeries(charId)
        seriesAdapter.viewModel = detailsViewModel
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerSeries.layoutManager = layoutManager
        binding.recyclerSeries.adapter = seriesAdapter
    }

    private fun observeViewModel() {
        detailsViewModel.comicsStateUi.observe(viewLifecycleOwner,
            Observer { handleComicsUiState(it) })
        detailsViewModel.seriesStateUi.observe(viewLifecycleOwner,
            Observer { handleSeriesUiState(it) })
    }

    private fun handleComicsUiState(state: ComicsStateUi) {
        binding.progressComics.isVisible = state is ComicsStateUi.Loading
        binding.recyclerComics.isInvisible =
            state !is ComicsStateUi.Loading && state !is ComicsStateUi.Loaded
        binding.layoutComics.isGone = state is ComicsStateUi.Empty
        binding.buttonRetryComics.isVisible = state is ComicsStateUi.Error
        binding.textMessageComics.isVisible = state is ComicsStateUi.Error

        if (state is ComicsStateUi.Error) {
            binding.textMessageComics.text = state.errorMsg
        }
    }

    private fun handleSeriesUiState(state: SeriesStateUi) {
        binding.progressSeries.isVisible = state is SeriesStateUi.Loading
        binding.recyclerSeries.isInvisible =
            state !is SeriesStateUi.Loading && state !is SeriesStateUi.Loaded
        binding.layoutSeries.isGone = state is SeriesStateUi.Empty
        binding.buttonRetrySeries.isVisible = state is SeriesStateUi.Error
        binding.textMessageSeries.isVisible = state is SeriesStateUi.Error

        if (state is SeriesStateUi.Error) {
            binding.textMessageSeries.text = state.errorMsg
        }
    }

    private fun binding() {
        binding.viewModel = sharedViewModel
        binding.onImageLoadListener = this
    }


}
