package com.example.remote.di

import com.example.remote.BuildConfig
import com.example.remote.retrofit.ApiService
import com.example.remote.retrofit.ServerIp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * network 통신 곤련 설정 처리를 Hilt를 사용하여, 의존성 주입 한다.
 **/

@Module
@InstallIn(SingletonComponent::class)//network 설정은 singleton으로  지정
object NetworkModule {

    //api service 제공하기
    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ):ApiService =
        retrofit.create(ApiService::class.java)


    //Retrofit 설정 적용
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(ServerIp.BaseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    //okhttp client 의존성 주입
    @Provides
    @Singleton
    fun provideOkhttpClient(
        networkInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(networkInterceptor)
        .addInterceptor(httpLogInterceptor())
        .build()


    //network interceptor 의존성 주입
    @Provides
    @Singleton
    fun provideNetworkInterceptor(): Interceptor =
        Interceptor { chain ->
            val requestWithHeader =
                chain.request().newBuilder().addHeader("X-Api-Key", BuildConfig.API_KEY)
            chain.proceed(requestWithHeader.build())
        }


    //http 통신 로그 intercept해서 보여줌. -> 디버깅용
    private fun httpLogInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        return loggingInterceptor.setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {//디버그가 아니면  로그가 안보이게 해준다.
                HttpLoggingInterceptor.Level.NONE
            }
        )
    }


}