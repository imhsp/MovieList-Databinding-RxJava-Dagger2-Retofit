package com.himansh.movielist.domain

import com.himansh.movielist.data.remote.RepoService
import com.himansh.movielist.domain.mappers.ResultMap
import io.reactivex.Observable
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(private val repoService: RepoService) {

    fun execute(searchQuery: String): Observable<ResultMap> {
        return repoService.getMoviesList(searchQuery)
            .map {
                if (it.Response) {
                    ResultMap.Success(it.Search)
                } else {
                    ResultMap.Failure(Exception(it.Error))
                }
            }
            .onErrorReturn { ResultMap.Failure(it) }
    }
}