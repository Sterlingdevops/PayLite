package com.sterlingng.paylite.di.module


import android.app.Application
import android.content.Context
import com.sterlingng.paylite.data.manager.AppDataManager
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.di.annotations.ApplicationContext
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.rx.RxBus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by rtukpe on 13/03/2018.
 */

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    internal fun provideEventBus(): EventBus = RxBus()

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager = appDataManager
}
