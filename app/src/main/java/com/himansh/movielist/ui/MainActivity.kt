package com.himansh.movielist.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.himansh.movielist.R
import com.himansh.movielist.data.model.MovieObject
import com.himansh.movielist.data.model.SearchObject
import com.himansh.movielist.data.rest.RepoService
import com.himansh.movielist.util.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    companion object{
        private val API_KEY = "94a221d"
    }
    private var mSearchView: SearchView? = null
    private var pDialog: ProgressDialog? = null
    private var movieList = listOf<MovieObject>()
    private var listView: ListView? = null
    private var adapter: CustomAdapter? = null

    private lateinit var repoService: RepoService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repoService = RetrofitClientInstance.retrofitInstance.create(RepoService::class.java)

        listView = findViewById<View>(R.id.movie_list) as ListView
        adapter = CustomAdapter(this, movieList)
        listView!!.adapter = adapter
        listView!!.onItemClickListener = OnItemClickListener { _, _, position, _ -> launchMovieInfo(movieList[position].imdbID) }
        listView!!.isTextFilterEnabled = true

        mSearchView = findViewById<View>(R.id.movie_search) as SearchView
        mSearchView!!.isIconifiedByDefault = false
        mSearchView!!.setOnQueryTextListener(this)
        mSearchView!!.isSubmitButtonEnabled = true
        mSearchView!!.queryHint = "Search Movie Here"
        mSearchView!!.setQuery("Home", true)
    }

    private fun searchMovie(query: String) {
        pDialog = ProgressDialog(this)
        pDialog!!.setMessage("Loading...")
        pDialog!!.show()
        val url = "http://www.omdbapi.com/?s=$query&apikey=94a221d"

        val searchMovieCall:Call<SearchObject> = repoService.getMoviesList(query, API_KEY)
        searchMovieCall.enqueue(object : Callback<SearchObject?> {
            override fun onResponse(call: Call<SearchObject?>?, response: retrofit2.Response<SearchObject?>) {
                hidePDialog()
                Log.d("TAG", response.code().toString() + "")
                val resource: SearchObject = response.body()!!
                movieList = resource.Search
                adapter!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<SearchObject?>, t: Throwable) {
                call.cancel()
                showError(t.message!!)
                hidePDialog()
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
        hidePDialog()
    }

    private fun hidePDialog() {
        if (pDialog != null) {
            pDialog!!.dismiss()
            pDialog = null
        }
    }
}