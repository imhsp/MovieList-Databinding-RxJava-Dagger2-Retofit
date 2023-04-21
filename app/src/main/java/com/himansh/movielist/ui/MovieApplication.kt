package com.himansh.movielist.ui

import android.app.Application
import com.himansh.movielist.di.ApplicationComponent
import com.himansh.movielist.di.DaggerApplicationComponent

class MovieApplication: Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder().build()
    }
}