package com.himansh.movielist.ui.screens.detail


import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.himansh.movielist.R
import com.himansh.movielist.data.model.MovieObject
import com.himansh.movielist.databinding.ActivityMovieInfoBinding
import com.himansh.movielist.domain.mappers.ResultMap
import com.himansh.movielist.ui.MovieApplication
import com.squareup.picasso.Picasso
import javax.inject.Inject

class MovieInfo : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: DetailsViewModelFactory

    private val viewModel: DetailsViewModel by viewModels { viewModelFactory }
    private lateinit var binding: ActivityMovieInfoBinding

    private lateinit var movieID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as MovieApplication).applicationComponent.inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_info)
        movieID = intent.getStringExtra("imdbID").toString()
        binding.tryAgainCta.setOnClickListener {
            viewModel.getMovieInfo(movieID)
        }

        getMovieDetails()

    }

    private fun getMovieDetails() {

        viewModel.movieData.observe(this) {
            when (it) {
                is ResultMap.Loading -> {
                    binding.loadingView.isVisible = true
                    binding.dataView.isVisible = false
                    binding.errorView.isVisible = false
                }

                is ResultMap.Success -> {
                    binding.loadingView.isVisible = false
                    binding.dataView.isVisible = true
                    binding.errorView.isVisible = false
                    handleResults(it.movies.first())
                }

                is ResultMap.Failure -> {
                    binding.loadingView.isVisible = false
                    binding.dataView.isVisible = false
                    binding.errorView.isVisible = true
                    if (it.throwable.message.isNullOrEmpty()) {
                        binding.errorMessage.text = getString(R.string.error_message)
                    } else {
                        binding.errorMessage.text = it.throwable.message
                    }
                }
            }
        }

        viewModel.getMovieInfo(movieID)
    }

    private fun handleResults(movieInfo: MovieObject) {
        binding.movieObject = movieInfo

        Picasso.get()
            .load(binding.movieObject?.Poster)
            .into(binding.moviePoster)
    }

    public override fun onDestroy() {
        super.onDestroy()
    }
}