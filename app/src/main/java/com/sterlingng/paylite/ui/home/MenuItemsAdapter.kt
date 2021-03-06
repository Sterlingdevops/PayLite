package com.sterlingng.paylite.ui.home

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.MenuItem
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import com.sterlingng.paylite.utils.ViewUtils.dpToPx
import com.sterlingng.paylite.utils.then
import java.util.*

class MenuItemsAdapter(val mContext: Context, val type: Mode) : RecyclerView.Adapter<BaseViewHolder>() {

    var items: ArrayList<MenuItem> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                when (type) {
                    Mode.LIST -> {
                        view = LayoutInflater.from(mContext).inflate(R.layout.home_list_view_item, parent, false)
                        ViewHolder(view, mRecyclerViewClickListener)
                    }
                    Mode.GRID -> {
                        view = LayoutInflater.from(mContext).inflate(R.layout.home_grid_view_item, parent, false)
                        ViewHolder(view, mRecyclerViewClickListener)
                    }
                }
            }
            else -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_view, parent, false)
                EmptyViewHolder(view)
            }
        }
    }

    fun get(position: Int): MenuItem = items[position]

    fun add(notification: MenuItem) {
        items.add(notification)
        notifyItemInserted(this.items.size - 1)
    }

    fun add(items: Collection<MenuItem>) {
        val index = this.items.size - 1
        this.items.addAll(items)
        notifyItemRangeInserted(index, items.size - 1)
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
        private val titleTextView: TextView = itemView.findViewById(R.id.title)
        private val iconImageView: ImageView = itemView.findViewById(R.id.icon)

        override fun onBind(position: Int) {
            super.onBind(adapterPosition)
            when (type) {
                Mode.GRID -> {
                    val layoutParams: ViewGroup.MarginLayoutParams = itemView.layoutParams as ViewGroup.MarginLayoutParams

                    (adapterPosition % 2 == 0) then
                    layoutParams.setMargins(dpToPx(16f), dpToPx(16f), dpToPx(8f),
                            (adapterPosition == items.count() - 1) then dpToPx(16f) ?: 0)
                            ?: layoutParams.setMargins(dpToPx(8f), dpToPx(16f), dpToPx(16f),
                                    (adapterPosition == items.count() - 1) then dpToPx(16f) ?: 0)

                    itemView.layoutParams = layoutParams
                    itemView.requestLayout()
                }
                Mode.LIST -> return
            }

            with(items[adapterPosition]) {
                titleTextView.text = title
                iconImageView.setImageDrawable(ContextCompat.getDrawable(mContext, icon))
            }
            itemView.setOnClickListener {
                recyclerViewClickListener.recyclerViewItemClicked(it, adapterPosition)
            }
        }
    }

    enum class Mode {
        GRID, LIST
    }

    inner class EmptyViewHolder(itemView: View) : BaseViewHolder(itemView)

    companion object {

        private const val VIEW_TYPE_NORMAL = 1
        private const val VIEW_TYPE_EMPTY = 0
    }
}