package com.himansh.movielist.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.himansh.movielist.R

class MovieInfo : AppCompatActivity() {
    var Title: TextView? = null
    var Released: TextView? = null
    var Rating: TextView? = null
    var Genre: TextView? = null
    var Director: TextView? = null
    var Writer: TextView? = null
    var Actor: TextView? = null
    var Plot: TextView? = null
    //var Poster: NetworkImageView? = null
    private var pDialog: ProgressDialog? = null
    private var url = "http://www.omdbapi.com/?i=tt0099785&apikey=94a221d"
    //var imageLoader: ImageLoader? = AppController.getInstance().getImageLoader()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_info)
        //Poster = findViewById(R.id.imageViewPoster)
        url = "http://www.omdbapi.com/?i=" + intent.getStringExtra("imdbID") + "&apikey=94a221d"
        Title = findViewById(R.id.textViewTitle)
        Released = findViewById(R.id.textViewReleased)
        Rating = findViewById(R.id.textViewIMDBRatings)
        Genre = findViewById(R.id.textViewGenre)
        Director = findViewById(R.id.textViewDirector)
        Writer = findViewById(R.id.textViewWriter)
        Actor = findViewById(R.id.textViewActors)
        Plot = findViewById(R.id.textViewPlot)
        pDialog = ProgressDialog(this)
        pDialog!!.setMessage("Loading...")
        pDialog!!.show()
       /* val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            hidePDialog()
            try {
                Title.setText(response.getString("Title"))
                Released.setText(response.getString("Released"))
                Genre.setText("Genre: " + response.getString("Genre"))
                Director.setText("Director: " + response.getString("Director"))
                Writer.setText("Writer: " + response.getString("Writer"))
                Actor.setText("Cast: " + response.getString("Actors"))
                Plot.setText("""
    Plot:
    ${response.getString("Plot")}
    """.trimIndent())
                Rating.setText(response.getString("imdbRating") + "/10")
                if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader()
                val thumbNail = findViewById<View>(R.id.imageViewPoster) as NetworkImageView
                thumbNail.setImageUrl(response.getString("Poster"), imageLoader)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }) { error ->
            showError(error)
            hidePDialog()
        }*/
        //AppController.getInstance().addToRequestQueue(jsonObjectRequest)
    }

    /*private fun showError(error: VolleyError) {
        Toast.makeText(this, "I'll handle this Error if you hire me!\n\n$error", Toast.LENGTH_LONG).show()
    }*/

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