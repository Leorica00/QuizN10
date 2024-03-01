package com.example.quizn10.presentation.state

import com.example.quizn10.presentation.model.Account

data class TransactionState(
    val accounts: List<Account>? = null,
    val checkAccount: Account? = null,
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
    val valuteCourse: Double? = null,
    val selectedAccountFrom: Account? = null,
    val selectedAccountTo: Account? = null,
    val convertedMoney: Double? = null
)
