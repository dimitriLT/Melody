package com.dimitri.le.torriellec.melody

import android.app.Application
import com.dimitri.le.torriellec.melody.domain.di.koinDomainModules
import com.dimitri.le.torriellec.melody.repository.di.koinDataSourceModules
import com.dimitri.le.torriellec.melody.ui.di.koinUiModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {

        // Koin
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            androidFileProperties()
            modules(koinDataSourceModules)
            modules(koinDomainModules)
            modules(koinUiModules)
        }

        super.onCreate()
    }
}