package com.himansh.movielist.ui.screens.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himansh.movielist.domain.GetMovieListUseCase
import com.himansh.movielist.domain.mappers.ResultMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel(private val getMovieListUseCase: GetMovieListUseCase) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _searchData = MutableLiveData<ResultMap>()
    val searchData: LiveData<ResultMap>
        get() = _searchData

    fun search(query: String) {
        _searchData.value = ResultMap.Loading
        val movieListObservable = getMovieListUseCase.execute(query)
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
        _searchData.value = result as ResultMap.Success
    }

    private fun handleError(t: Throwable) {
        _searchData.value = ResultMap.Failure(t)
    }
}