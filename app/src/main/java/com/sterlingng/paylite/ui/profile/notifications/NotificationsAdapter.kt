package com.sterlingng.paylite.ui.profile.notifications

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Notification
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import java.text.SimpleDateFormat
import java.util.*

class NotificationsAdapter(val mContext: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    val notifications: ArrayList<Notification> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_notification_item, parent, false)
                ViewHolder(view, mRecyclerViewClickListener)
            }
            VIEW_TYPE_EMPTY -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_notification_empty_view, parent, false)
                EmptyViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_notification_empty_view, parent, false)
                EmptyViewHolder(view)
            }
        }
    }

    fun get(position: Int): Notification = notifications[position]

    fun add(notification: Notification) {
        notifications.add(notification)
        notifyDataSetChanged()
    }

    fun add(notifications: Collection<Notification>) {
        this.notifications.addAll(notifications)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        this.notifications.removeAt(index)
        notifyDataSetChanged()
    }

    fun clear() {
        for (index in 0 until notifications.size) {
            this.notifications.removeAt(0)
            notifyItemRemoved(0)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (notifications.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun getItemCount(): Int {
        return if (notifications.size > 0) {
            notifications.size
        } else {
            1
        }
    }

    inner class ViewHolder(itemView: View, private var recyclerViewClickListener: RecyclerViewClickListener) : BaseViewHolder(itemView) {

        private val notificationNameTextView: TextView = itemView.findViewById(R.id.notification_text)
        private val notificationTypeTextView: TextView = itemView.findViewById(R.id.notification_date)
//        private val notificationLogoImageView: ImageView = itemView.findViewById(R.id.notification_logo)

        override fun onBind(position: Int) {
            super.onBind(adapterPosition)

            val simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.US)
            with(notifications[adapterPosition]) {
                notificationTypeTextView.text = simpleDateFormat.format(date)
                notificationNameTextView.text = mContext.getString(R.string.notification_placeholder)
//                notificationLogoImageView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.button_light_green))
            }
            itemView.setOnClickListener {
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