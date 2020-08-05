package com.rcacao.marvelchallenge.view.ui.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rcacao.marvelchallenge.databinding.FragmentDetailBinding
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.view.viewmodel.ComicsViewModel
import com.rcacao.marvelchallenge.view.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*
import javax.inject.Inject


@AndroidEntryPoint
class DetailFragment : Fragment(), OnImageLoadListener {

    private lateinit var binding: FragmentDetailBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val comicsViewModel: ComicsViewModel by viewModels()

    @Inject
    lateinit var comicsAdapter: ComicsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        binding()
        sharedViewModel.configureDetailsToolbar()
        initAdapter()
    }

    private fun initAdapter() {
        val character: CharacterModel? = sharedViewModel.selectedCharacter.value
        character?.let {
            comicsViewModel.getComics(character.id)
            comicsAdapter.viewModel = comicsViewModel
        }
        observeListUpdate()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerComics.layoutManager = layoutManager
        recyclerComics.adapter = comicsAdapter
    }

    private fun observeListUpdate() {
        comicsViewModel.comicsList.observe(
            viewLifecycleOwner,
            Observer { comicsAdapter.notifyDataSetChanged() })
    }

    private fun binding() {
        binding.viewModel = sharedViewModel
        binding.onImageLoadListener = this
    }

    override fun onImageLoad() {
        startPostponedEnterTransition()
    }


}