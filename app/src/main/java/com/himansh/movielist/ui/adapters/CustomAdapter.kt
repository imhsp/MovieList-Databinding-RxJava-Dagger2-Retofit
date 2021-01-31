package com.himansh.movielist.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.himansh.movielist.data.model.MovieObject
import com.himansh.movielist.databinding.CustomListItemBinding
import com.squareup.picasso.Picasso


class CustomAdapter(private val context: Context, private var movieList: ArrayList<MovieObject>, var onItemClick: (position: Int) -> Unit) : RecyclerView.Adapter<CustomAdapter.MovieObjectViewHolder>() {

    private lateinit var binding: CustomListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieObjectViewHolder {
        binding = CustomListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MovieObjectViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: MovieObjectViewHolder, position: Int) {
        holder.bind(movieList[position], position)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    class MovieObjectViewHolder(private val binding: CustomListItemBinding, var onItemClick: (position: Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movieObject: MovieObject, position: Int) {
            Picasso.get()
                    .load(movieObject.Poster)
                    .into(binding.moviePoster)
            binding.movie = movieObject
            itemView.setOnClickListener { onItemClick(position) }
        }

    }
}