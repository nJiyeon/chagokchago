package com.capibara.chagokchago.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capibara.chagokchago.R
import com.capibara.chagokchago.model.dto.RegisterRequestDto
import com.capibara.chagokchago.model.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextUserName: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextTelephone: EditText
    private lateinit var editTextName: EditText
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // View 연결
        editTextUserName = findViewById(R.id.editTextUserName)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextTelephone = findViewById(R.id.editTextTelephone)
        editTextName = findViewById(R.id.editTextName)
        buttonRegister = findViewById(R.id.buttonRegister)

        // 회원가입 버튼 클릭 시 네트워크 요청
        buttonRegister.setOnClickListener {
            val userName = editTextUserName.text.toString()
            val password = editTextPassword.text.toString()
            val telephone = editTextTelephone.text.toString()
            val name = editTextName.text.toString()

            // 회원가입 요청에 필요한 데이터 생성
            val registerRequest = RegisterRequestDto(userName, password, telephone, name)

            // Retrofit을 사용하여 API 호출
            RetrofitClient.apiService.registerUser(registerRequest).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // 성공적으로 회원가입이 완료되었을 때 처리
                        // MainActivity로 이동
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)

                        // 현재 RegisterActivity를 종료 (옵션)
                        finish()

                    } else {
                        // 서버 응답은 받았지만 실패한 경우 처리
                        Toast.makeText(this@RegisterActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    //Log.e("NetworkError", "네트워크 오류: ${t.message}", t)
                    t.printStackTrace()
                    Toast.makeText(this@RegisterActivity, "네트워크 오류: ${t.message}", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}