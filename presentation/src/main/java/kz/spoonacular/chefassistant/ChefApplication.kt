package kz.spoonacular.chefassistant

import android.app.Application
import kz.spoonacular.chefassistant.di.useCaseModule
import kz.spoonacular.chefassistant.di.viewModelModule
import kz.spoonacular.data.di.dataBaseModule
import kz.spoonacular.data.di.mapperModule
import kz.spoonacular.data.di.repositoryModule
import kz.spoonacular.data.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Created by Sarsenov Yerlan on 31.01.2021.
 */
class ChefApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(applicationContext)
            modules(
                listOf(
                    mapperModule,
                    dataBaseModule,
                    retrofitModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }

}
