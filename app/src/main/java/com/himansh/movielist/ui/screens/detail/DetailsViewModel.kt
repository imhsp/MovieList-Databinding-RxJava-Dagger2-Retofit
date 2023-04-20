package com.himansh.movielist.ui.screens.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himansh.movielist.data.remote.RepoService
import com.himansh.movielist.data.remote.RetrofitClientInstance
import com.himansh.movielist.domain.GetMovieDetailsUseCase
import com.himansh.movielist.domain.mappers.ResultMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailsViewModel : ViewModel() {

    private val repoService: RepoService =
        RetrofitClientInstance.retrofitInstance.create(RepoService::class.java)

    private val _movieData: MutableLiveData<ResultMap> = MutableLiveData()
    val movieData: MutableLiveData<ResultMap>
        get() = _movieData

    fun getMovieInfo(movieID: String) {
        _movieData.value = ResultMap.Loading
        val getMovieDetailsUseCase = GetMovieDetailsUseCase(repoService)
        val movieListObservable = getMovieDetailsUseCase.execute(movieID)
        val x = movieListObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResults, this::handleError)
    }

    private fun handleResults(result: ResultMap) {
        _movieData.value = result as ResultMap.Success
    }

    private fun handleError(throwable: Throwable) {
        _movieData.value = ResultMap.Failure(throwable)
    }
}