package com.sterlingng.paylite.ui.give.projects

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Category
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import java.util.*

class ProjectsAdapter(val mContext: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    val categories: ArrayList<Category> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener
    lateinit var onRetryClickedListener: OnRetryClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_categories_item, parent, false)
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

    fun getCategoryAtPosition(position: Int): Category = categories[position]

    fun addCategory(Category: Category) {
        categories.add(Category)
        notifyItemInserted(this.categories.size - 1)
    }

    fun addCategories(Categorys: Collection<Category>) {
        val index = this.categories.size - 1
        this.categories.addAll(Categorys)
        notifyItemRangeInserted(index, Categorys.size - 1)
    }

    fun removeCategory(index: Int) {
        this.categories.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clear() {
        categories.clear()
        notifyItemRangeRemoved(0, this.categories.size)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (categories.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun getItemCount(): Int {
        return if (categories.size > 0) {
            categories.size
        } else {
            1
        }
    }

    inner class ViewHolder(itemView: View, var recyclerViewClickListener: RecyclerViewClickListener) : BaseViewHolder(itemView) {

        private val categoryTextView: TextView = itemView.findViewById(R.id.category)
        private val categoryNameTextView: TextView = itemView.findViewById(R.id.category_name)
        private val categoryImageView: ImageView = itemView.findViewById(R.id.category_image)

        override fun onBind(position: Int) {
            super.onBind(position)

            with(categories[position]) {
                categoryTextView.text = category
                categoryNameTextView.text = name
                categoryImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.spartan))
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