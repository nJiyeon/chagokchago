package com.capibara.chagokchago.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capibara.chagokchago.model.repository.KeywordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class KeywordViewModel @Inject constructor(
    private val repository: KeywordRepository
) : ViewModel() {

    private val _keywords = MutableLiveData<List<String>>()
    val keywords: LiveData<List<String>> get() = _keywords

    init {
        loadKeywords()
    }

    private fun loadKeywords() {
        viewModelScope.launch {
            _keywords.value = repository.read()
        }
    }

    fun saveKeyword(keyword: String) {
        viewModelScope.launch {
            repository.update(keyword)
            loadKeywords()
        }
    }

    fun deleteKeyword(keyword: String) {
        viewModelScope.launch {
            repository.delete(keyword)
            loadKeywords()
        }
    }
}