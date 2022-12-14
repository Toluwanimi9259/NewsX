package com.themafia.apps.newsx

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.themafia.apps.newsx.databinding.FragmentSavedNewsBinding
import com.themafia.apps.newsx.presentation.adapters.Adapter
import com.themafia.apps.newsx.presentation.viewmodel.NewsViewModel


class SavedNewsFragment : Fragment() {

    private lateinit var binding: FragmentSavedNewsBinding

    private lateinit var newsViewModel: NewsViewModel

    private lateinit var newsAdapter : Adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedNewsBinding.bind(view)

        newsViewModel = (activity as MainActivity).newsViewModel

        newsAdapter = Adapter("savedNews")

//        newsAdapter = (activity as MainActivity).adapter

//        newsAdapter.location = "savedNews"

        newsViewModel.getSavedArticles().observe(viewLifecycleOwner , Observer {
            newsAdapter.differ.submitList(it)
        })

        initRecyclerView()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                newsViewModel.deleteSavedArticle(article)
                Snackbar.make(view!!, "Deleted Succesfully" , Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo"){
                            newsViewModel.saveNewsToDB(article)
                        }
                        show()
                    }

            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvSaved)
        }

    }




    private fun initRecyclerView(){
        binding.rvSaved.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}