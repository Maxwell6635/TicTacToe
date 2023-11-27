package com.jackson.tictactoe

import android.app.Application
import com.jackson.tictactoe.utils.appModule
import com.jackson.tictactoe.utils.utilModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MainApplication)
            // Load modules
            modules(utilModule, appModule)
        }

    }
}