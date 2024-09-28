package com.capibara.chagokchago.model.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parking_spaces")  // 테이블 이름 설정
data class ParkingSpaceDto(
    val ownerName: String,
    val parkingName: String,
    val maxCar: Int,
    val useCar: Int,
    val price: Int,
    val explain: String,
    val isAvailable: Boolean,
    @PrimaryKey val parkingSpaceId: Int,
    val latitude: Double,
    val longitude: Double
){
    // ParkingSpaceDto를 LocationDto로 변환하는 메서드
    // 좌표로 label을 찍어야하므로
    fun toLocationDto(): LocationDto {
        return LocationDto(
            place = this.parkingName,
            address = this.explain, // 설명을 주소로 사용
            category = "주차장", // 카테고리 고정
            latitude = this.latitude,
            longitude = this.longitude
        )
    }
}
