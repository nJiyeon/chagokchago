package com.capibara.chagokchago.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL1 = "http://192.168.176.91:8080/"  // 포트 번호와 IP 주소는 정확하게 기재
    private const val BASE_URL2 = "https://b700-2001-2d8-6a4d-b606-3ceb-405b-838b-13e7.ngrok-free.app"
    // Retrofit 인스턴스 생성
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL2)  // Base URL은 '/'로 끝나야 합니다
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)  // 연결 타임아웃 설정
                    .readTimeout(30, TimeUnit.SECONDS)     // 읽기 타임아웃 설정
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())  // Gson 변환기 추가
            .build()
            .create(ApiService::class.java)
    }
}