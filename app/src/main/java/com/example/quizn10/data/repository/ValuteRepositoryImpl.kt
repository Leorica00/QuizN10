package com.example.quizn10.data.repository

import com.example.quizn10.data.common.HandleResponse
import com.example.quizn10.data.common.Resource
import com.example.quizn10.data.mapper.base.asResource
import com.example.quizn10.data.mapper.toDomain
import com.example.quizn10.data.service.ValuteApiService
import com.example.quizn10.domain.model.GetValute
import com.example.quizn10.domain.repository.ValuteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ValuteRepositoryImpl @Inject constructor(private val valuteApiService: ValuteApiService, private val handleResponse: HandleResponse): ValuteRepository {
    override suspend fun getValute(): Flow<Resource<GetValute>> {
        return handleResponse.safeApiCall {
            valuteApiService.getValute()
        }.asResource {
            it.toDomain()
        }
    }
}