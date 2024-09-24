package com.capibara.chagokchago.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capibara.chagokchago.databinding.ActivityLoginBinding
import com.capibara.chagokchago.model.dto.LoginRequestDto

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val userName = binding.userNameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (userName.isNotEmpty() && password.isNotEmpty()) {
                val loginRequest = LoginRequestDto(
                    userName = userName,
                    password = password
                )

                // TODO: 서버로 로그인 요청 전송
                // 예시: apiService.loginUser(loginRequest)

                Toast.makeText(this, "로그인 요청이 전송되었습니다.", Toast.LENGTH_SHORT).show()
                // 로그인 성공 시 메인 화면으로 이동
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerTextView.setOnClickListener {
            // 회원가입 화면으로 이동
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}