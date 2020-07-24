package com.yrc.pos.core

import android.app.Application
import com.yrc.pos.core.koin.getListOfModules
import com.yrc.pos.core.session.Session
import com.yrc.pos.core.session.User
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class YrcApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@YrcApplication)
            modules(getListOfModules())
        }
        User.initialize(applicationContext)
        Session.initialize(applicationContext)
    }
}