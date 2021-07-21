package com.expleo.task.Activity

import android.app.Application
import com.singhajit.sherlock.core.Sherlock

class Crash: Application() {

    override fun onCreate() {
        super.onCreate()
        Sherlock.init(this)
    }
}