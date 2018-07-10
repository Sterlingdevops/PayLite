package com.sterlingng.paylite.ui.give.charities

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Charity
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import java.util.*

class CharitiesAdapter(val mContext: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    val charities: ArrayList<Charity> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener
    lateinit var onRetryClickedListener: OnRetryClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_charities_item, parent, false)
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

    fun getCharityAtPosition(position: Int): Charity = charities[position]

    fun addCharity(charity: Charity) {
        charities.add(charity)
        notifyItemInserted(this.charities.size - 1)
    }

    fun addCharities(charitys: Collection<Charity>) {
        val index = this.charities.size - 1
        this.charities.addAll(charitys)
        notifyItemRangeInserted(index, charitys.size - 1)
    }

    fun removeCharity(index: Int) {
        this.charities.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clear() {
        charities.clear()
        notifyItemRangeRemoved(0, this.charities.size)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (charities.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun getItemCount(): Int {
        return if (charities.size > 0) {
            charities.size
        } else {
            1
        }
    }

    inner class ViewHolder(itemView: View, private var recyclerViewClickListener: RecyclerViewClickListener) : BaseViewHolder(itemView) {

        val charityNameTextView: TextView = itemView.findViewById(R.id.charity_name)
        val charityTypeTextView: TextView = itemView.findViewById(R.id.charity_type)
        val charityLogoImageView: ImageView = itemView.findViewById(R.id.charity_logo)

        override fun onBind(position: Int) {
            super.onBind(position)

            with(charities[position]) {
                charityTypeTextView.text = category
                charityNameTextView.text = name
//                charityLogoImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.country_flag_gb))
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