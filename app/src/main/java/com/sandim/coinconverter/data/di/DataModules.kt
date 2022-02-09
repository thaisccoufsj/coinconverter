package com.sandim.coinconverter.data.di

import android.util.Log
import com.google.gson.GsonBuilder
import com.sandim.coinconverter.data.database.AppDatabase
import com.sandim.coinconverter.data.repository.CoinRepository
import com.sandim.coinconverter.data.repository.CoinRepositoryImplementer
import com.sandim.coinconverter.data.services.AwesomeService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModules {

    private const val HTTP_TAG = "OhHttp"

    fun load() {
        loadKoinModules(networkModule() + databaseModulues() + repositoryModule())
    }

    private fun networkModule(): Module {
        return module {
            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.e(HTTP_TAG, ": $it")
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }

            single {
                createService<AwesomeService>(get(), get())
            }
        }
    }

    private fun repositoryModule(): Module {
        return module {
            single<CoinRepository> {
                CoinRepositoryImplementer(get(), get())
            }
        }
    }

    private fun databaseModulues(): Module {
        return module{
            single {
                AppDatabase.getInstance(androidApplication())
            }
        }
    }

    private inline fun <reified T> createService(client: OkHttpClient, factory: GsonConverterFactory): T {
        return Retrofit.Builder()
            .baseUrl("https://economia.awesomeapi.com.br")
            .client(client)
            .addConverterFactory(factory)
            .build()
            .create(T::class.java)
    }
}