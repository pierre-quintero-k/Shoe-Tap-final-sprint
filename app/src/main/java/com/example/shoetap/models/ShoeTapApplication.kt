package com.example.shoetap.models

import android.app.Application

class ShoeTapApplication : Application() {
    companion object{
         lateinit var prefs:Prefs
    }
    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}