package com.sterlingng.paylite.ui.services

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.VasProvider
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.ServiceItemClickedListener

class VasProviderAdapter(private val mContext: Context, private val sectionIndex: Int) : RecyclerView.Adapter<BaseViewHolder>() {

    var providers: ArrayList<VasProvider> = ArrayList()
    lateinit var serviceItemClickedListener: ServiceItemClickedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_vas_item, parent, false)
                ViewHolder(view, serviceItemClickedListener)
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

    fun get(position: Int): VasProvider = providers[position]

    fun add(vAService: VasProvider) {
        providers.add(vAService)
        val serviceSet =
                providers.asSequence()
                        .sortedByDescending { it.name }
                        .distinctBy { it.name }.toList()

        clear()

        providers.addAll(serviceSet)
        notifyDataSetChanged()
    }

    fun add(newVAService: ArrayList<VasProvider>) {
        newVAService += this.providers
        val serviceSet =
                newVAService.asSequence()
                        .sortedByDescending { it.name }
                        .distinctBy { it.name }.toList()

        clear()

        this.providers.addAll(serviceSet)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        this.providers.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clear() {
        for (index in 0 until providers.size) {
            this.providers.removeAt(0)
            notifyItemRemoved(0)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (providers.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun getItemCount(): Int {
        return if (providers.size > 0) {
            providers.size
        } else {
            1
        }
    }

    inner class ViewHolder(itemView: View, private var serviceItemClickedListener: ServiceItemClickedListener)
        : BaseViewHolder(itemView) {
        private var providerName: TextView = itemView.findViewById(R.id.name)
        private var providerImage: ImageView = itemView.findViewById(R.id.image)

        override fun onBind(position: Int) {
            super.onBind(adapterPosition)

            with(providers[position]) {
                providerName.text = name
                providerImage.setImageDrawable(ContextCompat.getDrawable(mContext, this.resId))
            }

            itemView.setOnClickListener {
                serviceItemClickedListener.serviceItemClicked(it, sectionIndex, adapterPosition)
            }
        }
    }

    inner class EmptyViewHolder(itemView: View) : BaseViewHolder(itemView)

    companion object {

        private const val VIEW_TYPE_NORMAL = 1
        private const val VIEW_TYPE_EMPTY = 0
    }
}
