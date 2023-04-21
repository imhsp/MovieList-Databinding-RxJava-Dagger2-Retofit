package com.himansh.movielist.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himansh.movielist.domain.GetMovieDetailsUseCase
import javax.inject.Inject

class DetailsViewModelFactory @Inject constructor(private val getMovieDetailsUseCase: GetMovieDetailsUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(getMovieDetailsUseCase) as T
    }
}