package com.rcacao.marvelchallenge.view.ui.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rcacao.marvelchallenge.view.ui.favorites.FavoritesListFragment
import com.rcacao.marvelchallenge.view.ui.list.ListFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class PagesAdapter @Inject constructor(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    private val listFragment: ListFragment = ListFragment.newInstance()
    private val favoritesListFragment: FavoritesListFragment = FavoritesListFragment.newInstance()

    override fun createFragment(position: Int): Fragment {
        return if (position == 0) {
            listFragment
        } else {
            favoritesListFragment
        }
    }
}
