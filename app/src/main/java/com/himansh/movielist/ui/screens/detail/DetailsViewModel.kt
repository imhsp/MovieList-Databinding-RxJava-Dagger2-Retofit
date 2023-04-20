package com.himansh.movielist.ui.screens.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himansh.movielist.data.remote.RepoService
import com.himansh.movielist.domain.GetMovieDetailsUseCase
import com.himansh.movielist.domain.mappers.ResultMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsViewModel @Inject constructor(@Inject private var repoService: RepoService) : ViewModel() {
    private val _movieData: MutableLiveData<ResultMap> = MutableLiveData()
    val movieData: MutableLiveData<ResultMap>
        get() = _movieData

    fun getMovieInfo(movieID: String) {
        _movieData.value = ResultMap.Loading
        val getMovieDetailsUseCase = GetMovieDetailsUseCase(repoService)
        val movieListObservable = getMovieDetailsUseCase.execute(movieID, API_KEY)
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


    companion object {
        private const val API_KEY = "2ca845bf"
    }
}