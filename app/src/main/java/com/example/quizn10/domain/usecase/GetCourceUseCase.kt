package com.example.quizn10.domain.usecase

import com.example.quizn10.domain.repository.ValuteRepository
import javax.inject.Inject

class GetCourceUseCase @Inject constructor(private val valuteRepository: ValuteRepository) {
    suspend operator fun invoke() = valuteRepository.getValute()
}