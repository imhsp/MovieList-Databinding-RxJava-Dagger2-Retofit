package com.himansh.movielist.ui.screens.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.himansh.movielist.data.remote.RepoService
import com.himansh.movielist.data.remote.RetrofitClientInstance
import com.himansh.movielist.domain.GetMovieListUseCase
import com.himansh.movielist.domain.mappers.ResultMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel : ViewModel() {
    private val repoService: RepoService =
        RetrofitClientInstance.retrofitInstance.create(RepoService::class.java)

    private val _searchData = MutableLiveData<ResultMap>()
    val searchData: MutableLiveData<ResultMap>
        get() = _searchData

    fun search(query: String) {
        _searchData.value = ResultMap.Loading
        val getMovieListUseCase = GetMovieListUseCase(repoService)
        val movieListObservable = getMovieListUseCase.execute(query)
        val x = movieListObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResults, this::handleError)
    }

    private fun handleResults(result: ResultMap) {
        _searchData.value = result as ResultMap.Success
    }

    private fun handleError(t: Throwable) {
        _searchData.value = ResultMap.Failure(t)
    }
}