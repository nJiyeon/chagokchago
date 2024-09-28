package com.capibara.chagokchago.model.di

import com.capibara.chagokchago.model.api.ApiService
import com.capibara.chagokchago.model.api.KakaoLocalApi
import com.capibara.chagokchago.model.repository.ParkingSpaceRepository
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
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        // HttpLoggingInterceptor 설정
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        // OkHttpClient에 로깅 인터셉터 추가
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com")
            .client(client)  // OkHttpClient 추가
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideKakaoLocalApi(retrofit: Retrofit): KakaoLocalApi {
        return retrofit.create(KakaoLocalApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideParkingSpaceRepository(
        apiService: ApiService
    ): ParkingSpaceRepository {
        return ParkingSpaceRepository(apiService)
    }
}

