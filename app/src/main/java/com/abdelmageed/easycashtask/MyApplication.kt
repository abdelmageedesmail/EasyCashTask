package com.abdelmageed.easycashtask

import android.app.Application
import com.abdelmageed.islamicapp.di.AppModule
import com.abdelmageed.islamicapp.di.DaggerAppComponents

class MyApplication : Application() {

    val appComponent = DaggerAppComponents.builder().appModule(AppModule(this)).build()

}