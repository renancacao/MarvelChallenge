package com.rcacao.marvelchallenge.view.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import com.rcacao.marvelchallenge.R
import com.rcacao.marvelchallenge.view.model.NavigationEvent
import com.rcacao.marvelchallenge.view.model.ToolbarState
import com.rcacao.marvelchallenge.view.ui.details.DetailsFragment
import com.rcacao.marvelchallenge.view.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    private var menu: Menu? = null
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar)
        supportFragmentManager.addOnBackStackChangedListener(this)
        observeViewModel()
    }

    override fun onBackStackChanged() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            setDefaultToolbar()
        }
    }

    private fun navigateToDetails() {
        supportFragmentManager.commit {
            add<DetailsFragment>(R.id.fragment_container, null, null)
            addToBackStack(null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        this.menu = menu
        return true
    }

    private fun observeViewModel() {
        sharedViewModel.toolbarState.observe(this, Observer {
            handleToolbarState(it)
        })
        sharedViewModel.updateActionFav.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { handleUpdateFavIcon(it) }
        })
        sharedViewModel.navigationEvent.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { handleNavigation(it) }
        })
    }

    private fun handleNavigation(navigationEvent: NavigationEvent) {
        when (navigationEvent) {
            is NavigationEvent.NavigateToDetails -> navigateToDetails()
        }
    }

    private fun handleUpdateFavIcon(isFav: Boolean) {
        menu?.let {
            val item: MenuItem = it.findItem(R.id.action_favorite)
            updateFavIcon(item, isFav)
        }
    }

    private fun handleToolbarState(toolbarState: ToolbarState) {
        when (toolbarState) {
            is ToolbarState.DefaultToolbar -> setDefaultToolbar()
            is ToolbarState.DetailsToolbar -> setDetailsToolbar(
                toolbarState.name,
                toolbarState.isFavorite
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                sharedViewModel.selectedCharacter.value?.let {
                    sharedViewModel.starCharacter(it, sharedViewModel.currentPosition)
                }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setDetailsToolbar(name: String, isFavorite: Boolean) {
        supportActionBar?.let {
            it.title = name
            it.setDisplayHomeAsUpEnabled(true)
        }
        menu?.let {
            val item: MenuItem = it.findItem(R.id.action_favorite)
            item.isVisible = true
            updateFavIcon(item, isFavorite)
        }
    }

    private fun updateFavIcon(item: MenuItem, isFavorite: Boolean) {
        item.icon =
            if (isFavorite) getDrawable(R.drawable.ic_baseline_star_24)
            else getDrawable(R.drawable.ic_baseline_star_border_24)
    }

    private fun setDefaultToolbar() {
        supportActionBar?.let {
            it.title = "MarvelChallenge"
            it.setDisplayHomeAsUpEnabled(false)
        }
        menu?.let {
            it.findItem(R.id.action_favorite)?.isVisible = false
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }
}

