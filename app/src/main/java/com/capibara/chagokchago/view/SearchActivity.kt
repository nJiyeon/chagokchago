package com.capibara.chagokchago.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.capibara.chagokchago.databinding.ActivitySearchBinding
import com.capibara.chagokchago.model.LocationDto
import com.capibara.chagokchago.OnKeywordItemClickListener
import com.capibara.chagokchago.OnSearchItemClickListener
import com.capibara.chagokchago.model.adapter.KeywordAdapter
import com.capibara.chagokchago.model.adapter.SearchAdapter
import com.capibara.chagokchago.viewmodel.KeywordViewModel
import com.capibara.chagokchago.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity(), OnSearchItemClickListener, OnKeywordItemClickListener {
    private lateinit var binding: ActivitySearchBinding

    // ViewModel을 Lazy하게 제공받기
    private val searchViewModel: SearchViewModel by viewModels()
    private val keywordViewModel: KeywordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 검색 결과 RecyclerView 설정
        var searchAdapter = SearchAdapter(this)
        binding.searchResultView.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        // 검색어 목록 RecyclerView 설정
        var keywordAdapter = KeywordAdapter(this)
        binding.keywordHistoryView.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = keywordAdapter
        }

        // 검색 입력 설정
        binding.searchTextInput.doAfterTextChanged {
            searchViewModel.searchLocationData(it.toString())
        }

        // 취소 버튼 클릭 이벤트 설정
        binding.deleteTextInput.setOnClickListener {
            binding.searchTextInput.text.clear()
        }

        // 검색어 목록 관찰
        keywordViewModel.keywords.observe(this) {
            keywordAdapter.submitList(it)
        }

        // 검색 결과 관찰하여 UI 업데이트
        searchViewModel.locations.observe(this) {
            searchAdapter.submitList(it)
            binding.searchResultView.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
            binding.emptyView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onSearchItemClick(location: LocationDto) {
        // 검색 항목 클릭 시 선택된 데이터를 반환하고 검색어 저장
        keywordViewModel.saveKeyword(location.place)
        val resultIntent = Intent().apply {
            putExtra("place_name", location.place)
            putExtra("road_address_name", location.address)
            putExtra("latitude", location.latitude)
            putExtra("longitude", location.longitude)
        }
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun onKeywordItemClick(keyword: String) {
        // 저장된 검색어 클릭
        binding.searchTextInput.setText(keyword)
        searchViewModel.searchLocationData(keyword)
    }

    override fun onKeywordItemDeleteClick(keyword: String) {
        // 저장된 검색어 삭제
        keywordViewModel.deleteKeyword(keyword)
    }
}