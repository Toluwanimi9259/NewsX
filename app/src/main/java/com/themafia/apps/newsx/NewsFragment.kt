package com.themafia.apps.newsx

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.themafia.apps.newsx.data.util.Resource
import com.themafia.apps.newsx.databinding.FragmentNewsBinding
import com.themafia.apps.newsx.presentation.adapters.Adapter
import com.themafia.apps.newsx.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {

    private lateinit var newsViewModel: NewsViewModel

    private lateinit var fragmentNewsBinding: FragmentNewsBinding

    lateinit var newsAdapter: Adapter

    private val country = "us"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0

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
        newsAdapter = (activity as MainActivity).adapter
//        Toast.makeText(activity, "NORMAL MOTHERFUCKER", Toast.LENGTH_LONG).show()
        initRecyclerView()
        viewNews()
        setSearchView()
    }

    private fun viewNews() {
        newsViewModel.getNewsHeadLines(country, page)
        newsViewModel.newsHeadlines.observe(viewLifecycleOwner, Observer {
            when (it) {

                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let {
                        Log.d("MYTAG", "News Fragment  Success  ${it.articles.toList()}")
                        newsAdapter.differ.submitList(it.articles.toList())
                        pages = if (it.totalResults % 20 == 0) {
                            it.totalResults / 20
                        } else {
                            it.totalResults / 20 + 1
                        }
                        isLastPage = page == pages
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }

                is Resource.Error -> {
                    hideProgressBar()
                    Log.d("MYTAG" , "An error occurred View" + it.message)
                    Toast.makeText(activity, "An error occurred An error occurred View : " + it.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    private fun setSearchView(){
        fragmentNewsBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewSearchedNews("$query")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                MainScope().launch {
//                    delay(3000)
                    viewSearchedNews("$newText")
                }
                return false
            }

        })

        fragmentNewsBinding.searchView.setOnCloseListener(object : SearchView.OnCloseListener,
            android.widget.SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                initRecyclerView()
                viewNews()
                return false
            }

        })
    }

    private fun viewSearchedNews(keyWord : String){
        newsViewModel.getSearchedNews2Headlines(keyWord)
        newsViewModel.searchedNews2Headlines.observe(viewLifecycleOwner , Observer {
            when (it) {
                is Resource.Success -> {
                    hideProgressBar()
                    it.data?.let {
                        Log.d("MYTAG", "News Fragment  Success  ${it.articles.toList()}")
                        newsAdapter.differ.submitList(it.articles.toList())
                        pages = if (it.totalResults % 20 == 0) {
                            it.totalResults / 20
                        } else {
                            it.totalResults / 20 + 1
                        }
                        isLastPage = page == pages
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }

                is Resource.Error -> {
                    hideProgressBar()
                    Log.d("MYTAG" , "An error occurred " + it.message)
                    Toast.makeText(activity, "An error occurred " + it.message, Toast.LENGTH_LONG)
                        .show()
                }
            }
        })
    }

    private fun initRecyclerView() {
        newsAdapter = Adapter()
        fragmentNewsBinding.rvNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@NewsFragment.onScrollListener)
        }
    }

    private fun showProgressBar() {
        isLoading = true
        fragmentNewsBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        isLoading = false
        fragmentNewsBinding.progressBar.visibility = View.INVISIBLE
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }

        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = fragmentNewsBinding.rvNews.layoutManager as LinearLayoutManager
            val sizeOfTheCurrentList = layoutManager.itemCount
            val visibleItems = layoutManager.childCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()

            val hasReachedToEnd = topPosition + visibleItems >= sizeOfTheCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
            if (shouldPaginate) {
                page++
                newsViewModel.getNewsHeadLines(country, page)
                isScrolling = false

            }
        }
    }
}


