package com.example.quizn10.data.mapper

import com.example.quizn10.data.model.AccountDto
import com.example.quizn10.domain.model.GetAccount

fun AccountDto.toDomain() = GetAccount(
    id = id,
    accountName = accountName,
    accountNumber = accountNumber,
    valuteType = valuteType,
    cardType = cardType,
    balance = balance,
    cardLogo = cardLogo
)
