package com.himansh.movielist.ui.screens.search

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.himansh.movielist.R
import com.himansh.movielist.data.model.MovieObject
import com.himansh.movielist.databinding.ActivityMainBinding
import com.himansh.movielist.domain.mappers.ResultMap
import com.himansh.movielist.ui.adapters.MovieListAdapter
import com.himansh.movielist.ui.screens.detail.MovieInfo


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: MovieListAdapter
    private lateinit var binding: ActivityMainBinding

    private var movieList = arrayListOf<MovieObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel.searchData.observe(this) {
            when (it) {
                is ResultMap.Loading -> {
                    binding.loadingView.isVisible = true
                    binding.movieList.isVisible = false
                    binding.errorView.isVisible = false
                }
                is ResultMap.Success -> {
                    binding.loadingView.isVisible = false
                    binding.movieList.isVisible = true
                    binding.errorView.isVisible = false
                    if (it.movies.isNotEmpty()) {
                        movieList.clear()
                        movieList.addAll(it.movies)
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(
                            this, "NO RESULTS FOUND",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                is ResultMap.Failure -> {
                    binding.loadingView.isVisible = false
                    binding.movieList.isVisible = false
                    binding.errorView.isVisible = true
                }
            }
        }

        initViews()
    }

    private fun initViews() {

        adapter = MovieListAdapter(this, movieList) {
            launchMovieInfo(movieList[it].imdbID)
        }

        binding.movieList.adapter = adapter
        binding.movieList.layoutManager = LinearLayoutManager(this)

        binding.movieSearch.isIconifiedByDefault = false
        binding.movieSearch.setOnQueryTextListener(this)
        binding.movieSearch.isSubmitButtonEnabled = true
        binding.movieSearch.queryHint = "Search Movie Here"
        binding.movieSearch.setQuery("Home", true)
    }

    override fun onQueryTextChange(newText: String): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.search(query)
        return true
    }

    private fun launchMovieInfo(imdb: String) {
        val intent = Intent(this, MovieInfo::class.java)
        intent.putExtra("imdbID", imdb)
        startActivity(intent)
    }

    public override fun onDestroy() {
        super.onDestroy()
    }
}