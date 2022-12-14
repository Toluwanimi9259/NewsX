package com.themafia.apps.newsx

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.themafia.apps.newsx.data.retrofit.dataclasses.Article
import com.themafia.apps.newsx.databinding.FragmentNewsDetailBinding
import com.themafia.apps.newsx.presentation.viewmodel.NewsViewModel

class NewsDetailFragment : Fragment() {

    lateinit var fragmentNewsDetailBinding: FragmentNewsDetailBinding

    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsDetailBinding = FragmentNewsDetailBinding.bind(view)
        newsViewModel = (activity as MainActivity).newsViewModel

        val newsUrl = requireArguments().getString("news_url")
        val artikle : Article = requireArguments().get("selected_article") as Article

//        if (newsUrl == null || newsUrl == ""){
//            Toast.makeText(activity, "Url is NULL", Toast.LENGTH_LONG).show()
//        }

        fragmentNewsDetailBinding.webView.apply {
            webViewClient = WebViewClient()
            try {
                loadUrl(newsUrl!!)
            }catch (e : Exception){
                Log.d("MYTAG" , "DetailFragment : " + e.message.toString())
            }
//
//
        }

        fragmentNewsDetailBinding.fabSaveNews.setOnClickListener {
           newsViewModel.saveNewsToDB(artikle)
            Snackbar.make(view , "Saved Successfully" , Snackbar.LENGTH_LONG).show()
        }

    }
}