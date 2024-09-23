package com.capibara.chagokchago.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.capibara.chagokchago.databinding.ActivityReserveParkingLotBinding

class ReserveParkingLotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ViewBinding 초기화
        val binding = ActivityReserveParkingLotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Spinner에 사용할 데이터
        val availableTime = listOf("시간을 선택하세요.", "이용 가능 시간 1", "이용 가능 시간 2", "이용 가능 시간 3", "이용 가능 시간 4")

        // ArrayAdapter 생성
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, availableTime)

        // 드롭다운 목록의 레이아웃 설정 (기본 레이아웃 사용)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Spinner에 어댑터 설정
        binding.inTimeSpinner.adapter = adapter
        binding.outTimeSpinner.adapter = adapter

        // Spinner 아이템 선택 리스너 설정
        binding.inTimeSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, // Spinner 자체
                view: View?, // 선택된 뷰
                position: Int, // 선택된 아이템의 위치
                id: Long // 선택된 아이템의 ID
            ) {
                // 선택된 놈 가져오기
                val selectedInTime = parent?.getItemAtPosition(position).toString()
                // 선택된 시간에 대한 Toast 메시지 표시
                if (position != 0) { // 선택을 안 한 상태가 아니면
                    Toast.makeText(this@ReserveParkingLotActivity, "$selectedInTime 선택됨", Toast.LENGTH_SHORT).show()
                }

            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // 아무것도 선택 안됐을 때
                Toast.makeText(this@ReserveParkingLotActivity, "시간을 선택해주세요.", Toast.LENGTH_SHORT).show()
            }

        }
    }
}