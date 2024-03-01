package com.example.quizn10.presentation.screen.transaction.bottomSheet.fromaccount

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizn10.data.common.Resource
import com.example.quizn10.domain.usecase.GetAccountsUseCase
import com.example.quizn10.presentation.state.TransactionState
import com.example.quizn10.presentation.extension.getErrorMessage
import com.example.quizn10.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FromAccountBottomSheetViewModel @Inject constructor(
    private val getAccountsUseCase: GetAccountsUseCase,
) : ViewModel() {

    private val _fromAccountStateFlow = MutableStateFlow(TransactionState())
    val accountsStateFlow = _fromAccountStateFlow.asStateFlow()

    init {
        fetchAccounts()
    }

    private fun fetchAccounts() {
        viewModelScope.launch {
            getAccountsUseCase().collect {resource ->
                when(resource) {
                    is Resource.Loading -> _fromAccountStateFlow.update { currentState -> currentState.copy(isLoading = resource.loading) }
                    is Resource.Error -> updateErrorMessage(getErrorMessage(resource.error))
                    is Resource.Success -> _fromAccountStateFlow.update { currentState -> currentState.copy(accounts = resource.response.map { it.toPresentation() }) }
                }
            }
        }
    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _fromAccountStateFlow.update { currentState -> currentState.copy(errorMessage = errorMessage) }
    }
}