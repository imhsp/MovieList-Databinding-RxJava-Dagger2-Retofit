package com.himansh.movielist.data.model

data class SearchObject(
    val Search: ArrayList<MovieObject>,
    val totalResults: Int,
    val Response: Boolean,
    val Error: String
)
