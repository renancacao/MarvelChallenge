package com.rcacao.marvelchallenge.view.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import com.rcacao.marvelchallenge.R
import com.rcacao.marvelchallenge.view.model.ToolbarState
import com.rcacao.marvelchallenge.view.ui.details.DetailsFragment
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
        observeViewModel()
    }

    fun navigateToDetails() {
        supportFragmentManager.commit {
            // Replace whatever is in the fragment_container view with a new Fragment, generated
            // from the FragmentFactory, and give it an argument for the selected article
            replace<DetailsFragment>(R.id.fragment_container, null, null)
            // add the transaction to the back stack so the user can navigate back
            addToBackStack(null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        this.menu = menu
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return true
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

