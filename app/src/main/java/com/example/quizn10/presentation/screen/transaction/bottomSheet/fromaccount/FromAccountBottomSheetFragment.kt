package com.example.quizn10.presentation.screen.transaction.bottomSheet.fromaccount

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.quizn10.databinding.FragmentFromAccountBottomSheetListDialogBinding
import com.example.quizn10.presentation.state.TransactionState
import com.example.quizn10.presentation.model.Account
import com.example.quizn10.presentation.screen.transaction.adapter.FromAccountRecyclerViewAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FromAccountBottomSheetFragment(private val listener: OnAccountSelectedListener) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFromAccountBottomSheetListDialogBinding
    private lateinit var accountAdapter: FromAccountRecyclerViewAdapter
    private val viewModel: FromAccountBottomSheetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFromAccountBottomSheetListDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecycler()
        setUpObservers()

        val bottomSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        bottomSheetBehavior.peekHeight = 800
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })
    }

    private fun setUpRecycler() = with(binding.accountsRecyclerView) {
        accountAdapter = FromAccountRecyclerViewAdapter(object : OnAccountSelectedListener {
            override fun onAccountSelected(account: Account) {
                listener.onAccountSelected(account)
                dismiss()
            }
        })
        layoutManager = LinearLayoutManager(requireContext())
        adapter = accountAdapter
    }

    private fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.accountsStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun handleState(state: TransactionState) = with(state) {
        accounts?.let {
            accountAdapter.submitList(it)
        }
    }

    companion object {
        const val RESTAURANT_BOTTOM_SHEET = "RestaurantBottomSheet"
    }
}