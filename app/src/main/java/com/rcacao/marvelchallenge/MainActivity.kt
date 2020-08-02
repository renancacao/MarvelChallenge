package com.rcacao.marvelchallenge

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.rcacao.marvelchallenge.view.CharactersAdapter
import com.rcacao.marvelchallenge.view.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CharactersViewModel by viewModels()

    private val adapter: CharactersAdapter = CharactersAdapter()
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initSearch()
        search("")
        initAdapter()
    }

    private fun initSearch() {
        lifecycleScope.launch {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { recyclerView.scrollToPosition(0) }
        }
    }

    private fun search(query: String) {
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchRepo(query).collectLatest {
                Timber.d("Novos Dados de API")
                adapter.submitData(it)
            }
        }
    }

    private fun initAdapter() {
        buttonRetry.setOnClickListener(View.OnClickListener { adapter.retry() })
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        adapter.addLoadStateListener { loadState ->
            recyclerView.isVisible = isSuccess(loadState) && !isError(loadState)
            progressBar.isVisible = isLoading(loadState)
            buttonRetry.isVisible = isError(loadState)

            val errorState: LoadState.Error? = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(this, "\uD83D\uDE28 Wooops ${it.error}", Toast.LENGTH_LONG).show()
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


}

