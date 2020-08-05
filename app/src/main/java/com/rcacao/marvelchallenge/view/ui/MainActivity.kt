package com.rcacao.marvelchallenge.view.ui

import android.os.Bundle
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

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(my_toolbar)
        setupActionBarWithNavController(findNavController(R.id.nav_host_fragment))
        observeViewModel()


    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
    }

    private fun observeViewModel() {
        sharedViewModel.toolbarState.observe(this, Observer {
            handleToolbarState(it)
        })
    }

    private fun handleToolbarState(toolbarState: ToolbarState) {
        when (toolbarState) {
            is ToolbarState.DefaultToolbar -> setDefaultToolbar()
            is ToolbarState.DetailsToolbar -> setDetailsToolbar(toolbarState.name)
        }
    }

    private fun setDetailsToolbar(name: String) {
        supportActionBar?.let {
            supportActionBar?.title = name
        }
    }

    private fun setDefaultToolbar() {
        supportActionBar?.let {
//            it.setDisplayHomeAsUpEnabled(false)
//            it.setDisplayShowTitleEnabled(true)
//            txtToolbar.text = ""
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home ->
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}

