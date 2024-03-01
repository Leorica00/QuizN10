package com.example.quizn10.presentation.state

import com.example.quizn10.presentation.model.Account

data class AccountState(
    val neededAccount: Account? = null,
    val accountNumber: String? = null,
    val personId: String? = null,
    val phoneNumber: String? = null,
    val errorMessage: Int? = null,
    val isValidated: Boolean = false
)