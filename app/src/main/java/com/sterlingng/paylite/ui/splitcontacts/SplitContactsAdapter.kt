package com.sterlingng.paylite.ui.splitcontacts

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
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
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import java.util.*


class SplitContactsAdapter(private val mContext: Context) : RecyclerSwipeAdapter<SplitContactsAdapter.ViewHolder>() {

    var contacts: ArrayList<ContactItem> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.layout_add_contact_item, parent, false)
        return ViewHolder(view, mRecyclerViewClickListener, ContactAmountTextWatcher())
    }

    override fun getSwipeLayoutResourceId(adapterPosition: Int): Int = R.id.swipe

    fun get(position: Int): ContactItem = contacts[position]

    fun add(item: ContactItem) {
        contacts.add(item)
        notifyDataSetChanged()
    }

    fun add(contacts: Collection<ContactItem>) {
        val index = this.contacts.size - 1
        this.contacts.addAll(contacts)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        if (contacts.size <= 2) {
            Toast.makeText(mContext, "Split action needs at least people", LENGTH_SHORT).show()
            return
        }
        contacts.removeAt(index)
        notifyDataSetChanged()
    }

    fun clear() {
        for (index in 0 until contacts.size - 2) {
            this.contacts.removeAt(0)
            notifyItemRemoved(0)
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mContactAmountTextWatcher.updatePosition(holder.adapterPosition)
        holder.chooseContactTextView.text = contacts[holder.adapterPosition].contact
        holder.contactAmountEditText.setText(contacts[holder.adapterPosition].amount)
    }

    override fun getItemCount(): Int = contacts.size

    inner class ViewHolder(itemView: View,
                           recyclerViewClickListener: RecyclerViewClickListener,
                           contactAmountTextWatcher: ContactAmountTextWatcher) : RecyclerView.ViewHolder(itemView) {

        private val swipeLayout: SwipeLayout = itemView.findViewById(R.id.swipe)
        private val deleteContactTextView: TextView = itemView.findViewById(R.id.delete)
        val chooseContactTextView: TextView = itemView.findViewById(R.id.choose_contact)
        val contactAmountEditText: EditText = itemView.findViewById(R.id.contact_amount)
        val mContactAmountTextWatcher: ContactAmountTextWatcher = contactAmountTextWatcher

        init {
            contactAmountEditText.addTextChangedListener(contactAmountTextWatcher)
            swipeLayout.showMode = SwipeLayout.ShowMode.LayDown

            deleteContactTextView.setOnClickListener {
                remove(adapterPosition)
            }

            chooseContactTextView.setOnClickListener {
                recyclerViewClickListener.recyclerViewListClicked(it, adapterPosition)
            }
        }
    }

    inner class ContactAmountTextWatcher : TextWatcher {
        var index: Int = -1

        fun updatePosition(position: Int) {
            this.index = position
        }

        override fun afterTextChanged(s: Editable?) {
            contacts[index].amount = s?.toString()!!
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }
}