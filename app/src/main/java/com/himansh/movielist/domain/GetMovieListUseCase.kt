package com.himansh.movielist.domain

import com.himansh.movielist.data.remote.RepoService
import com.himansh.movielist.domain.mappers.ResultMap
import io.reactivex.Observable
import javax.inject.Inject

class GetMovieListUseCase  @Inject constructor(private val repoService: RepoService) {

    fun execute(searchQuery: String): Observable<ResultMap> {
        return repoService.getMoviesList(searchQuery)
                .map { ResultMap.Success(it.Search) as ResultMap }
                .onErrorReturn { ResultMap.Failure(it) }
    }
}