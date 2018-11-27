package com.sterlingng.paylite.ui.transactions.categories

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

class MonthsAdapter(private val mContext: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    val items: ArrayList<String> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_transaction_date_item, parent, false)
                ViewHolder(view, mRecyclerViewClickListener)
            }
            else -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_transaction_date_item, parent, false)
                EmptyViewHolder(view)
            }
        }
    }

    fun get(position: Int): String = items[position]

    fun add(transaction: String) {
        items += transaction
        val itemsSet =
                items.asSequence()
                        .sortedByDescending { it }
                        .distinctBy { it }.toList()

        clear()

        items.addAll(itemsSet)
        notifyDataSetChanged()
    }

    fun add(newString: ArrayList<String>) {
        newString += this.items
        val itemsSet =
                newString.asSequence()
                        .sortedByDescending { it }
                        .distinctBy { it }.toList()

        clear()

        this.items.addAll(itemsSet)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        this.items.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clear() {
        for (index in 0 until items.size) {
            this.items.removeAt(0)
            notifyItemRemoved(0)
        }
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

        private var month: TextView = itemView.findViewById(R.id.month)

        override fun onBind(position: Int) {
            super.onBind(adapterPosition)
            month.text = items[adapterPosition].toUpperCase()
            month.setOnClickListener {
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
