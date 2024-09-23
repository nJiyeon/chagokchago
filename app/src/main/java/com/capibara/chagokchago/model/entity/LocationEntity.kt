package com.capibara.chagokchago.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "place_name") val placeName: String,
    @ColumnInfo(name = "address_name") val addressName: String,
    @ColumnInfo(name = "category_group_name") val categoryGroupName: String,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double
)