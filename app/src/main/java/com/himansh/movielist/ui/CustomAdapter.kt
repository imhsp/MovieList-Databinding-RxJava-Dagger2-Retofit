package com.himansh.movielist.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.himansh.movielist.R
import com.himansh.movielist.data.model.MovieObject
import com.squareup.picasso.Picasso


class CustomAdapter(private val context: Context, private var movieList: ArrayList<MovieObject>, var onItemClick: (position: Int) -> Unit) : RecyclerView.Adapter<CustomAdapter.MovieObjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieObjectViewHolder {
        val inflatedView = LayoutInflater.from(context).inflate(R.layout.custom_list_item, parent, false)
        return MovieObjectViewHolder(inflatedView, onItemClick)
    }

    override fun onBindViewHolder(holder: MovieObjectViewHolder, position: Int) {
        holder.bind(movieList[position], position)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class MovieObjectViewHolder(itemView: View, var onItemClick: (position: Int) -> Unit) : RecyclerView.ViewHolder(itemView) {

        private val moviePoster: ImageView = itemView.findViewById(R.id.moviePoster)
        private val movieTitle: TextView = itemView.findViewById(R.id.movieName)
        private val movieType: TextView = itemView.findViewById(R.id.movieType)
        private val movieYear: TextView = itemView.findViewById(R.id.movieYear)

        fun bind(movieObject: MovieObject, position: Int) {

            Picasso.get()
                    .load(movieObject.Poster)
                    .into(moviePoster)

            movieTitle.text = movieObject.Title
            movieType.text = movieObject.Type
            movieYear.text = movieObject.Year

            itemView.setOnClickListener { onItemClick(position) }
        }

    }
}