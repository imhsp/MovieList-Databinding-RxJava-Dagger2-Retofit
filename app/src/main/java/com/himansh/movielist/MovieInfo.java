package com.himansh.movielist;

import android.app.ProgressDialog;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieInfo extends AppCompatActivity {

    TextView Title, Released, Rating, Genre, Director, Writer, Actor, Plot;
    NetworkImageView Poster;
    private ProgressDialog pDialog;
    private String url = "http://www.omdbapi.com/?i=tt0099785&apikey=94a221d";
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        Poster = findViewById(R.id.imageViewPoster);

        url = "http://www.omdbapi.com/?i=" + getIntent().getStringExtra("imdbID") + "&apikey=94a221d";

        Title = findViewById(R.id.textViewTitle);
        Released = findViewById(R.id.textViewReleased);
        Rating = findViewById(R.id.textViewIMDBRatings);
        Genre = findViewById(R.id.textViewGenre);
        Director = findViewById(R.id.textViewDirector);
        Writer = findViewById(R.id.textViewWriter);
        Actor = findViewById(R.id.textViewActors);
        Plot = findViewById(R.id.textViewPlot);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        hidePDialog();
                        try {

                            Title.setText(response.getString("Title"));
                            Released.setText(response.getString("Released"));
                            Genre.setText("Genre: " + response.getString("Genre"));
                            Director.setText("Director: " + response.getString("Director"));
                            Writer.setText("Writer: " + response.getString("Writer"));
                            Actor.setText("Cast: " + response.getString("Actors"));
                            Plot.setText("Plot:\n" + response.getString("Plot"));
                            Rating.setText(response.getString("imdbRating") + "/10");

                            if (imageLoader == null)
                                imageLoader = AppController.getInstance().getImageLoader();
                            NetworkImageView thumbNail = (NetworkImageView) findViewById(R.id.imageViewPoster);

                            thumbNail.setImageUrl(response.getString("Poster"), imageLoader);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showError(error);
                        hidePDialog();

                    }
                });


        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    private void showError(VolleyError error) {
        Toast.makeText(this, "I'll handle this Error if you hire me!\n\n" + error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
