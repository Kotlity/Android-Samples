package com.kotlity.network.di.modules

import com.kotlity.network.BuildConfig
import com.kotlity.network.di.annotations.HeaderRequestInterceptorQualifier
import com.kotlity.network.interceptors.HeaderInterceptor
import com.kotlity.network.interceptors.HeaderRequestInterceptor
import com.kotlity.network.interceptors.RequestInterceptor
import com.kotlity.network.model.api.FakeApi
import com.kotlity.network.model.repositories.FakeApiRepository
import com.kotlity.network.model.repositories.FakeRetrofitApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @HeaderRequestInterceptorQualifier
    fun provideRequestInterceptor(): RequestInterceptor = HeaderRequestInterceptor(headerName = "Content-type", headerValue = "application/json; charset=UTF-8")

    @Provides
    @Singleton
    fun provideHeaderInterceptor(
        @HeaderRequestInterceptorQualifier
        requestInterceptor: RequestInterceptor
    ): HeaderInterceptor = HeaderInterceptor(requestInterceptor = requestInterceptor)

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        return with(okHttpClient) {
            addInterceptor(headerInterceptor)
            addInterceptor(httpLoggingInterceptor)
            build()
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitApi(
        retrofit: Retrofit
    ): FakeApi {
        return retrofit.create(FakeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFakeApiRepository(
        fakeApi: FakeApi
    ): FakeApiRepository {
        return FakeRetrofitApiRepository(fakeApi = fakeApi)
    }

}