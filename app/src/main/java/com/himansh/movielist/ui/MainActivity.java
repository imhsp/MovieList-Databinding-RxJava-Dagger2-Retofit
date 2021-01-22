package com.himansh.movielist.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.himansh.movielist.data.rest.AppController;
import com.himansh.movielist.data.model.MovieObject;
import com.himansh.movielist.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{


    SearchView mSearchView;

    private ProgressDialog pDialog;
    private ArrayList<MovieObject> movieList = new ArrayList<MovieObject>();
    private ListView listView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchView=(SearchView) findViewById(R.id.movie_search);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Search Movie Here");
        mSearchView.setQuery("Home", true);


        listView = (ListView) findViewById(R.id.movie_list);
        adapter = new CustomAdapter(this, movieList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                launchMovieInfo(movieList.get(position).getImdb());
            }
        });

        listView.setTextFilterEnabled(true);

    }

    private void searchMovie(String query){

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        String url = "http://www.omdbapi.com/?s="+query+"&apikey=94a221d";

        movieList.clear();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        hidePDialog();
                        JSONArray resultArray = null;
                        try {
                            resultArray = response.getJSONArray("Search");

                            for (int j = 0; j < resultArray.length(); j++) {



                                JSONObject obj = resultArray.getJSONObject(j);
                                MovieObject movie = new MovieObject(obj.getString("Title"), obj.getString("Year"),obj.getString("imdbID"),obj.getString("Type"),obj.getString("Poster"));
                                movieList.add(movie);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.notifyDataSetChanged();


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
        Toast.makeText(this,"I'll handle this Error if you hire me!\n\n" + error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {

        searchMovie(query);

        return true;
    }


    private void launchMovieInfo(String imdb) {
        Intent intent = new Intent(this, MovieInfo.class);
        intent.putExtra("imdbID", imdb);
        startActivity(intent);
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
