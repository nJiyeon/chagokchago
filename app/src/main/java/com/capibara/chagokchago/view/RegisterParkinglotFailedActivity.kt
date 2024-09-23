package com.capibara.chagokchago.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.capibara.chagokchago.databinding.ActivityRegisterParkingLotFailedBinding

class RegisterParkingLotFailedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 바인딩 객체 획득
        val binding = ActivityRegisterParkingLotFailedBinding.inflate(layoutInflater)
        // 액티비티 화면 출력
        setContentView(binding.root)
    }
}