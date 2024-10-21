package com.example.productdisplayapp

import android.app.Application
import com.airbnb.mvrx.Mavericks
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProductDisplayApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)
    }
}