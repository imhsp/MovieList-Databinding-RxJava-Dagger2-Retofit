package com.himansh.movielist.ui


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.himansh.movielist.R
import com.himansh.movielist.data.model.MovieObject
import com.himansh.movielist.data.rest.RepoService
import com.himansh.movielist.databinding.ActivityMovieInfoBinding
import com.himansh.movielist.util.RetrofitClientInstance
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback

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

        val searchMovieCall: Call<MovieObject> = repoService.getMovieDetail(movieID, API_KEY)
        searchMovieCall.enqueue(object : Callback<MovieObject?> {
            override fun onResponse(call: Call<MovieObject?>?, response: retrofit2.Response<MovieObject?>) {
                ProgressDialog.dismiss()
                Log.d("TAG", response.code().toString() + "")
                val resource: MovieObject = response.body()!!
                binding.movieObject = resource

                Picasso.get()
                        .load(resource.Poster)
                        .into(binding.moviePoster)
            }

            override fun onFailure(call: Call<MovieObject?>, t: Throwable) {
                call.cancel()
                showError(t.message!!)
                ProgressDialog.dismiss()
            }
        })
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