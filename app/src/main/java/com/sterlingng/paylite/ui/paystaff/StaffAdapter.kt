package com.sterlingng.paylite.ui.paystaff

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import com.sterlingng.views.TitleLabelTextView
import java.text.SimpleDateFormat
import java.util.*

class StaffAdapter(private val mContext: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    val transactions: ArrayList<Transaction> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_payment_history_item, parent, false)
                ViewHolder(view, mRecyclerViewClickListener)
            }
            VIEW_TYPE_EMPTY -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_payment_history_empty_view, parent, false)
                EmptyViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_payment_history_empty_view, parent, false)
                EmptyViewHolder(view)
            }
        }
    }

    fun get(position: Int): Transaction = transactions[position]

    fun add(contact: Transaction) {
        transactions += contact
        val transactionsSet =
                transactions.asSequence()
                        .sortedByDescending { it.id }
                        .distinctBy { it.id }.toList()

        clear()

        transactions += transactionsSet
        notifyDataSetChanged()
    }

    fun add(newTransactions: ArrayList<Transaction>) {
        newTransactions += transactions
        val transactionsSet =
                newTransactions.asSequence()
                        .sortedByDescending { it.id }
                        .distinctBy { it.id }.toList()

        clear()

        transactions += transactionsSet
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        this.transactions.removeAt(index)
        notifyDataSetChanged()
    }

    fun clear() {
        for (index in 0 until transactions.size) {
            transactions.removeAt(0)
            notifyItemRemoved(0)
        }
        notifyDataSetChanged()
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
        private val historyTextView: TitleLabelTextView = itemView.findViewById(R.id.payment_history)

        override fun onBind(position: Int) {
            super.onBind(adapterPosition)

            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            val dateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH)

            with(transactions[adapterPosition]) {
                val date = formatter.parse(this.date)
                historyTextView.title = dateFormat.format(date)
                historyTextView.label = String.format("₦%,.2f", this.amount.toFloat())
            }

            itemView.setOnClickListener {
                recyclerViewClickListener.recyclerViewItemClicked(it, adapterPosition)
            }
        }
    }

    inner class EmptyViewHolder(itemView: View) : BaseViewHolder(itemView)

    companion object {

        private const val VIEW_TYPE_NORMAL = 1
        private const val VIEW_TYPE_EMPTY = 0
    }
}