package com.himansh.movielist.ui.screens.detail


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.himansh.movielist.R
import com.himansh.movielist.data.remote.RepoService
import com.himansh.movielist.data.remote.RetrofitClientInstance
import com.himansh.movielist.databinding.ActivityMovieInfoBinding
import com.himansh.movielist.domain.GetMovieDetailsUseCase
import com.himansh.movielist.domain.mappers.ResultMap
import com.himansh.movielist.util.ProgressDialog
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieInfo : AppCompatActivity() {

    companion object {
        private const val API_KEY = "94a221d"
    }

    private var pDialog: ProgressDialog? = null
    private lateinit var repoService: RepoService
    private lateinit var binding: ActivityMovieInfoBinding

    private lateinit var movieID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_info)
        movieID = intent.getStringExtra("imdbID").toString()

        repoService = RetrofitClientInstance.retrofitInstance.create(RepoService::class.java)
        ProgressDialog.initialise(this)

        getMovieDetails()

    }

    private fun getMovieDetails() {

        ProgressDialog.show()

        val getMovieDetailsUseCase = GetMovieDetailsUseCase(repoService)
        val movieListObservable = getMovieDetailsUseCase.execute(movieID, API_KEY)
        movieListObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError)
    }

    private fun handleResults(result: ResultMap) {
        ProgressDialog.dismiss()
        val successResult = (result as ResultMap.Success).movies[0]
        binding.movieObject = successResult

        Picasso.get()
                .load(binding.movieObject?.Poster)
                .into(binding.moviePoster)
    }

    private fun handleError(t: Throwable) {
        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again",
                Toast.LENGTH_LONG).show()
        showError(t.message!!)
        ProgressDialog.dismiss()
    }

    private fun showError(error: String) {
        Toast.makeText(this, "I'll handle this Error if you hire me!\n\n$error", Toast.LENGTH_LONG).show()
    }

    public override fun onDestroy() {
        super.onDestroy()
        hidePDialog()
    }

    private fun hidePDialog() {
        if (pDialog != null) {
            pDialog!!.dismiss()
            pDialog = null
        }
    }
}