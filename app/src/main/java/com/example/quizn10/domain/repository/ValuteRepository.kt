package com.example.quizn10.domain.repository

import com.example.quizn10.data.common.Resource
import com.example.quizn10.domain.model.GetValute
import kotlinx.coroutines.flow.Flow

interface ValuteRepository {
    suspend fun getValute(): Flow<Resource<GetValute>>
}