package com.capibara.chagokchago.viewmodel

import androidx.lifecycle.*
import com.capibara.chagokchago.model.Location
import com.capibara.chagokchago.model.api.KakaoLocalApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val api: KakaoLocalApi
) : ViewModel() {
    private val _locations = MutableLiveData<List<Location>>()
    val locations: LiveData<List<Location>>
        get() = _locations

    fun searchLocationData(keyword: String) {
        viewModelScope.launch {
            try {
                val response = api.searchKeyword("KakaoAK ${com.capibara.chagokchago.BuildConfig.KAKAO_REST_API_KEY}", keyword)
                _locations.value = response.documents.map {
                    Location(
                        place = it.placeName,
                        address = it.addressName,
                        category = it.categoryGroupName,
                        latitude = it.latitude,
                        longitude = it.longitude
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}