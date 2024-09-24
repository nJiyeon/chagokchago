package com.capibara.chagokchago.model.repository


import com.capibara.chagokchago.model.LocationDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationSearcher @Inject constructor(private val locationDao: LocationDao) {

    suspend fun search(keyword: String): List<LocationDto> {
        val locationEntities = locationDao.searchByCategory(keyword)
        return locationEntities.map {
            LocationDto(
                place = it.placeName,
                address = it.addressName,
                category = it.categoryGroupName,
                latitude = it.latitude,
                longitude = it.longitude
            )
        }
    }
}