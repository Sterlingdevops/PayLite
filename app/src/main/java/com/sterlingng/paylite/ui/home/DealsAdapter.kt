package com.sterlingng.paylite.ui.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Deal
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import java.util.*

class DealsAdapter(val mContext: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    val deals: ArrayList<Deal> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener
    lateinit var onRetryClickedListener: OnRetryClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_deals_preview_item, parent, false)
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

    fun getDealAtPosition(position: Int): Deal = deals[position]

    fun addDeal(deal: Deal) {
        deals.add(deal)
        notifyItemInserted(this.deals.size - 1)
    }

    fun addDeals(deals: Collection<Deal>) {
        val index = this.deals.size - 1
        this.deals.addAll(deals)
        notifyItemRangeInserted(index, deals.size - 1)
    }

    fun removeDeal(index: Int) {
        this.deals.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clear() {
        deals.clear()
        notifyItemRangeRemoved(0, this.deals.size)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (deals.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun getItemCount(): Int {
        return if (deals.size > 0) {
            deals.size
        } else {
            1
        }
    }

    inner class ViewHolder(itemView: View, var recyclerViewClickListener: RecyclerViewClickListener) : BaseViewHolder(itemView) {

        override fun onBind(position: Int) {
            super.onBind(position)

            with(deals[position]) {

            }
            itemView.setOnClickListener {
                recyclerViewClickListener.recyclerViewListClicked(it, position)
            }
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
            if (!AppUtils.hasInternetConnection(mContext)) {
                errorText.text = mContext.resources.getString(R.string.offline)
            }
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