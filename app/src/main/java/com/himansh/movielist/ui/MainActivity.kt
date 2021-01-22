package com.himansh.movielist.ui

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.himansh.movielist.R
import com.himansh.movielist.data.model.MovieObject
import com.himansh.movielist.data.rest.AppController
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private var mSearchView: SearchView? = null
    private var pDialog: ProgressDialog? = null
    private val movieList = ArrayList<MovieObject>()
    private var listView: ListView? = null
    private var adapter: CustomAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mSearchView = findViewById<View>(R.id.movie_search) as SearchView
        mSearchView!!.isIconifiedByDefault = false
        mSearchView!!.setOnQueryTextListener(this)
        mSearchView!!.isSubmitButtonEnabled = true
        mSearchView!!.queryHint = "Search Movie Here"
        mSearchView!!.setQuery("Home", true)
        listView = findViewById<View>(R.id.movie_list) as ListView
        adapter = CustomAdapter(this, movieList)
        listView!!.adapter = adapter
        listView!!.onItemClickListener = OnItemClickListener { _, _, position, _ -> launchMovieInfo(movieList[position].imdb) }
        listView!!.isTextFilterEnabled = true
    }

    private fun searchMovie(query: String) {
        pDialog = ProgressDialog(this)
        pDialog!!.setMessage("Loading...")
        pDialog!!.show()
        val url = "http://www.omdbapi.com/?s=$query&apikey=94a221d"
        movieList.clear()
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            hidePDialog()
            val resultArray: JSONArray?
            try {
                resultArray = response.getJSONArray("Search")
                for (j in 0 until resultArray.length()) {
                    val obj = resultArray.getJSONObject(j)
                    val movie = MovieObject(obj.getString("Title"), obj.getString("Year"), obj.getString("imdbID"), obj.getString("Type"), obj.getString("Poster"))
                    movieList.add(movie)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            adapter!!.notifyDataSetChanged()
        }) { error ->
            showError(error)
            hidePDialog()
        }
        AppController.instance.addToRequestQueue(jsonObjectRequest)
    }

    private fun showError(error: VolleyError) {
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