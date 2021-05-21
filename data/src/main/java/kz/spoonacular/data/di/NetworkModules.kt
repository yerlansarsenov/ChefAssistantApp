package kz.spoonacular.data.di

import com.google.gson.Gson
import kz.spoonacular.data.BASE_URL
import kz.spoonacular.data.api.ApiKeyInterceptor
import kz.spoonacular.data.api.IngredientsApi
import kz.spoonacular.data.api.RecipeApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Sarsenov Yerlan on 30.01.2021.
 */

val retrofitModule = module {
    fun client(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient().newBuilder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        builder.addInterceptor(ApiKeyInterceptor())
        return builder.build()
    }

    single { client() }

    fun retrofit(client: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

    single { retrofit(get()) }

    fun recipeApi(retrofit: Retrofit) : RecipeApi =
        retrofit.create(RecipeApi::class.java)

    fun ingredientsApi(retrofit: Retrofit) : IngredientsApi =
        retrofit.create(IngredientsApi::class.java)

    single { recipeApi(get()) }

    single { ingredientsApi(get()) }
}
