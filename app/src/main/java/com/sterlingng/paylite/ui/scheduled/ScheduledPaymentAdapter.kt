package com.sterlingng.paylite.ui.scheduled

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.ScheduledPayment
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import com.sterlingng.paylite.utils.RecyclerViewLongClickListener
import com.sterlingng.paylite.utils.initials

class ScheduledPaymentAdapter(private val mContext: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    val payments: ArrayList<ScheduledPayment> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener
    lateinit var mRecyclerViewLongClickListener: RecyclerViewLongClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_scheduled_transaction_item, parent, false)
                ViewHolder(view, mRecyclerViewClickListener)
            }
            VIEW_TYPE_EMPTY -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_scheduled_transactions_empty_view, parent, false)
                EmptyViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_scheduled_transactions_empty_view, parent, false)
                EmptyViewHolder(view)
            }
        }
    }

    fun get(position: Int): ScheduledPayment = payments[position]

    fun add(contact: ScheduledPayment) {
        payments += contact
        val paymentsSet =
                payments.asSequence()
                        .sortedByDescending { it.reference }
                        .distinctBy { it.reference }.toList()

        clear()

        payments += paymentsSet
        notifyDataSetChanged()
    }

    fun add(newpayments: ArrayList<ScheduledPayment>) {
        newpayments += payments
        val paymentsSet =
                newpayments.asSequence()
                        .sortedByDescending { it.reference }
                        .distinctBy { it.reference }.toList()

        clear()

        payments += paymentsSet
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        this.payments.removeAt(index)
        notifyDataSetChanged()
    }

    fun clear() {
        for (index in 0 until payments.size) {
            payments.removeAt(0)
            notifyItemRemoved(0)
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (payments.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun getItemCount(): Int {
        return if (payments.size > 0) {
            payments.size
        } else {
            1
        }
    }

    inner class ViewHolder(itemView: View, private var recyclerViewClickListener: RecyclerViewClickListener) : BaseViewHolder(itemView) {
        private val contactNameTextView: TextView = itemView.findViewById(R.id.name)
        private val contactRateTextView: TextView = itemView.findViewById(R.id.rate)
        private val contactAmountTextView: TextView = itemView.findViewById(R.id.amount)
        private val contactInitialsTextView: TextView = itemView.findViewById(R.id.initials)

        override fun onBind(position: Int) {
            super.onBind(adapterPosition)

            with(payments[adapterPosition]) {
                contactNameTextView.text = beneficiaryId
                contactRateTextView.text = when (interval) {
                    1 -> "Daily"
                    2 -> "Weekly"
                    3 -> "Monthly"
                    4 -> "Yearly"
                    else -> "Never"
                }
                contactAmountTextView.text = mContext.getString(R.string.amount_in_naira, amount.toString())
                contactInitialsTextView.text = beneficiaryId.initials()
            }

            itemView.setOnClickListener {
                recyclerViewClickListener.recyclerViewItemClicked(it, adapterPosition)
            }

            itemView.setOnLongClickListener {
                mRecyclerViewLongClickListener.recyclerViewItemLongClicked(it, adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }

    inner class EmptyViewHolder(itemView: View) : BaseViewHolder(itemView)

    companion object {

        private const val VIEW_TYPE_NORMAL = 1
        private const val VIEW_TYPE_EMPTY = 0
    }
}