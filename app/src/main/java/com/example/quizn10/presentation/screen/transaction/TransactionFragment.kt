package com.example.quizn10.presentation.screen.transaction

import android.util.Log.d
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizn10.databinding.FragmentTransactionBinding
import com.example.quizn10.presentation.base.BaseFragment
import com.example.quizn10.presentation.model.Account
import com.example.quizn10.presentation.screen.transaction.adapter.FromAccountRecyclerViewAdapter
import com.example.quizn10.presentation.screen.transaction.bottomSheet.fromaccount.FromAccountBottomSheetFragment
import com.example.quizn10.presentation.screen.transaction.bottomSheet.fromaccount.OnAccountSelectedListener
import com.example.quizn10.presentation.screen.transaction.bottomSheet.toAccount.ToAccountBottomSheetFragment
import com.example.quizn10.presentation.screen.transaction.bottomSheet.toAccount.ValidatedAccount
import com.example.quizn10.presentation.state.TransactionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransactionFragment : BaseFragment<FragmentTransactionBinding>(FragmentTransactionBinding::inflate),
    OnAccountSelectedListener, ValidatedAccount {
    private val viewModel: TransactionViewModel by viewModels()
    private val modalBottomSheet = FromAccountBottomSheetFragment(this)
    private val toAccountModalBottomSheet = ToAccountBottomSheetFragment(this)
    private val recyclerAdapter = FromAccountRecyclerViewAdapter(this)

    override fun setUp() {
        setUpRecycler()
    }

    override fun setUpListeners() {
        binding.btnFromAccount.setOnClickListener {
            setUpBottomSheet()
        }

        binding.btnToAccount.setOnClickListener {
            setUpToAccountBottomSheet()
        }
    }

    override fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.accountsStateFlow.collect {
                    d("selectedAccountFrom", it.selectedAccountFrom.toString())
                    d("selectedAccountTo", it.selectedAccountTo.toString())
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: TransactionState) {
        with(state) {
            d("wowBroDavigale", state.toString())
            selectedAccountFrom?.let {from ->
                selectedAccountTo?.let {to ->
                    val list = listOf(from, to)
                    recyclerAdapter.submitList(list)
                    with(binding) {
                        if (from.valuteType == to.valuteType) {
                            layoutStandard.visibility = View.VISIBLE
                        }else {
                            viewModel.getCourse()
                            layoutStandard.visibility = View.VISIBLE
                            layoutConverted.visibility = View.VISIBLE
                            etBalanceStandard.doAfterTextChanged {
                                viewModel.convertValute(it.toString())
                            }
                        }
                    }
                }
            }

            convertedMoney?.let {
                binding.tvBalanceConverted.setText(it.toString())
            }
        }
    }

    private fun setUpRecycler() {
        with(binding.recyclerViewAccount) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerAdapter
        }
    }

    private fun setUpBottomSheet() {
        modalBottomSheet.show(parentFragmentManager, FromAccountBottomSheetFragment.RESTAURANT_BOTTOM_SHEET)
    }

    private fun setUpToAccountBottomSheet() {
        toAccountModalBottomSheet.show(parentFragmentManager, ToAccountBottomSheetFragment.RESTAURANT_BOTTOM_SHEET)
    }

    override fun onAccountSelected(account: Account) {
        viewModel.updateSelectedAccountFrom(account)
    }

    override fun getValidatedAccount(account: Account) {
        viewModel.updateSelectedAccountTo(account)
    }
}