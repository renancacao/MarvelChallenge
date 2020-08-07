package com.rcacao.marvelchallenge.view.ui.details

import android.os.Bundle
import android.transition.TransitionInflater
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
import kotlinx.android.synthetic.main.fragment_details.*
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
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        binding()
        setListeners()
        sharedViewModel.configureDetailsToolbar()
        charId = sharedViewModel.selectedCharacter.value?.id ?: ""
        initComicsList()
        initSeriesList()
        observeViewModel()
    }

    private fun setListeners() {
        buttonRetryComics.setOnClickListener { detailsViewModel.getComics(charId) }
        buttonRetrySeries.setOnClickListener { detailsViewModel.getSeries(charId) }
    }

    private fun initComicsList() {
        detailsViewModel.getComics(charId)
        comicsAdapter.viewModel = detailsViewModel
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerComics.layoutManager = layoutManager
        recyclerComics.adapter = comicsAdapter
    }

    private fun initSeriesList() {
        detailsViewModel.getSeries(charId)
        seriesAdapter.viewModel = detailsViewModel
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerSeries.layoutManager = layoutManager
        recyclerSeries.adapter = seriesAdapter
    }

    private fun observeViewModel() {
        detailsViewModel.comicsStateUi.observe(viewLifecycleOwner,
            Observer { handleComicsUiState(it) })
        detailsViewModel.seriesStateUi.observe(viewLifecycleOwner,
            Observer { handleSeriesUiState(it) })
    }

    private fun handleComicsUiState(state: ComicsStateUi) {
        progressComics.isVisible = state is ComicsStateUi.Loading
        recyclerComics.isInvisible =
            state !is ComicsStateUi.Loading && state !is ComicsStateUi.Loaded
        layoutComics.isGone = state is ComicsStateUi.Empty
        buttonRetryComics.isVisible = state is ComicsStateUi.Error
    }

    private fun handleSeriesUiState(state: SeriesStateUi) {
        progressSeries.isVisible = state is SeriesStateUi.Loading
        recyclerSeries.isInvisible =
            state !is SeriesStateUi.Loading && state !is SeriesStateUi.Loaded
        layoutSeries.isGone = state is SeriesStateUi.Empty
        buttonRetrySeries.isVisible = state is SeriesStateUi.Error
    }

    private fun binding() {
        binding.viewModel = sharedViewModel
        binding.onImageLoadListener = this
    }

    override fun onImageLoad() {
        startPostponedEnterTransition()
    }


}