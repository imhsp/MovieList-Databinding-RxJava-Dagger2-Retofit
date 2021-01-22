package com.himansh.movielist.data.rest

import com.himansh.movielist.data.model.MovieObject
import retrofit2.Call
import retrofit2.http.GET


interface RepoService {

    @get:GET("/photos")
    val allPhotos: Call<List<MovieObject?>?>?

}