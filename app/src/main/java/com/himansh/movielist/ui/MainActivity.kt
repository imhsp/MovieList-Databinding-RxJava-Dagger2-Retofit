package com.himansh.movielist.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.himansh.movielist.R
import com.himansh.movielist.data.model.MovieObject
import com.himansh.movielist.data.model.SearchObject
import com.himansh.movielist.data.rest.RepoService
import com.himansh.movielist.util.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    companion object {
        private const val API_KEY = "94a221d"
    }

    private lateinit var mSearchView: SearchView
    private lateinit var listView: RecyclerView
    private lateinit var adapter: CustomAdapter

    private var movieList = listOf<MovieObject>()

    private lateinit var repoService: RepoService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        repoService = RetrofitClientInstance.retrofitInstance.create(RepoService::class.java)
        initViews()
    }

    private fun initViews() {
        ProgressDialog.initialise(this)

        listView = findViewById<View>(R.id.movie_list) as RecyclerView

        adapter = CustomAdapter(this, movieList) {
            launchMovieInfo(movieList[it].imdbID)
        }

        listView.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        listView.layoutManager = linearLayoutManager

        mSearchView = findViewById<View>(R.id.movie_search) as SearchView
        mSearchView.isIconifiedByDefault = false
        mSearchView.setOnQueryTextListener(this)
        mSearchView.isSubmitButtonEnabled = true
        mSearchView.queryHint = "Search Movie Here"
        mSearchView.setQuery("Home", true)
    }

    private fun searchMovie(query: String) {
        ProgressDialog.show()

        val searchMovieCall: Call<SearchObject> = repoService.getMoviesList(query, API_KEY)
        searchMovieCall.enqueue(object : Callback<SearchObject?> {
            override fun onResponse(call: Call<SearchObject?>?, response: retrofit2.Response<SearchObject?>) {
                ProgressDialog.dismiss()
                Log.d("TAG", response.code().toString() + "")
                val resource: SearchObject = response.body()!!
                movieList = resource.Search
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