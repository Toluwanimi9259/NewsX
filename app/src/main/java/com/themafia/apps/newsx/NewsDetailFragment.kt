package com.themafia.apps.newsx

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.themafia.apps.newsx.databinding.FragmentNewsDetailBinding

class NewsDetailFragment : Fragment() {

    lateinit var fragmentNewsDetailBinding: FragmentNewsDetailBinding

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

        val newsUrl = requireArguments().getString("news_url")

        if (newsUrl == null || newsUrl == ""){
            Toast.makeText(activity, "Url is NULL", Toast.LENGTH_LONG).show()
        }

        fragmentNewsDetailBinding.webView.apply {
            webViewClient = WebViewClient()
            if (newsUrl!=""){
                loadUrl(newsUrl!!)
            }
        }

    }
}