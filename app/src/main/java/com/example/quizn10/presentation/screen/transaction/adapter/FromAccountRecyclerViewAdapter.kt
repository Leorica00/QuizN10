package com.example.quizn10.presentation.screen.transaction.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quizn10.R
import com.example.quizn10.databinding.FragmentFromAccountBottomSheetListDialogItemBinding
import com.example.quizn10.presentation.model.Account
import com.example.quizn10.presentation.screen.transaction.bottomSheet.fromaccount.OnAccountSelectedListener

class FromAccountRecyclerViewAdapter(private val listener: OnAccountSelectedListener): ListAdapter<Account, FromAccountRecyclerViewAdapter.AccountViewHolder>(
    AccountItemDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        return AccountViewHolder(
            FragmentFromAccountBottomSheetListDialogItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind()
    }

    inner class AccountViewHolder(private val binding: FragmentFromAccountBottomSheetListDialogItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val account = currentList[adapterPosition]
            if (account.cardType == "VISA")
                imageViewCover.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_visa))
            else {
                imageViewCover.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_mastercard))
            }
            tvAccountName.text = account.accountName
            tvAccountNumber.text = itemView.resources.getString(R.string.dots).plus(account.accountNumber.takeLast(4))
            val valute = when(account.valuteType) {
                "GEL" -> R.string.gel
                "EUR" -> R.string.eur
                "USD" -> R.string.usd
                else -> 0
            }
            tvBalance.text = account.balance.toString().plus(" ").plus(itemView.resources.getString(valute))

            root.setOnClickListener {
                listener.onAccountSelected(account)
            }
        }
    }

    companion object {
        private val AccountItemDiffCallback = object : DiffUtil.ItemCallback<Account>() {

            override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
                return oldItem == newItem
            }
        }
    }
}