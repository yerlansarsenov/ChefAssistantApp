package kz.spoonacular.data.di

import android.content.Context
import androidx.room.Room
import kz.spoonacular.data.database.ChefAppDataBase
import kz.spoonacular.data.database.RecipesDao
import org.koin.dsl.module

/**
 * Created by Sarsenov Yerlan on 18.02.2021.
 */

private const val CHEF_DB = "kz.spoonacular.data.di.CHEF_DB"

val dataBaseModule = module {
    fun getDataBase(context: Context): ChefAppDataBase {
        return Room.databaseBuilder(
            context,
            ChefAppDataBase::class.java, CHEF_DB
        ).build()
    }

    single { getDataBase(get()) }

    fun getRecipesDao(dataBase: ChefAppDataBase): RecipesDao {
        return dataBase.recipesDao()
    }

    factory { getRecipesDao(get()) }
}