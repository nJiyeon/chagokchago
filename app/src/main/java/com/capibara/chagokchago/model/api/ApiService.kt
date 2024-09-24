package com.capibara.chagokchago.model.api

import retrofit2.Call
import retrofit2.http.POST
import com.capibara.chagokchago.model.RegisterRequestDto
import retrofit2.http.Body


interface ApiService {
    @POST("/auth/register")  // 엔드포인트 수정
    fun registerUser(@Body registerRequest: RegisterRequestDto): Call<Void>

}
