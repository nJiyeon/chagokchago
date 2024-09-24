package com.capibara.chagokchago.view

import com.capibara.chagokchago.model.dto.LocationDto

interface OnSearchItemClickListener {
    fun onSearchItemClick(location: LocationDto)
}