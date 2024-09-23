package com.capibara.chagokchago.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.capibara.chagokchago.databinding.ActivityRegisterParkingLotBinding

class RegisterParkingLotActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterParkingLotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 바인딩 객체 획득
        binding = ActivityRegisterParkingLotBinding.inflate(layoutInflater)
        settingButton()
        // 액티비티 화면 출력
        setContentView(binding.root)
    }
    fun settingButton() {
        binding.registerBtn.setOnClickListener{
            val intent = Intent(this, RegisterParkingLotSuccessActivity::class.java)
            startActivity(intent)
        }
    }
}