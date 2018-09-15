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
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TransactionAdapter(private val mContext: Context) : RecyclerView.Adapter<BaseViewHolder>(),
        StickyRecyclerHeadersAdapter<TransactionAdapter.HeaderViewHolder> {

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

    fun get(position: Int): Transaction = transactions[position]

    fun add(transaction: Transaction) {
        transactions += transaction
        val transactionsSet = transactions.sortedByDescending { it.id }.distinctBy { it.id }

        clear()

        this.transactions.addAll(transactionsSet)
        notifyDataSetChanged()
    }

    fun add(newTransactions: ArrayList<Transaction>) {
        newTransactions.addAll(this.transactions)
        val transactionsSet = newTransactions.sortedByDescending { it.id }.distinctBy { it.id }

        clear()

        this.transactions.addAll(transactionsSet)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        this.transactions.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clear() {
        for (index in 0 until transactions.size) {
            this.transactions.removeAt(0)
            notifyItemRemoved(0)
        }
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

    override fun getHeaderId(position: Int): Long {
        return if (transactions.size > 0) {
            val calendar = Calendar.getInstance()
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val date = formatter.parse(transactions[position].date.split("T")[0])
            calendar.time = Date(date.time)
            calendar.get(Calendar.DAY_OF_YEAR).toLong()
        } else
            0
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): HeaderViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_transactions_header_item, parent, false)
        return HeaderViewHolder(view)
    }

    override fun onBindHeaderViewHolder(holder: HeaderViewHolder, position: Int) {
        if (transactions.size > 0) {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val date = formatter.parse(transactions[position].date.split("T")[0])
            val dateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH)
            holder.headerName.text = dateFormat.format(date.time)
        }
    }

    inner class ViewHolder(itemView: View, private var recyclerViewClickListener: RecyclerViewClickListener) : BaseViewHolder(itemView) {
        private var transactionType: ImageView = itemView.findViewById(R.id.transaction_type)
        private var transactionDate: TextView = itemView.findViewById(R.id.transaction_date)
        private var transactionName: TextView = itemView.findViewById(R.id.transaction_name)
        private var transactionAmount: TextView = itemView.findViewById(R.id.transaction_amount)

        override fun onBind(position: Int) {
            super.onBind(adapterPosition)

            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH)
            val date = formatter.parse(transactions[adapterPosition].date)
            val dateFormat = SimpleDateFormat("hh:mm aaa", Locale.ENGLISH)

            with(transactions[adapterPosition]) {
                transactionAmount.text = if (type == "11") "+ ${mContext.getString(R.string.naira)} $amount" else "- ${mContext.getString(R.string.naira)} $amount"
                transactionName.text = name
                transactionDate.text = dateFormat.format(date.time)
                transactionType.setImageDrawable(ContextCompat.getDrawable(mContext, if (type == "11") R.drawable.arrow_bottom_left else R.drawable.arrow_top_right))
                transactionType.setColorFilter(ContextCompat.getColor(mContext, if (type == "11") R.color.apple_green else R.color.scarlet), android.graphics.PorterDuff.Mode.SRC_IN)
                transactionAmount.setTextColor(ContextCompat.getColor(mContext, if (type == "11") R.color.apple_green else R.color.scarlet))
            }
            itemView.setOnClickListener {
                recyclerViewClickListener.recyclerViewListClicked(it, adapterPosition)
            }
        }
    }

    inner class HeaderViewHolder(itemView: View) : BaseViewHolder(itemView) {
        var headerName: TextView = itemView.findViewById(R.id.header_name)
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
