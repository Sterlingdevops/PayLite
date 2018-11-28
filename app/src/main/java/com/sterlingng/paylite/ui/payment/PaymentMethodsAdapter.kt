package com.sterlingng.paylite.ui.payment

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.PaymentMethod
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import com.sterlingng.paylite.utils.then
import java.util.*


class PaymentMethodsAdapter(val mContext: Context) : RecyclerSwipeAdapter<BaseViewHolder>() {

    val paymentMethods: ArrayList<PaymentMethod> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener
    lateinit var onAddPaymentMethodListener: OnAddPaymentMethod
    lateinit var onDeletePaymentMethodListener: OnDeletePaymentMethod

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_payment_method_item, parent, false)
                ViewHolder(view, onDeletePaymentMethodListener, mRecyclerViewClickListener)
            }
            VIEW_TYPE_EMPTY -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_fund_empty_view, parent, false)
                EmptyViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_fund_empty_view, parent, false)
                EmptyViewHolder(view)
            }
        }
    }

    override fun getSwipeLayoutResourceId(adapterPosition: Int): Int = R.id.swipe

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

    inner class ViewHolder(itemView: View,
                           private var onDeletePaymentMethodListener: OnDeletePaymentMethod,
                           private var recyclerViewClickListener: RecyclerViewClickListener) : BaseViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.image)
        private val swipeLayout: SwipeLayout = itemView.findViewById(R.id.swipe)
        private val expiryTextView: TextView = itemView.findViewById(R.id.expiry)
        private val numberTextView: TextView = itemView.findViewById(R.id.number)
        private val root: ConstraintLayout = itemView.findViewById(R.id.payment_method)
        private val deleteContactTextView: TextView = itemView.findViewById(R.id.delete)

        override fun onBind(position: Int) {
            super.onBind(adapterPosition)

            swipeLayout.showMode = SwipeLayout.ShowMode.PullOut

            with(paymentMethods[adapterPosition]) {
                expiryTextView.text = isCard then "Exp: $expiry" ?: ""
                numberTextView.text = isCard then "Card: ${String(number.toCharArray().takeLast(4).toCharArray())}" ?: "$bankname: $number"
                imageView.setImageDrawable(ContextCompat.getDrawable(mContext,
                        (image != 0) then image ?: R.drawable.icon_card))
            }

            deleteContactTextView.setOnClickListener {
                onDeletePaymentMethodListener.onPaymentMethodDeleted(adapterPosition)
            }

            root.setOnClickListener {
                recyclerViewClickListener.recyclerViewItemClicked(it, adapterPosition)
            }

            if (paymentMethods[adapterPosition].default) toggleSelection(adapterPosition)
        }
    }

    inner class EmptyViewHolder(itemView: View) : BaseViewHolder(itemView) {

        private var addPaymentMethod: TextView = itemView.findViewById(R.id.add_payment_method)

        override fun onBind(position: Int) {
            super.onBind(position)
            addPaymentMethod.setOnClickListener {
                onAddPaymentMethodListener.onAddPaymentMethodClicked()
            }
        }
    }

    interface OnDeletePaymentMethod {
        fun onPaymentMethodDeleted(adapterPosition: Int)
    }

    interface OnAddPaymentMethod {
        fun onAddPaymentMethodClicked()
    }

    companion object {

        private const val VIEW_TYPE_NORMAL = 1
        private const val VIEW_TYPE_EMPTY = 0
    }
}