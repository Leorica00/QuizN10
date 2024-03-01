package com.example.quizn10.data.service

import com.example.quizn10.data.model.ValuteDto
import retrofit2.Response
import retrofit2.http.GET

interface ValuteApiService {
    @GET("d9eab148-a083-4625-9f9a-9ada0d409ba3?from_account=USD&to_account=EUR")
    suspend fun getValute(): Response<ValuteDto>
}