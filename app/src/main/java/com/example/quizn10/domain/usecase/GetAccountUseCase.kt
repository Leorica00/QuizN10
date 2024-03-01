package com.example.quizn10.domain.usecase

import com.example.quizn10.domain.repository.AccountsRepository
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(private val accountsRepository: AccountsRepository) {
    suspend operator fun invoke() = accountsRepository.getAccount()
}