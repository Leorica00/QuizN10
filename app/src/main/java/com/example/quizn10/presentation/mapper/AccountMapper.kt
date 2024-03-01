package com.example.quizn10.presentation.mapper

import com.example.quizn10.domain.model.GetAccount
import com.example.quizn10.presentation.model.Account

fun GetAccount.toPresentation() = Account(
    id = id,
    accountName = accountName,
    accountNumber = accountNumber,
    valuteType = valuteType,
    cardType = cardType,
    balance = balance,
    cardLogo = cardLogo
)