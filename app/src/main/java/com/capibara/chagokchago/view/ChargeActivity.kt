package com.capibara.chagokchago.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.capibara.chagokchago.R
import com.capibara.chagokchago.databinding.ActivityChargeBinding
import com.capibara.chagokchago.model.dto.BankItemDto
import com.capibara.chagokchago.model.adapter.BankSpinnerAdapter

class ChargeActivity : AppCompatActivity() {

    lateinit var binding: ActivityChargeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ViewBinding 초기화
        binding = ActivityChargeBinding.inflate(layoutInflater)

        settingButton()

        setContentView(binding.root)

        // Spinner에 사용할 데이터: BankItem 리스트
        val bankList = listOf(
            BankItemDto("입금하실 은행을\n선택하세요", R.drawable.baseline_attach_money_24),
            BankItemDto("농협은행", R.drawable.ic_nonghyup),
            BankItemDto("카카오뱅크", R.drawable.ic_kakaobank),
            BankItemDto("KB국민은행", R.drawable.ic_kb),
            BankItemDto("우리은행", R.drawable.ic_shinhan),
            BankItemDto("하나은행", R.drawable.ic_hana)
        )

        // 커스텀 어댑터 생성
        val adapter = BankSpinnerAdapter(this, R.layout.spinner_bank_item, bankList)

        // Spinner에 어댑터 설정
        binding.spinner.adapter = adapter

        // Spinner 아이템 선택 리스너 설정
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedBank = bankList[position].name

                if (position != 0) { // "선택하세요"가 아닐 경우
                    Toast.makeText(this@ChargeActivity, "$selectedBank 선택됨", Toast.LENGTH_SHORT).show()
                    // 추가적인 동작 구현 가능 (예: 다른 Activity로 이동)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    fun settingButton() {
        binding.charge.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "충전이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}