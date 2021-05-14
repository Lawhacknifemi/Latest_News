package com.example.latestnews.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.latestnews.R
import com.example.latestnews.ui.NewsActivity
import com.example.latestnews.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {
    lateinit var viewModel: NewsViewModel
    val args : ArticleFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel

        val article = args.article
        webView.apply {
            webViewClient  = WebViewClient()
            loadUrl(article.url)

        }
        val id = args.article.urlToImage
        main_backdrop.transitionName = id



        fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article Saved Successfully!",Snackbar.LENGTH_SHORT).show()

        }
    }
}