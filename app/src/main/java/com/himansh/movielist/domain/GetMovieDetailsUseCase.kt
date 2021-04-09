package com.himansh.movielist.domain

import com.himansh.movielist.data.remote.RepoService
import com.himansh.movielist.domain.mappers.ResultMap
import io.reactivex.Observable
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repoService: RepoService) {
    fun execute(movieId: String, apiKey: String): Observable<ResultMap> {
        return repoService.getMovieDetail(movieId, apiKey)
                .map { ResultMap.Success(listOf(it)) as ResultMap }
                .onErrorReturn { ResultMap.Failure(it) }
    }
}