package com.rcacao.marvelchallenge.view.ui.list

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.rcacao.marvelchallenge.R
import com.rcacao.marvelchallenge.databinding.FragmentListBinding
import com.rcacao.marvelchallenge.view.model.UpdateCharactersEvent
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

    private val charactersViewModel: CharactersViewModel by viewModels()
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

        initAdapter()
        binding.buttonRetry.setOnClickListener { adapter.retry() }

        val query: String = charactersViewModel.currentQuery
        search(query)
        initSearchAndPosition(query)
        initSwipe()
        observeViewModel()
    }

    private fun observeViewModel() {
        sharedViewModel.characterEvent.observe(
            viewLifecycleOwner,
            Observer { event ->
                event.getContentIfNotHandled()?.let { handleListItemUpdate(it) }
            })

        charactersViewModel.errorMessage.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { handleError(it) }
        })

        charactersViewModel.listVisibility.observe(viewLifecycleOwner, Observer {
            binding.recyclerView.isVisible = it
        })

        charactersViewModel.loadingVisibility.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = it
        })

        charactersViewModel.errorVisibility.observe(viewLifecycleOwner, Observer {
            binding.buttonRetry.isVisible = it
            binding.textMessage.isVisible = it
        })
    }

    private fun handleError(errorMessage: String) {
        binding.textMessage.text = errorMessage
    }

    private fun handleListItemUpdate(event: UpdateCharactersEvent) {
        when (event) {
            is UpdateCharactersEvent.UpdateItem -> changeFav(event.pos, event.value)
            is UpdateCharactersEvent.UpdateList -> updateRepoListFromInput()
        }
    }

    private fun changeFav(position: Int, value: Boolean) {
        adapter.getItemData(position)?.let {
            it.isFavorite = value
            adapter.notifyItemChanged(position)
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
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, resources.getInteger(R.integer.item_columns))
        adapter.addLoadStateListener { loadState: CombinedLoadStates ->
            charactersViewModel.stateChange(loadState)
        }
        binding.recyclerView.adapter = adapter
    }


    companion object {
        @JvmStatic
        fun newInstance(): ListFragment {
            return ListFragment()
        }
    }

}
