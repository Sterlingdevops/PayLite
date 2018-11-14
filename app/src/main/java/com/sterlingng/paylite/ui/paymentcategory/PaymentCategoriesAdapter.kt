package com.sterlingng.paylite.ui.paymentcategory

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.PaymentCategory
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import com.sterlingng.paylite.utils.toSentenceCase

class PaymentCategoriesAdapter(private val mContext: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    val payments: ArrayList<PaymentCategory> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_payment_category_item, parent, false)
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

    fun get(position: Int): PaymentCategory = payments[position]

    fun add(payment: PaymentCategory) {
        payments += payment
        val paymentSet =
                payments.asSequence()
                        .sortedByDescending { it.name }
                        .distinctBy { it.name }.toList()

        clear()

        payments.addAll(paymentSet)
        notifyDataSetChanged()
    }

    fun add(newPayment: ArrayList<PaymentCategory>) {
        newPayment += this.payments
        val paymentSet =
                newPayment.asSequence()
                        .sortedByDescending { it.name }
                        .distinctBy { it.name }.toList()

        clear()

        this.payments.addAll(paymentSet)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        this.payments.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clear() {
        for (index in 0 until payments.size) {
            this.payments.removeAt(0)
            notifyItemRemoved(0)
        }
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

    private val selectedItems: SparseBooleanArray = SparseBooleanArray()

    fun toggleSelection(pos: Int) {
        selectedItems.clear()
        selectedItems.put(pos, true)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View, private var recyclerViewClickListener: RecyclerViewClickListener) : BaseViewHolder(itemView) {
        private val categoryName: TextView = itemView.findViewById(R.id.category_name)
        private val categoryImage: ImageView = itemView.findViewById(R.id.category_image)

        override fun onBind(position: Int) {
            super.onBind(adapterPosition)

            with(payments[adapterPosition]) {
                categoryName.text = this.name.toSentenceCase()
                categoryImage.setImageDrawable(ContextCompat.getDrawable(mContext, this.image))
            }

            if (selectedItems.get(adapterPosition)) {
                itemView.setBackgroundResource(R.drawable.background_pin_view)
            } else {
                itemView.background = null
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
