package com.capibara.chagokchago.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capibara.chagokchago.model.dto.ParkingSpaceDto
import com.capibara.chagokchago.model.repository.ParkingSpaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParkingSpaceViewModel @Inject constructor(
    private val parkingSpaceRepository: ParkingSpaceRepository
) : ViewModel() {

    private val _parkingSpaces = MutableLiveData<List<ParkingSpaceDto>>()
    val parkingSpaces: LiveData<List<ParkingSpaceDto>> get() = _parkingSpaces

    fun loadParkingSpaces() {
        viewModelScope.launch {
            _parkingSpaces.value = parkingSpaceRepository.fetchParkingSpaces()
        }
    }
}
