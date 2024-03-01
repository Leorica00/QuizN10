package com.example.quizn10.data.model

import com.squareup.moshi.Json

data class AccountDto(
    val id: Int,
    @Json(name = "account_name") val accountName: String,
    @Json(name = "account_number") val accountNumber: String,
    @Json(name = "valute_type") val valuteType: String,
    @Json(name = "card_type") val cardType: String,
    val balance: Int = 0,
    @Json(name = "card_logo") val cardLogo: String?
)