package com.yrc.pos.core.prefs

import android.content.Context
import org.koin.dsl.module

val prefModule = module {
    fun getPrefManager(context: Context): YrcPrefManager {
        return YrcPrefManager(context)
    }

    single { getPrefManager(get()) }
}