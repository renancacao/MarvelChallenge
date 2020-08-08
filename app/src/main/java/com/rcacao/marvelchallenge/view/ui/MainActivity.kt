package com.rcacao.marvelchallenge.view.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.rcacao.marvelchallenge.R
import com.rcacao.marvelchallenge.view.model.ToolbarState
import com.rcacao.marvelchallenge.view.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var menu: Menu? = null
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar)
        setupActionBarWithNavController(findNavController(R.id.nav_host_fragment))
        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        this.menu = menu
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

    private fun observeViewModel() {
        sharedViewModel.toolbarState.observe(this, Observer {
            handleToolbarState(it)
        })
        sharedViewModel.updateActionFav.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let { handleUpdateFavIcon(it) }
        })
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
            supportActionBar?.title = name
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
        menu?.let {
            it.findItem(R.id.action_favorite)?.isVisible = false
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home ->
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}

