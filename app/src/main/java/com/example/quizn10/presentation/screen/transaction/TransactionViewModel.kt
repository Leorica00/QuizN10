package com.example.quizn10.presentation.screen.transaction

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizn10.data.common.Resource
import com.example.quizn10.domain.usecase.GetCourceUseCase
import com.example.quizn10.presentation.extension.getErrorMessage
import com.example.quizn10.presentation.state.TransactionState
import com.example.quizn10.presentation.model.Account
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getCourceUseCase: GetCourceUseCase
) : ViewModel() {

    private val _accountsStateFlow = MutableStateFlow(TransactionState())
    val accountsStateFlow = _accountsStateFlow.asStateFlow()

    private fun updateErrorMessage(errorMessage: Int?) {
        _accountsStateFlow.update { currentState -> currentState.copy(errorMessage = errorMessage) }
    }

    fun updateSelectedAccountFrom(account: Account) {
        _accountsStateFlow.update { currentState -> currentState.copy(selectedAccountFrom = account) }
    }

    fun updateSelectedAccountTo(account: Account) {
        _accountsStateFlow.update { currentState -> currentState.copy(selectedAccountTo = account) }
    }

    fun getCourse() {
        viewModelScope.launch {
            getCourceUseCase().collect {resource ->
                d("resource234", resource.toString())
                when(resource) {
                    is Resource.Loading -> _accountsStateFlow.update { currentState -> currentState.copy(isLoading = resource.loading) }
                    is Resource.Error -> updateErrorMessage(getErrorMessage(resource.error))
                    is Resource.Success -> _accountsStateFlow.update { currentState -> currentState.copy(valuteCourse = resource.response.course) }
                }
            }
        }
    }

    fun convertValute(money: String) {
        money.toDoubleOrNull()?.let {money->
            _accountsStateFlow.value.valuteCourse?.let {
                _accountsStateFlow.update { currentState -> currentState.copy(convertedMoney = money * it) }
            }
        }
    }
}