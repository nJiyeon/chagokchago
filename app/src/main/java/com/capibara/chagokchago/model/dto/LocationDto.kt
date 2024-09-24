package com.capibara.chagokchago.model.dto

data class LocationDto(
    val place: String,
    val address: String,
    val category: String,
    val latitude: Double,
    val longitude: Double
)