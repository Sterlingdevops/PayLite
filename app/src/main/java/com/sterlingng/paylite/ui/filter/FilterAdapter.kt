package com.sterlingng.paylite.ui.filter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import java.util.*

class FilterAdapter(val mContext: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    val items: ArrayList<String> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_filter_item, parent, false)
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

    fun getItemAtPosition(position: Int): String = items[position]

    fun addItem(String: String) {
        items.add(String)
        notifyItemInserted(this.items.size - 1)
    }

    fun addItems(Strings: Collection<String>) {
        val index = this.items.size - 1
        this.items.addAll(Strings)
        notifyItemRangeInserted(index, Strings.size - 1)
    }

    fun removeItem(index: Int) {
        this.items.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clear() {
        items.clear()
        notifyItemRangeRemoved(0, this.items.size)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (items.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun getItemCount(): Int {
        return if (items.size > 0) {
            items.size
        } else {
            1
        }
    }

    inner class ViewHolder(itemView: View, private var recyclerViewClickListener: RecyclerViewClickListener) : BaseViewHolder(itemView) {

        private val filterItemTextView: TextView = itemView.findViewById(R.id.filter_item)

        override fun onBind(position: Int) {
            super.onBind(position)

            with(items[position]) {
                filterItemTextView.text = this
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
            errorText.text = mContext.resources.getString(R.string.offline)
        }

        private fun retryClicked() {
            checkConnection()
        }
    }

    companion object {

        private const val VIEW_TYPE_NORMAL = 1
        private const val VIEW_TYPE_EMPTY = 0
    }
}