package com.rcacao.marvelchallenge.view.ui.list

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
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.rcacao.marvelchallenge.R
import com.rcacao.marvelchallenge.databinding.FragmentListBinding
import com.rcacao.marvelchallenge.view.model.UpdateItemEvent
import com.rcacao.marvelchallenge.view.viewmodel.CharactersViewModel
import com.rcacao.marvelchallenge.view.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ListFragment : Fragment() {

    private val charactersViewModel: CharactersViewModel by activityViewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var searchJob: Job? = null
    private lateinit var binding: FragmentListBinding

    @Inject
    lateinit var adapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.configureDefaultToolbar()

        initAdapter()
        binding.buttonRetry.setOnClickListener { adapter.retry() }

        val query: String = charactersViewModel.currentQuery
        search(query)
        initSearchAndPosition(query)
        initSwipe()
        observeListItemUpdate()
    }

    private fun observeListItemUpdate() {
        sharedViewModel.updateItem.observe(
            viewLifecycleOwner,
            Observer { event ->
                event.getContentIfNotHandled()?.let { handleListItemUpdate(it) }
            })
    }

    private fun handleListItemUpdate(updateData: UpdateItemEvent) {
        adapter.changeFav(updateData.pos, updateData.value)
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
        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    binding.recyclerView.scrollToPosition(0)
                }
        }
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
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            charactersViewModel.searchCharacter(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initAdapter() {
        binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        adapter.addLoadStateListener { loadState: CombinedLoadStates ->
            binding.recyclerView.isVisible = isSuccess(loadState) && !isError(loadState)
            binding.progressBar.isVisible = isLoading(loadState)
            binding.buttonRetry.isVisible = isError(loadState)

            val errorState: LoadState.Error? = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(context, "\uD83D\uDE28 Wooops ${it.error}", Toast.LENGTH_LONG).show()
            }
        }
        binding.recyclerView.adapter = adapter
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