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
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class ContactsAdapter(val mContext: Context, val type: Int) : RecyclerView.Adapter<BaseViewHolder>() {

    val contacts: ArrayList<PayliteContact> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener
    lateinit var onRetryClickedListener: OnRetryClicked

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
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_view, parent, false)
                EmptyViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_view, parent, false)
                EmptyViewHolder(view)
            }
        }
    }

    fun get(position: Int): PayliteContact = contacts[position]

    fun add(contact: PayliteContact) {
        contacts.add(contact)
        notifyItemInserted(this.contacts.size - 1)
    }

    fun add(contacts: Collection<PayliteContact>) {
        val index = this.contacts.size - 1
        this.contacts.addAll(contacts.filterNot { it.firstname == "See" })
        notifyItemRangeInserted(index, contacts.size - 1)
    }

    fun remove(index: Int) {
        this.contacts.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clear() {
        for (index in 0 until contacts.size) {
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

    inner class ViewHolder(itemView: View, private var recyclerViewClickListener: RecyclerViewClickListener) : BaseViewHolder(itemView) {

        private val contactNameTextView: TextView = itemView.findViewById(R.id.name)
        private val contactInitialsTextView: TextView? = itemView.findViewById(R.id.initials)
        private val contactLogoImageView: CircleImageView = itemView.findViewById(R.id.image)

        override fun onBind(position: Int) {
            super.onBind(position)

            with(contacts[position]) {
                if (type == 1)
                    contactInitialsTextView?.text = "${firstname[0]}${lastname[0]}".toUpperCase()
                contactNameTextView.text = "$firstname $lastname"
                contactLogoImageView.setImageDrawable(ContextCompat.getDrawable(mContext, image))
                if (firstname == "See")
                    contactLogoImageView.scaleType = ImageView.ScaleType.CENTER_CROP
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