package com.example.quizn10.di

import com.example.quizn10.BuildConfig
import com.example.quizn10.data.common.HandleResponse
import com.example.quizn10.data.repository.AccountRepositoryImpl
import com.example.quizn10.data.repository.ValuteRepositoryImpl
import com.example.quizn10.data.service.AccountsApiService
import com.example.quizn10.data.service.ValuteApiService
import com.example.quizn10.domain.repository.AccountsRepository
import com.example.quizn10.domain.repository.ValuteRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideHandleResponse(): HandleResponse = HandleResponse()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            client.addInterceptor(httpLoggingInterceptor)
        }
        return client.build()
    }

    @Provides
    @Singleton
    fun provideClothesRetrofitClient(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.MOCKY_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideAccountsApiService(retrofit: Retrofit): AccountsApiService {
        return retrofit.create(AccountsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideValuteApiService(retrofit: Retrofit): ValuteApiService {
        return retrofit.create(ValuteApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAccountsRepository(
        accountsApiService: AccountsApiService,
        handleResponse: HandleResponse
    ): AccountsRepository =
        AccountRepositoryImpl(
            accountsApiService = accountsApiService,
            handleResponse = handleResponse,
        )

    @Provides
    @Singleton
    fun provideValuteRepository(
        valuteApiService: ValuteApiService,
        handleResponse: HandleResponse
    ): ValuteRepository =
        ValuteRepositoryImpl(
            valuteApiService = valuteApiService,
            handleResponse = handleResponse,
        )

    @Provides
    @Singleton
    fun provideIODispatcher() = Dispatchers.IO
}