package com.capibara.chagokchago.model.repository


import com.capibara.chagokchago.model.Location
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationSearcher @Inject constructor(private val locationDao: LocationDao) {

    suspend fun search(keyword: String): List<Location> {
        val locationEntities = locationDao.searchByCategory(keyword)
        return locationEntities.map {
            Location(
                place = it.placeName,
                address = it.addressName,
                category = it.categoryGroupName,
                latitude = it.latitude,
                longitude = it.longitude
            )
        }
    }
}