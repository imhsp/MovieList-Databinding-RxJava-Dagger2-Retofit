package com.himansh.movielist.ui;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.himansh.movielist.data.rest.AppController;
import com.himansh.movielist.data.model.MovieObject;
import com.himansh.movielist.R;

public class CustomAdapter extends BaseAdapter {

    ArrayList<MovieObject> movieList;
    private Activity activity;
    private LayoutInflater inflater;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomAdapter(Activity activity, ArrayList<MovieObject> items) {
        this.activity = activity;
        this.movieList = items;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.custom_list_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.movieName);
        TextView type = (TextView) convertView.findViewById(R.id.movieType);
        TextView year = (TextView) convertView.findViewById(R.id.movieYear);

        // getting movie data for the row
        MovieObject m = movieList.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getImage(), imageLoader);

        // title
        title.setText(m.getTitle());


        // genre

        type.setText(m.getType());

        // release year
        year.setText(m.getYear());

        return convertView;



    }
}
