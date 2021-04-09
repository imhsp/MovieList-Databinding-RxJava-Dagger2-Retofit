package com.himansh.movielist.domain.mappers

import com.himansh.movielist.data.model.MovieObject

open class ResultMap {
    object Loading : ResultMap()
    data class Success(val movies: List<MovieObject>) : ResultMap()
    data class Failure(val throwable: Throwable) : ResultMap()
}