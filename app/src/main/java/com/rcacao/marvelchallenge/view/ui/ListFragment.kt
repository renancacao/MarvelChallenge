package com.rcacao.marvelchallenge.view.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.rcacao.marvelchallenge.R
import com.rcacao.marvelchallenge.view.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val viewModel: CharactersViewModel by activityViewModels()
    private var searchJob: Job? = null

    private val adapter: CharactersAdapter =
        CharactersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        buttonRetry.setOnClickListener { adapter.retry() }

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: ""
        search(query)
        initSearch(query)
        initSwipe()
    }

    private fun initSwipe() {
        swipeWrapper.setOnRefreshListener {
            swipeWrapper.isRefreshing = false
            updateRepoListFromInput()
        }
        swipeWrapper.setColorSchemeResources(
            R.color.colorPrimary,
            R.color.colorPrimaryDark,
            R.color.colorPrimary,
            R.color.colorPrimaryDark
        )
    }

    private fun initSearch(query: String) {
        txtSearch.setText(query)
        setSearchTextListeners()
        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { recyclerView.scrollToPosition(0) }
        }
    }

    private fun setSearchTextListeners() {
        txtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
        txtSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRepoListFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updateRepoListFromInput() {
        txtSearch.text.trim().let {
            search(it.toString())
        }
    }

    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchCharacter(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initAdapter() {
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter.addLoadStateListener { loadState: CombinedLoadStates ->
            recyclerView.isVisible = isSuccess(loadState) && !isError(loadState)
            progressBar.isVisible = isLoading(loadState)
            buttonRetry.isVisible = isError(loadState)

            val errorState: LoadState.Error? = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(context, "\uD83D\uDE28 Wooops ${it.error}", Toast.LENGTH_LONG).show()
            }
        }
        recyclerView.adapter = adapter
    }

    private fun isSuccess(loadState: CombinedLoadStates) =
        loadState.source.refresh is LoadState.NotLoading

    private fun isError(loadState: CombinedLoadStates) =
        loadState.append is LoadState.Error
                || loadState.source.refresh is LoadState.Error

    private fun isLoading(loadState: CombinedLoadStates): Boolean =
        loadState.append is LoadState.Loading
                || loadState.source.refresh is LoadState.Loading

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
    }
}