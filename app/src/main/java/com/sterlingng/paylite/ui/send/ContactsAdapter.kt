package com.sterlingng.paylite.ui.send

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import com.sterlingng.paylite.utils.RecyclerViewLongClickListener
import com.sterlingng.paylite.utils.initials
import com.sterlingng.paylite.utils.toSentenceCase
import de.hdodenhof.circleimageview.CircleImageView

class ContactsAdapter(val mContext: Context, val type: Int) : RecyclerView.Adapter<BaseViewHolder>() {

    val contacts: ArrayList<PayliteContact> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener
    lateinit var mRecyclerViewLongClickListener: RecyclerViewLongClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                when (type) {
                    0 -> {
                        view = LayoutInflater.from(mContext).inflate(R.layout.layout_contact_item, parent, false)
                        ViewHolder(view, mRecyclerViewClickListener)
                    }
                    else -> {
                        view = LayoutInflater.from(mContext).inflate(R.layout.layout_recent_contact_item, parent, false)
                        ViewHolder(view, mRecyclerViewClickListener)
                    }
                }
            }
            VIEW_TYPE_EMPTY -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_contact_view, parent, false)
                EmptyViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_contact_view, parent, false)
                EmptyViewHolder(view)
            }
        }
    }

    fun get(position: Int): PayliteContact = contacts[position]

    fun add(contact: PayliteContact) {
        contacts += contact
        val contactsSet =
                contacts.asSequence()
                        .sortedByDescending { it.id }
                        .distinctBy { it.id }.toList()

        clear()

        when (type) {
            0 -> contactsSet.forEach {
                if (contacts.size < 3) contacts += it
            }
            else -> contacts += contactsSet
        }
        notifyDataSetChanged()
    }

    fun add(newContacts: ArrayList<PayliteContact>) {
        newContacts += contacts
        val contactsSet =
                newContacts.asSequence()
                        .sortedByDescending { it.id }
                        .distinctBy { it.id }.toList()

        clear()

        when (type) {
            0 -> contactsSet.forEach {
                if (contacts.size < 3) contacts += it
            }
            else -> contacts += contactsSet
        }
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        this.contacts.removeAt(index)
        notifyDataSetChanged()
    }

    fun clear() {
        for (index in 0 until contacts.size) {
            contacts.removeAt(0)
            notifyItemRemoved(0)
        }
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

        private val contactNameTextView: TextView = itemView.findViewById(R.id.name)
        private val contactInitialsTextView: TextView? = itemView.findViewById(R.id.initials)
        private val contactLogoImageView: CircleImageView = itemView.findViewById(R.id.image)

        override fun onBind(position: Int) {
            super.onBind(adapterPosition)

            with(contacts[adapterPosition]) {
                contactNameTextView.text = name.toSentenceCase()
                contactInitialsTextView?.text = name.initials()
                when (type) {
                    0 -> contactLogoImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.color.apple_green))
                    else -> contactLogoImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.color.light_blue))
                }
                if (name == "See All") contactLogoImageView.scaleType = ImageView.ScaleType.CENTER_CROP
            }

            itemView.setOnClickListener {
                recyclerViewClickListener.recyclerViewItemClicked(it, adapterPosition)
            }

            itemView.setOnLongClickListener {
                mRecyclerViewLongClickListener.recyclerViewItemLongClicked(it, adapterPosition)
                return@setOnLongClickListener true
            }
        }
    }

    inner class EmptyViewHolder(itemView: View) : BaseViewHolder(itemView)

    companion object {

        private const val VIEW_TYPE_NORMAL = 1
        private const val VIEW_TYPE_EMPTY = 0
    }
}