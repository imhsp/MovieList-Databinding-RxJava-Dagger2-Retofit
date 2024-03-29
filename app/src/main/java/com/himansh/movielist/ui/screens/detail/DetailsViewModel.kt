package com.himansh.movielist.ui.screens.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himansh.movielist.domain.GetMovieDetailsUseCase
import com.himansh.movielist.domain.mappers.ResultMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DetailsViewModel(private val getMovieDetailsUseCase: GetMovieDetailsUseCase) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _movieData: MutableLiveData<ResultMap> = MutableLiveData()
    val movieData: LiveData<ResultMap>
        get() = _movieData

    fun getMovieInfo(movieID: String) {
        _movieData.value = ResultMap.Loading
        val movieListObservable = getMovieDetailsUseCase.execute(movieID)
        disposables.add(
            movieListObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError)
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun handleResults(result: ResultMap) {
        when (result) {
            is ResultMap.Success -> {
                _movieData.value = result
            }

            is ResultMap.Failure -> {
                handleError(result.throwable)
            }
        }
    }

    private fun handleError(throwable: Throwable) {
        _movieData.value = ResultMap.Failure(throwable)
    }
}