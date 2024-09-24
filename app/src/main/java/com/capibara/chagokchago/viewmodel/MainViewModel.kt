package com.capibara.chagokchago.viewmodel


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capibara.chagokchago.model.dto.LocationDto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val sharedPreferences: SharedPreferences = application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val _lastMarkerPosition = MutableLiveData<LocationDto?>()
    val lastMarkerPosition: LiveData<LocationDto?> get() = _lastMarkerPosition

    init {
        loadLastMarkerPosition()
    }

    fun saveLastMarkerPosition(location: LocationDto) {
        with(sharedPreferences.edit()) {
            putFloat(PREF_LATITUDE, location.latitude.toFloat())
            putFloat(PREF_LONGITUDE, location.longitude.toFloat())
            putString(PREF_PLACE_NAME, location.place)
            putString(PREF_ROAD_ADDRESS_NAME, location.address)
            apply()
        }
        _lastMarkerPosition.value = location
    }

    private fun loadLastMarkerPosition() {
        if (sharedPreferences.contains(PREF_LATITUDE) && sharedPreferences.contains(PREF_LONGITUDE)) {
            val latitude = sharedPreferences.getFloat(PREF_LATITUDE, 0.0f).toDouble()
            val longitude = sharedPreferences.getFloat(PREF_LONGITUDE, 0.0f).toDouble()
            val placeName = sharedPreferences.getString(PREF_PLACE_NAME, "") ?: ""
            val roadAddressName = sharedPreferences.getString(PREF_ROAD_ADDRESS_NAME, "") ?: ""

            _lastMarkerPosition.value = if (placeName.isNotEmpty() && roadAddressName.isNotEmpty()) {
                LocationDto(place = placeName, address = roadAddressName, category = "", latitude = latitude, longitude = longitude)
            } else {
                null
            }
        } else {
            _lastMarkerPosition.value = null
        }
    }

    companion object {
        private const val PREFS_NAME = "LastMarkerPrefs"
        private const val PREF_LATITUDE = "lastLatitude"
        private const val PREF_LONGITUDE = "lastLongitude"
        private const val PREF_PLACE_NAME = "lastPlaceName"
        private const val PREF_ROAD_ADDRESS_NAME = "lastRoadAddressName"
    }
}