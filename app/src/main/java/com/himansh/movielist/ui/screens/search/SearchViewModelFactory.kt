package com.himansh.movielist.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himansh.movielist.domain.GetMovieListUseCase
import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(private val getMovieListUseCase: GetMovieListUseCase): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(getMovieListUseCase) as T
    }
}