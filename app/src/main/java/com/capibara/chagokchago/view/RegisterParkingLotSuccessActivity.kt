package com.capibara.chagokchago.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.capibara.chagokchago.databinding.ActivityRegisterParkingLotSuccessBinding

class RegisterParkingLotSuccessActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterParkingLotSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 바인딩 객체 획득
        binding = ActivityRegisterParkingLotSuccessBinding.inflate(layoutInflater)
        settingButton()
        // 액티비티 화면 출력
        setContentView(binding.root)
    }

    fun settingButton() {
        binding.homeBtn.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        binding.registerBtn.setOnClickListener{
            val intent = Intent(this, RegisterParkingLotActivity::class.java)
            startActivity(intent)
        }
    }
}