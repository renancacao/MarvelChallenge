package com.rcacao.marvelchallenge.view.ui.favorites

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.rcacao.marvelchallenge.R
import com.rcacao.marvelchallenge.databinding.FragmentListBinding
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import com.rcacao.marvelchallenge.view.model.UpdateFavoriteEvent
import com.rcacao.marvelchallenge.view.model.details.favorites.FavoritesStateUi
import com.rcacao.marvelchallenge.view.viewmodel.FavoritesViewModel
import com.rcacao.marvelchallenge.view.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesListFragment : Fragment() {

    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentListBinding

    @Inject
    lateinit var adapter: FavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setListeners()
        val query: String = favoritesViewModel.currentQuery
        search(query)
        initSearchAndPosition(query)
        initSwipe()
        observeViewModels()
    }

    private fun setListeners() {
        buttonRetry.setOnClickListener { updateRepoListFromInput() }
    }

    private fun observeViewModels() {
        favoritesViewModel.favoritesList.observe(viewLifecycleOwner, Observer {
            adapter.data = it as ArrayList<CharacterModel>?
            adapter.notifyDataSetChanged()
        })
        favoritesViewModel.favoritesStateUi.observe(viewLifecycleOwner, Observer {
            handleFavoritesUiState(it)
        })
        sharedViewModel.favoriteEvent.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { handleEvent(it) }
        })
    }

    private fun handleEvent(event: UpdateFavoriteEvent) {
        when (event) {
            is UpdateFavoriteEvent.FavoriteRemove -> removeItem(event.pos)
            is UpdateFavoriteEvent.UpdateList -> updateRepoListFromInput()
        }
    }

    private fun removeItem(pos: Int) {
        adapter.data?.let {
            it.removeAt(pos)
            adapter.notifyDataSetChanged()
        }
    }

    private fun handleFavoritesUiState(state: FavoritesStateUi) {
        binding.progressBar.isVisible = state is FavoritesStateUi.Loading
        binding.recyclerView.isInvisible =
            state !is FavoritesStateUi.Loading && state !is FavoritesStateUi.Loaded
        binding.buttonRetry.isVisible = state is FavoritesStateUi.Error
        binding.textMessage.isVisible = state is FavoritesStateUi.Error

        if (state is FavoritesStateUi.Error) {
            binding.textMessage.text = state.errorMsg
        }
    }

    private fun initSwipe() {
        binding.swipeWrapper.setOnRefreshListener {
            sharedViewModel.currentPosition = 0
            binding.swipeWrapper.isRefreshing = false
            updateRepoListFromInput()
        }
        binding.swipeWrapper.setColorSchemeResources(
            R.color.colorPrimary,
            R.color.colorPrimaryDark,
            R.color.colorPrimary,
            R.color.colorPrimaryDark
        )
    }

    private fun initSearchAndPosition(query: String) {
        binding.txtSearch.setText(query)
        setSearchTextListeners()
    }

    private fun setSearchTextListeners() {
        binding.txtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                sharedViewModel.currentPosition = 0
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        binding.txtSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                sharedViewModel.currentPosition = 0
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updateRepoListFromInput() {
        binding.txtSearch.text?.trim().let {
            search(it.toString())
        }
    }

    private fun search(query: String) {
        favoritesViewModel.searchCharacter(query)
    }

    private fun initAdapter() {
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, resources.getInteger(R.integer.item_columns))
        binding.recyclerView.adapter = adapter
    }


    companion object {
        @JvmStatic
        fun newInstance(): FavoritesListFragment {
            return FavoritesListFragment()
        }
    }

}