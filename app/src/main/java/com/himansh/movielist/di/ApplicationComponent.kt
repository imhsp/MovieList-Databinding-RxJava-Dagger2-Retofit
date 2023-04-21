package com.himansh.movielist.di

import com.himansh.movielist.ui.screens.detail.MovieInfo
import com.himansh.movielist.ui.screens.search.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(movieInfo: MovieInfo)
}