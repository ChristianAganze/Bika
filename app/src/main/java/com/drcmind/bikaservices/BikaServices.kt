package com.drcmind.bikaservices

import android.app.Application
import com.drcmind.bikaservices.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class BikaServices :Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@BikaServices)
            androidLogger()
            modules(appModule)
        }
    }
}