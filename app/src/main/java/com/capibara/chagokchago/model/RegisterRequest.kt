package com.capibara.chagokchago.model

data class RegisterRequest(
    val userName: String,
    val password: String,
    val telephone: String,
    val name: String
)