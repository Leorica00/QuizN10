package com.example.quizn10.data.repository

import com.example.quizn10.data.common.HandleResponse
import com.example.quizn10.data.common.Resource
import com.example.quizn10.data.mapper.base.asResource
import com.example.quizn10.data.mapper.toDomain
import com.example.quizn10.data.service.AccountsApiService
import com.example.quizn10.domain.model.GetAccount
import com.example.quizn10.domain.repository.AccountsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AccountRepositoryImpl@Inject constructor(private val accountsApiService: AccountsApiService, private val handleResponse: HandleResponse): AccountsRepository {
    override suspend fun getAccounts(): Flow<Resource<List<GetAccount>>> {
        return handleResponse.safeApiCall {
            accountsApiService.getAccounts()
        }.asResource {
            it.map { it.toDomain() }
        }
    }

    override suspend fun getAccount(): Flow<Resource<GetAccount>> {
        return handleResponse.safeApiCall {
            accountsApiService.getAccount()
        }.asResource {
            it.toDomain()
        }
    }

}