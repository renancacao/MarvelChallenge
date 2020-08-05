package com.rcacao.marvelchallenge.view.ui.detail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rcacao.marvelchallenge.databinding.FragmentDetailBinding
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.view.viewmodel.ComicsViewModel
import com.rcacao.marvelchallenge.view.viewmodel.SharedViewModel


class DetailFragment : Fragment(), OnImageLoadListener {

    private lateinit var binding: FragmentDetailBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val comicsViewModel: ComicsViewModel by viewModels()

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
        binding.viewModel = sharedViewModel
        binding.onImageLoadListener = this
    }

    override fun onImageLoad() {
        startPostponedEnterTransition()
        sharedViewModel.configureDetailsToolbar()
        val character: CharacterModel? = sharedViewModel.selectedCharacter.value

        character?.let{
            comicsViewModel.getComics(character.id)
        }

    }


}