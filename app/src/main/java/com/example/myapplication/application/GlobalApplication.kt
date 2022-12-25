package com.example.myapplication.application

import android.app.Application
import com.example.myapplication.BuildConfig
import com.example.myapplication.timber.TimberCustomTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class GlobalApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(TimberCustomTree())
    }
}