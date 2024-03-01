package com.example.quizn10.presentation.screen.transaction.bottomSheet.toAccount

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.quizn10.databinding.FragmentToAccountBottomSheetBinding
import com.example.quizn10.presentation.extension.showSnackBar
import com.example.quizn10.presentation.state.AccountState
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ToAccountBottomSheetFragment(private val validatedAccount: ValidatedAccount) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentToAccountBottomSheetBinding
    private val viewModel: ToAccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToAccountBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setUpListener()

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

    private fun setUpObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.accountStateFlow.collect {
                    handleState(it)
                }
            }
        }
    }

    private fun setUpListener() {
        binding.btnSubmit.setOnClickListener {
            viewModel.checkAccount(binding.etAccount.text.toString())
        }
    }

    private fun handleState(state: AccountState) {
        with(state) {
            errorMessage?.let {
                requireView().showSnackBar(resources.getString(it))
            }
            if (isValidated) {
                neededAccount?.let {
                    validatedAccount.getValidatedAccount(it)
                    dismiss()
                }
            }
        }
    }

    companion object {
        const val RESTAURANT_BOTTOM_SHEET = "FromAccountBottomSheet"
    }
}