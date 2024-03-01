package com.example.quizn10.domain.model

data class GetAccount(
    val id: Int,
    val accountName: String,
    val accountNumber: String,
    val valuteType: String,
    val cardType: String,
    val balance: Int,
    val cardLogo: String?
)
