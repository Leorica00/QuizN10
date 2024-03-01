package com.example.quizn10.data.service

import com.example.quizn10.data.model.AccountDto
import retrofit2.Response
import retrofit2.http.GET

interface AccountsApiService {
    @GET("5c74ec0e-5cc1-445e-b64b-b76b286b215f")
    suspend fun getAccounts(): Response<List<AccountDto>>

    @GET("4253786f-3500-4148-9ebc-1fe7fb9dc8d5?account_number=EU67JG7744036080300903")
    suspend fun getAccount(): Response<AccountDto>
}