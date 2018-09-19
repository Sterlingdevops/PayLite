package com.sterlingng.paylite.ui.contacts

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Contact
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import com.sterlingng.paylite.utils.initials
import java.util.*

class SelectContactAdapter(val mContext: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    private val contacts: ArrayList<Contact> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener
    lateinit var onRetryClickedListener: OnRetryClicked

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_select_contact_item, parent, false)
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

    fun get(position: Int): Contact = contacts[position]

    fun add(item: Contact) {
        contacts.add(item)
        notifyDataSetChanged()
    }

    fun add(contacts: Collection<Contact>) {
        this.contacts.addAll(contacts)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        contacts.removeAt(index)
        notifyDataSetChanged()
    }

    fun clear() {
        contacts.clear()
        notifyDataSetChanged()
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

    inner class ViewHolder(itemView: View, private var recyclerViewClickListener: RecyclerViewClickListener) : BaseViewHolder(itemView) {

        private val contactNameTextView: TextView = itemView.findViewById(R.id.contact_name)
        private val contactEmailTextView: TextView = itemView.findViewById(R.id.contact_email)
        private val contactPhoneTextView: TextView = itemView.findViewById(R.id.contact_phone)
        private val contactInitialsTextView: TextView = itemView.findViewById(R.id.contact_initials)

        @SuppressLint("SetTextI18n")
        override fun onBind(position: Int) {
            super.onBind(adapterPosition)

            with(contacts[adapterPosition]) {
                contactNameTextView.text = name

                if (emails.size <= 0)
                    contactEmailTextView.visibility = View.GONE
                else
                    contactEmailTextView.visibility = View.VISIBLE

                if (numbers.size <= 0)
                    contactPhoneTextView.visibility = View.GONE
                else
                    contactPhoneTextView.visibility = View.VISIBLE

                contactInitialsTextView.text = name.initials()
                contactEmailTextView.text = if (emails.size <= 0) "" else "${emails[0].address} - ${emails[0].type}"
                contactPhoneTextView.text = if (numbers.size <= 0) "" else "${numbers[0].number} - ${numbers[0].type}"
            }

            contactEmailTextView.setOnClickListener {
                recyclerViewClickListener.recyclerViewListClicked(it, adapterPosition)
            }

            contactPhoneTextView.setOnClickListener {
                recyclerViewClickListener.recyclerViewListClicked(it, adapterPosition)
            }
        }
    }

    inner class EmptyViewHolder(itemView: View) : BaseViewHolder(itemView) {

        private var errorText: TextView = itemView.findViewById(R.id.error_text)
        private var retry: TextView = itemView.findViewById(R.id.retry)

        override fun onBind(position: Int) {
            super.onBind(adapterPosition)
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