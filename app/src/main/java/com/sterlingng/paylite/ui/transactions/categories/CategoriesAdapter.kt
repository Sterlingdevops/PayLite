package com.sterlingng.paylite.ui.transactions.categories

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import java.util.*

class CategoriesAdapter(private val mContext: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    val transactions: ArrayList<Transaction> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener
    lateinit var onCreateNewClickedListener: OnCreateNewClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_transaction_item, parent, false)
                ViewHolder(view, mRecyclerViewClickListener)
            }
            VIEW_TYPE_EMPTY -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_view, parent, false)
                EmptyViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_view, parent, false)
                EmptyViewHolder(view)
            }
        }
    }

    fun getCreditTransactionAtPosition(position: Int): Transaction = transactions[position]

    fun addCreditTransaction(transaction: Transaction) {
        transactions.add(transaction)
        notifyItemInserted(this.transactions.size - 1)
    }

    fun addTransactions(transactions: Collection<Transaction>) {
        val index = this.transactions.size - 1
        this.transactions.addAll(transactions)
        notifyItemRangeInserted(index, transactions.size - 1)
    }

    fun removeFixture(index: Int) {
        this.transactions.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clear() {
        transactions.clear()
        notifyItemRangeRemoved(0, this.transactions.size)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (transactions.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun getItemCount(): Int {
        return if (transactions.size > 0) {
            transactions.size
        } else {
            1
        }
    }

    inner class ViewHolder(itemView: View, private var recyclerViewClickListener: RecyclerViewClickListener) : BaseViewHolder(itemView) {
        private var transactionType: ImageView = itemView.findViewById(R.id.transaction_type)
        private var transactionDate: TextView = itemView.findViewById(R.id.transaction_date)
        private var transactionName: TextView = itemView.findViewById(R.id.transaction_name)
        private var transactionAmount: TextView = itemView.findViewById(R.id.transaction_amount)

        override fun onBind(position: Int) {
            super.onBind(position)
            with(transactions[position]) {
                transactionAmount.text = "${mContext.getString(R.string.naira)}$amount"
                transactionName.text = name
                transactionDate.text = count
                transactionType.setImageDrawable(ContextCompat.getDrawable(mContext, if (type == Transaction.TransactionType.Credit) R.drawable.arrow_bottom_left else R.drawable.arrow_top_right))
                transactionType.setColorFilter(ContextCompat.getColor(mContext, if (type == Transaction.TransactionType.Credit) R.color.apple_green else R.color.scarlet), android.graphics.PorterDuff.Mode.SRC_IN)
                transactionAmount.setTextColor(ContextCompat.getColor(mContext, if (type == Transaction.TransactionType.Credit) R.color.apple_green else R.color.scarlet))
            }
            itemView.setOnClickListener {
                recyclerViewClickListener.recyclerViewListClicked(it, position)
            }
        }
    }

    inner class EmptyViewHolder(itemView: View) : BaseViewHolder(itemView) {

        private var createCreditTransaction: TextView = itemView.findViewById(R.id.retry)

        override fun onBind(position: Int) {
            super.onBind(position)
            createCreditTransaction.setOnClickListener { onCreateNewClickedListener.onCreateNew() }
        }
    }

    interface OnCreateNewClicked {
        fun onCreateNew()
    }

    companion object {

        private const val VIEW_TYPE_NORMAL = 1
        private const val VIEW_TYPE_EMPTY = 0
    }
}
