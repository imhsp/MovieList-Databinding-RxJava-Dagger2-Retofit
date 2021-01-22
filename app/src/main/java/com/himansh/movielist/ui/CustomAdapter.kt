package com.himansh.movielist.ui

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.himansh.movielist.R
import com.himansh.movielist.data.model.MovieObject

class CustomAdapter(private val activity: Activity, var movieList: List<MovieObject>) : BaseAdapter() {
    private var inflater: LayoutInflater? = null
    //var imageLoader: ImageLoader? = AppController.getInstance().getImageLoader()
    override fun getCount(): Int {
        return movieList.size
    }

    override fun getItem(position: Int): Any {
        return movieList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        if (inflater == null) inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        //if (imageLoader == null) imageLoader = AppController.getInstance().getImageLoader()

        val title = convertView.findViewById<View>(R.id.movieName) as TextView
        val type = convertView.findViewById<View>(R.id.movieType) as TextView
        val year = convertView.findViewById<View>(R.id.movieYear) as TextView

        // getting movie data for the row
        val (title1, year1, _, type1, image) = movieList[position]

        // title
        title.text = title1

        // genre
        type.text = type1

        // release year
        year.text = year1
        return convertView
    }
}