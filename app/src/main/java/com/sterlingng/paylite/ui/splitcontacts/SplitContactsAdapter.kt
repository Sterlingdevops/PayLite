package com.sterlingng.paylite.ui.splitcontacts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.ContactItem
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import java.util.*

class SplitContactsAdapter(val mContext: Context) : RecyclerSwipeAdapter<BaseViewHolder>() {

    private val contacts: ArrayList<ContactItem> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener
    lateinit var onRetryClickedListener: OnRetryClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_add_contact_item, parent, false)
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

    override fun getSwipeLayoutResourceId(position: Int): Int = R.id.swipe

    fun get(position: Int): ContactItem = contacts[position]

    fun add(ContactItem: ContactItem) {
        contacts.add(ContactItem)
        notifyItemInserted(this.contacts.size - 1)
    }

    fun add(contacts: Collection<ContactItem>) {
        val index = this.contacts.size - 1
        this.contacts.addAll(contacts)
        notifyItemRangeInserted(index, contacts.size - 1)
    }

    fun remove(index: Int) {
        if (contacts.size <= 2) {
            Toast.makeText(mContext, "Split action needs at least people", LENGTH_SHORT).show()
            return
        }
        this.contacts.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clear() {
        for (index in 0 until contacts.size - 2) {
            this.contacts.removeAt(0)
            notifyItemRemoved(0)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (contacts.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun getItemCount(): Int {
        return if (contacts.size > 0) {
            contacts.size
        } else {
            1
        }
    }

    inner class ViewHolder(itemView: View, var recyclerViewClickListener: RecyclerViewClickListener) : BaseViewHolder(itemView) {

        private val swipeLayout: SwipeLayout = itemView.findViewById(R.id.swipe)
        private val chooseContactTextView: TextView = itemView.findViewById(R.id.choose_contact)
        private val deleteContactTextView: TextView = itemView.findViewById(R.id.delete)
        private val contactAmountEditText: EditText = itemView.findViewById(R.id.contact_amount)

        override fun onBind(position: Int) {
            super.onBind(position)

            swipeLayout.showMode = SwipeLayout.ShowMode.LayDown

            deleteContactTextView.setOnClickListener {
                remove(position)
            }

            chooseContactTextView.setOnClickListener {
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