package com.capibara.chagokchago.model.api

import com.capibara.chagokchago.model.dto.ParkingSpaceDto
import retrofit2.Call
import retrofit2.http.POST
import com.capibara.chagokchago.model.dto.RegisterRequestDto
import retrofit2.http.Body
import retrofit2.http.GET


interface ApiService {
    @POST("/auth/register")  // 엔드포인트 수정
    fun registerUser(@Body registerRequest: RegisterRequestDto): Call<Void>

    @GET("/parking/available/parkingspace") // 주차장 정보 조회 API
    suspend fun getParkingSpaces(): List<ParkingSpaceDto> // 주차장 정보 리스트 반환
}
