package com.sterlingng.paylite.root

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.sterlingng.paylite.di.component.ApplicationComponent
import com.sterlingng.paylite.di.component.DaggerApplicationComponent
import com.sterlingng.paylite.di.module.ApplicationModule
import com.sterlingng.paylite.utils.Log
import io.realm.Realm

class MvpApp : Application() {

    // Needed to replace the component with a test specific one
    var component: ApplicationComponent? = null

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
        component!!.inject(this)

        Realm.init(this)
        Logger.addLogAdapter(AndroidLogAdapter())

        //App logger
        Log.init()
    }
}
