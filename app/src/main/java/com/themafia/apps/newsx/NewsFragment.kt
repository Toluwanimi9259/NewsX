package com.themafia.apps.newsx

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.themafia.apps.newsx.data.util.Resource
import com.themafia.apps.newsx.databinding.FragmentNewsBinding
import com.themafia.apps.newsx.presentation.adapters.Adapter
import com.themafia.apps.newsx.presentation.viewmodel.NewsViewModel

class NewsFragment : Fragment() {

    private lateinit var newsViewModel: NewsViewModel

    private lateinit var fragmentNewsBinding: FragmentNewsBinding

    private lateinit var newsAdapter: Adapter

    private val country = "us"
    private val page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentNewsBinding = FragmentNewsBinding.bind(view)
        newsViewModel = (activity as MainActivity).newsViewModel
        initRecyclerView()
        viewNews()


    }

    private fun viewNews() {
        newsViewModel.getNewsHeadLines(country , page)
        newsViewModel.newsHeadlines.observe(viewLifecycleOwner , Observer {
            when(it){
                
                is Resource.Success->{
                    hideProgressBar()
                    it.data?.let {
                        Log.i("MYTAG","News Fragment  Success  ${it.articles.toList().size}")
                        newsAdapter.differ.submitList(it.articles.toList())
                    }
                }
                
                is Resource.Loading->{
                    showProgressBar()
                }
                
                is Resource.Error->{
                    hideProgressBar()
                    Toast.makeText(activity,"An error occurred : ${it.message}", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun initRecyclerView() {
        newsAdapter = Adapter()
        fragmentNewsBinding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showProgressBar(){
        fragmentNewsBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        fragmentNewsBinding.progressBar.visibility = View.INVISIBLE
    }
}