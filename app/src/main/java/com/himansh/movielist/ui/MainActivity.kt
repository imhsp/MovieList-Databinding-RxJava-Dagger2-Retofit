package com.himansh.movielist.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.himansh.movielist.R
import com.himansh.movielist.data.model.MovieObject
import com.himansh.movielist.data.model.SearchObject
import com.himansh.movielist.data.remote.RepoService
import com.himansh.movielist.data.remote.RetrofitClientInstance
import com.himansh.movielist.databinding.ActivityMainBinding
import com.himansh.movielist.ui.adapters.CustomAdapter
import com.himansh.movielist.util.ProgressDialog
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    companion object {
        private const val API_KEY = "94a221d"
    }

    private lateinit var adapter: CustomAdapter
    private lateinit var binding: ActivityMainBinding

    private var movieList = arrayListOf<MovieObject>()

    private lateinit var repoService: RepoService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        repoService = RetrofitClientInstance.retrofitInstance.create(RepoService::class.java)
        initViews()
    }

    private fun initViews() {
        ProgressDialog.initialise(this)

        adapter = CustomAdapter(this, movieList) {
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

    private fun searchMovie(query: String) {
        ProgressDialog.show()

        val searchMovieCall: Call<SearchObject> = repoService.getMoviesList(query, API_KEY)
        searchMovieCall.enqueue(object : Callback<SearchObject?> {
            override fun onResponse(call: Call<SearchObject?>?, response: retrofit2.Response<SearchObject?>) {
                movieList.clear()
                ProgressDialog.dismiss()
                Log.d("TAG", response.code().toString() + "")
                val resource: SearchObject = response.body()!!
                if (resource.totalResults > 0)
                    movieList.addAll(resource.Search)

                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<SearchObject?>, t: Throwable) {
                call.cancel()
                showError(t.message!!)
                ProgressDialog.dismiss()
            }
        })
    }

    private fun showError(error: String) {
        Toast.makeText(this, "I'll handle this Error if you hire me!\n\n$error", Toast.LENGTH_LONG).show()
    }

    override fun onQueryTextChange(newText: String): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        searchMovie(query)
        return true
    }

    private fun launchMovieInfo(imdb: String) {
        val intent = Intent(this, MovieInfo::class.java)
        intent.putExtra("imdbID", imdb)
        startActivity(intent)
    }

    public override fun onDestroy() {
        super.onDestroy()
        ProgressDialog.dismiss()
    }
}