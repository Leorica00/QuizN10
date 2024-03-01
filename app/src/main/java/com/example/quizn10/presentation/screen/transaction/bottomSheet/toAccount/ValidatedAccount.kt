package com.example.quizn10.presentation.screen.transaction.bottomSheet.toAccount

import com.example.quizn10.presentation.model.Account

interface ValidatedAccount {
    fun getValidatedAccount(account: Account)
}