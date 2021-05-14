package com.example.latestnews.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.latestnews.R
import com.example.latestnews.db.ArticleDatabase
import com.example.latestnews.repository.NewsRepository
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.activity_news_content.*


class NewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        // Set margin top to CoordinatorLayout
        coordinator.setOnApplyWindowInsetsListener { v, insets ->
            val params = v.layoutParams as ViewGroup.MarginLayoutParams
            params.topMargin = insets.systemWindowInsetTop
            insets
        }

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application,newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)


        setNavigation()
//        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
    }


    private fun setNavigation() {
        val navController = findNavController(R.id.newsNavHostFragment)

        // Set ActionBar
        // AppBarConfiguration(navController.graph) -> Only considers the home fragment in the navigation graph as a top level
        // Declare which fragments are the top level destinations
        // If you have a bottomNavigationView with multiple fragments to switch -> AppBarConfiguration(setOf(R.id.fragment1, R.id.fragment2, ...))
        appBarConfiguration = AppBarConfiguration(
            setOf(
                // R.id.homeFragment,
                R.id.breakingNewsFragment
                // R.id.myProfileFragment
            )
        )

//        setupActionBarWithNavController(navController, appBarConfiguration)

        main_toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search_menu -> {
                    findNavController(R.id.newsNavHostFragment).navigate(R.id.action_breakingNewsFragment_to_searchNewsFragment)
                    true
                }
                R.id.night_day_mode -> {
                    val mode = application.applicationContext?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
                    when (mode) {
                        Configuration.UI_MODE_NIGHT_YES -> {AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)}
                        Configuration.UI_MODE_NIGHT_NO -> {AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)}
                        Configuration.UI_MODE_NIGHT_UNDEFINED -> {AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)}
                    }
                    true
                }

                else -> false
            }
        }

        // Set BottomNavigationView
         bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->



            main_toolbar.title = when(destination.id){
                R.id.searchNewsFragment -> "Search News"
                R.id.savedNewsFragment -> "Saved Articles"
                else -> getString(R.string.app_name)
            }

            when (destination.id) {
                R.id.articleFragment -> expandAppBarLayout(expanded = false, animate = false)
                R.id.searchNewsFragment ->expandAppBarLayout(expanded = false, animate = false)
                R.id.savedNewsFragment ->expandAppBarLayout(expanded = false,animate = false)

                else -> {
                    expandAppBarLayout(expanded = true, animate = true)
                    supportActionBar?.let {
                        // it.setDisplayHomeAsUpEnabled(true)
                        // it.setHomeAsUpIndicator(R.drawable.ic_search_black_24dp)
                    }
                }
            }
        }
    }
    private fun expandAppBarLayout(expanded: Boolean, animate: Boolean) {
        appbar.setExpanded(expanded, animate)

    }





}
