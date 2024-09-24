package com.capibara.chagokchago.model

data class LocationDto(
    val place: String,
    val address: String,
    val category: String,
    val latitude: Double,
    val longitude: Double
)