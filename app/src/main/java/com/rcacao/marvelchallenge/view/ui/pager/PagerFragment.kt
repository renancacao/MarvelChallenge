package com.rcacao.marvelchallenge.view.ui.pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.rcacao.marvelchallenge.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_pager.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PagerFragment : Fragment() {

    private lateinit var pagesAdapter: PagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pagesAdapter =
            PagesAdapter(this)
        pager.adapter = pagesAdapter
        setupTabLayout()
    }

    private fun setupTabLayout() {
        TabLayoutMediator(tab_layout, pager) { tab, position ->
            when (position) {
                0 -> tab.text = "Characters"
                else -> tab.text = "Favorites"
            }
        }.attach()
    }
}