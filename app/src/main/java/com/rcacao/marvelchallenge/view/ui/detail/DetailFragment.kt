package com.rcacao.marvelchallenge.view.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.rcacao.marvelchallenge.databinding.FragmentDetailBinding
import com.rcacao.marvelchallenge.view.viewmodel.SharedViewModel


class DetailFragment : Fragment(), OnImageLoadListener {

    private lateinit var binding: FragmentDetailBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        binding.viewModel = sharedViewModel
        binding.onImageLoadListener = this
    }

    override fun onImageLoad(){
        startPostponedEnterTransition()
    }
}