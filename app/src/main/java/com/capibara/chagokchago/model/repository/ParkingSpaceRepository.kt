package com.capibara.chagokchago.model.repository

import android.util.Log
import com.capibara.chagokchago.model.api.ApiService
import com.capibara.chagokchago.model.dto.ParkingSpaceDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ParkingSpaceRepository @Inject constructor(
    private val apiService: ApiService
) {
    // 주차장 데이터를 API를 통해 가져옴
    suspend fun fetchParkingSpacesFromApi(): List<ParkingSpaceDto> {
        return apiService.getParkingSpaces()
    }

    // 주차장 데이터를 가져오는 함수
    suspend fun fetchParkingSpaces(): List<ParkingSpaceDto>? {
        return try {
            // API 호출을 통해 주차장 데이터 가져오기
            val parkingSpaces = fetchParkingSpacesFromApi()
            parkingSpaces  // 성공적으로 데이터를 가져오면 반환
        } catch (e: Exception) {
            Log.e("error", "정보를 가져오지 못했습니다")
            null
        }
    }
}

