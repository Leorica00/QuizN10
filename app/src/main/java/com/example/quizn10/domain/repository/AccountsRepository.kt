package com.example.quizn10.domain.repository

import com.example.quizn10.data.common.Resource
import com.example.quizn10.domain.model.GetAccount
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {
    suspend fun getAccounts(): Flow<Resource<List<GetAccount>>>
    suspend fun getAccount(): Flow<Resource<GetAccount>>
}