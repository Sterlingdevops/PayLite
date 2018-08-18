package com.sterlingng.paylite.di.component


import android.app.Application
import android.content.Context
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.di.annotations.ApplicationContext
import com.sterlingng.paylite.di.module.ApplicationModule
import com.sterlingng.paylite.root.MvpApp
import com.sterlingng.paylite.rx.EventBus
import dagger.Component
import javax.inject.Singleton

/**
 * Created by rtukpe on 13/03/2018.
 */

@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {

    val dataManager: DataManager

    fun inject(app: MvpApp)

    val eventBus: EventBus

    @ApplicationContext
    fun context(): Context

    fun application(): Application
}
