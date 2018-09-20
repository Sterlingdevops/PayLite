package com.sterlingng.paylite.ui.payment

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.PaymentMethod
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import com.sterlingng.paylite.utils.then
import java.util.*


class PaymentMethodsAdapter(val mContext: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    val paymentMethods: ArrayList<PaymentMethod> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener
    lateinit var onRetryClickedListener: OnRetryClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_payment_method_item, parent, false)
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

    private val selectedItems: SparseBooleanArray = SparseBooleanArray()

    fun toggleSelection(pos: Int) {
        selectedItems.clear()
        selectedItems.put(pos, true)
        notifyDataSetChanged()
    }

    fun clearSelections() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun get(position: Int): PaymentMethod = paymentMethods[position]

    fun add(PaymentMethod: PaymentMethod) {
        paymentMethods.add(PaymentMethod)
        notifyItemInserted(this.paymentMethods.size - 1)
    }

    fun add(projects: Collection<PaymentMethod>) {
        val index = this.paymentMethods.size - 1
        this.paymentMethods.addAll(projects)
        notifyItemRangeInserted(index, projects.size - 1)
    }

    fun remove(index: Int) {
        this.paymentMethods.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clear() {
        paymentMethods.clear()
        notifyItemRangeRemoved(0, this.paymentMethods.size)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (paymentMethods.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun getItemCount(): Int {
        return if (paymentMethods.size > 0) {
            paymentMethods.size
        } else {
            1
        }
    }

    inner class ViewHolder(itemView: View, private var recyclerViewClickListener: RecyclerViewClickListener) : BaseViewHolder(itemView) {

        private val expiryTextView: TextView = itemView.findViewById(R.id.expiry)
        private val nameTextView: TextView = itemView.findViewById(R.id.name)
        private val numberTextView: TextView = itemView.findViewById(R.id.number)
        private val imageView: ImageView = itemView.findViewById(R.id.image)
        private val defaultCheckBox: CheckBox = itemView.findViewById(R.id.checkbox)
        private val defaultTextView: TextView = itemView.findViewById(R.id.default_payment_method)

        override fun onBind(position: Int) {
            super.onBind(adapterPosition)

            with(paymentMethods[adapterPosition]) {
                expiryTextView.text = expiry
                nameTextView.text = name
                numberTextView.text = number
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext,
                        (image != 0) then image ?: R.drawable.card))
            }

            defaultCheckBox.isChecked = selectedItems.get(adapterPosition)
            if (selectedItems.get(adapterPosition)) {
                defaultTextView.text = mContext.getString(R.string.default_payment_method)
                defaultTextView.setTextColor(ContextCompat.getColor(mContext, R.color.black))
            } else {
                defaultTextView.text = mContext.getString(R.string.set_default_payment_method)
                defaultTextView.setTextColor(ContextCompat.getColor(mContext, R.color.dark_sky_blue))
            }

            defaultTextView.setOnClickListener {
                recyclerViewClickListener.recyclerViewListClicked(it, adapterPosition)
            }

            itemView.setOnClickListener {
                recyclerViewClickListener.recyclerViewListClicked(it, adapterPosition)
            }

            if (paymentMethods[adapterPosition].default) toggleSelection(adapterPosition)
        }
    }

    inner class EmptyViewHolder(itemView: View) : BaseViewHolder(itemView) {

        private var errorText: TextView = itemView.findViewById(R.id.error_text)
        private var retry: TextView = itemView.findViewById(R.id.retry)

        override fun onBind(position: Int) {
            super.onBind(position)
            checkConnection()
            retry.setOnClickListener { retryClicked() }
        }

        private fun checkConnection() {
            errorText.text = mContext.resources.getString(R.string.offline)
        }

        private fun retryClicked() {
            checkConnection()
            onRetryClickedListener.onRetryClicked()
        }
    }

    interface OnRetryClicked {
        fun onRetryClicked()
    }

    companion object {

        private const val VIEW_TYPE_NORMAL = 1
        private const val VIEW_TYPE_EMPTY = 0
    }
}