package com.example.quizn10.presentation.screen.transaction.bottomSheet.toAccount

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizn10.R
import com.example.quizn10.data.common.Resource
import com.example.quizn10.domain.usecase.GetAccountUseCase
import com.example.quizn10.presentation.extension.getErrorMessage
import com.example.quizn10.presentation.mapper.toPresentation
import com.example.quizn10.presentation.state.AccountState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToAccountViewModel @Inject constructor(private val getAccountUseCase: GetAccountUseCase): ViewModel() {
    private val _accountStateFlow = MutableStateFlow(AccountState())
    val accountStateFlow = _accountStateFlow.asStateFlow()

    init {
        getAccount()
    }

    fun checkAccount(accountNumber: String) {
        when {
            accountNumber.length == 9 && accountNumber.all { it.digitToIntOrNull() != null } -> _accountStateFlow.update { currentState ->  currentState.copy(phoneNumber = accountNumber) }
            accountNumber.length == 11 && accountNumber.all { it.digitToIntOrNull() != null } -> _accountStateFlow.update { currentState ->  currentState.copy(personId = accountNumber) }
            accountNumber.length == 22 -> {
                d("accountNumber", "${accountNumber}, ${_accountStateFlow.value.neededAccount?.accountNumber}")
                if (accountNumber == _accountStateFlow.value.neededAccount?.accountNumber){
                    _accountStateFlow.update { currentState -> currentState.copy(accountNumber = accountNumber, isValidated = true) }
                } else
                    updateErrorMessage(R.string.not_valid)
            }
            else -> updateErrorMessage(R.string.not_valid)
        }
    }

    fun getAccount() {
        viewModelScope.launch {
            getAccountUseCase().collect {
                when(it) {
                    is Resource.Success -> _accountStateFlow.update { currentState ->  currentState.copy(neededAccount = it.response.toPresentation()) }
                    is Resource.Error -> updateErrorMessage(getErrorMessage(it.error))
                    else -> {}
                }
            }
        }
    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _accountStateFlow.update { currentState -> currentState.copy(errorMessage = errorMessage) }
    }

}