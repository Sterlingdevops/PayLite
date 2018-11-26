package com.sterlingng.paylite.ui.splitcontacts

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
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
    lateinit var onDeleteContactWatcher: OnDeleteContactWatcher

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.layout_add_contact_item, parent, false)
        return ViewHolder(view,
                mRecyclerViewClickListener,
                onDeleteContactWatcher,
                ContactNameTextWatcher(),
                ContactAmountTextWatcher())
    }

    override fun getSwipeLayoutResourceId(adapterPosition: Int): Int = R.id.swipe

    fun get(position: Int): ContactItem = contacts[position]

    fun add(item: ContactItem) {
        contacts.add(item)
        notifyDataSetChanged()
    }

    fun add(contacts: Collection<ContactItem>) {
        this.contacts.addAll(contacts)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        if (contacts.size <= 2) {
            Toast.makeText(mContext, "Split action needs at least two people", LENGTH_SHORT).show()
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
        holder.mContactNameTextWatcher.updatePosition(holder.adapterPosition)

        holder.chooseContactEditText.setText(contacts[holder.adapterPosition].contact)
        holder.contactAmountEditText.setText(contacts[holder.adapterPosition].amount)
    }

    override fun getItemCount(): Int = contacts.size

    inner class ViewHolder(itemView: View,
                           recyclerViewClickListener: RecyclerViewClickListener,
                           onDeleteContactWatcher: OnDeleteContactWatcher,
                           contactNameTextWatcher: ContactNameTextWatcher,
                           contactAmountTextWatcher: ContactAmountTextWatcher) : RecyclerView.ViewHolder(itemView) {

        private val swipeLayout: SwipeLayout = itemView.findViewById(R.id.swipe)
        private val deleteContactTextView: TextView = itemView.findViewById(R.id.delete)
        val chooseContactEditText: EditText = itemView.findViewById(R.id.choose_contact)
        val contactAmountEditText: EditText = itemView.findViewById(R.id.contact_amount)

        val mContactNameTextWatcher: ContactNameTextWatcher = contactNameTextWatcher
        val mContactAmountTextWatcher: ContactAmountTextWatcher = contactAmountTextWatcher

        init {
            contactAmountEditText.addTextChangedListener(contactAmountTextWatcher)
            chooseContactEditText.addTextChangedListener(contactNameTextWatcher)
            swipeLayout.showMode = SwipeLayout.ShowMode.LayDown

            val drawable = ContextCompat.getDrawable(mContext, R.drawable.icon_phone_book)
            drawable?.setBounds(0, 0, (drawable.intrinsicWidth * 0.7).toInt(), (drawable.intrinsicHeight * 0.7).toInt())
            chooseContactEditText.setCompoundDrawables(null, null, drawable, null)
            chooseContactEditText.setOnTouchListener { view, event ->
                if (event.action == MotionEvent.ACTION_UP &&
                        event.rawX >= (chooseContactEditText.right - chooseContactEditText.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                    recyclerViewClickListener.recyclerViewItemClicked(view, adapterPosition)
                    return@setOnTouchListener true
                }
                return@setOnTouchListener false
            }

            deleteContactTextView.setOnClickListener {
                remove(adapterPosition)
                onDeleteContactWatcher.onContactDeleted()
            }
        }
    }

    companion object {
        const val DRAWABLE_RIGHT = 2
    }

    interface OnDeleteContactWatcher {
        fun onContactDeleted()
    }

    inner class ContactNameTextWatcher : TextWatcher {
        var index: Int = -1

        fun updatePosition(position: Int) {
            this.index = position
        }

        override fun afterTextChanged(s: Editable?) {
            contacts[index].contact = s?.toString()!!
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

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