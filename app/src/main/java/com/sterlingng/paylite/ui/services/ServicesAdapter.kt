package com.sterlingng.paylite.ui.services

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.VAService
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.ServiceItemClickedListener

class ServicesAdapter(private val mContext: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    val services: ArrayList<VAService> = ArrayList()
    private var recycledViewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()
    lateinit var serviceItemClickedListener: ServiceItemClickedListener
    private var snapHelper: SnapHelper = GravitySnapHelper(Gravity.START)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_services_item, parent, false)
                ViewHolder(view)
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

    fun get(position: Int): VAService = services[position]

    fun add(vAService: VAService) {
        services += vAService
        val serviceSet =
                services.asSequence()
                        .sortedByDescending { it.name }
                        .distinctBy { it.name }.toList()

        clear()

        services.addAll(serviceSet)
        notifyDataSetChanged()
    }

    fun add(newVAService: ArrayList<VAService>) {
        newVAService += this.services
        val serviceSet =
                newVAService.asSequence()
                        .sortedByDescending { it.name }
                        .distinctBy { it.name }.toList()

        clear()

        this.services.addAll(serviceSet)
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        this.services.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clear() {
        for (index in 0 until services.size) {
            this.services.removeAt(0)
            notifyItemRemoved(0)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (services.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun getItemCount(): Int {
        return if (services.size > 0) {
            services.size
        } else {
            1
        }
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private var serviceName: TextView = itemView.findViewById(R.id.service_name)
        private var serviceImage: ImageView = itemView.findViewById(R.id.service_image)
        private var recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)

        override fun onBind(position: Int) {
            super.onBind(adapterPosition)

            val vasProviderAdapter = VasProviderAdapter(mContext, adapterPosition)
            vasProviderAdapter.serviceItemClickedListener = serviceItemClickedListener
            vasProviderAdapter.providers = services[position].providers

            recyclerView.adapter = vasProviderAdapter
            recyclerView.setHasFixedSize(true)
            recyclerView.setRecycledViewPool(recycledViewPool)
            recyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            snapHelper.attachToRecyclerView(recyclerView)

            with(services[position]) {
                serviceName.text = name
                serviceImage.setImageDrawable(ContextCompat.getDrawable(mContext, this.resId))
            }
        }
    }

    inner class EmptyViewHolder(itemView: View) : BaseViewHolder(itemView)

    companion object {

        private const val VIEW_TYPE_NORMAL = 1
        private const val VIEW_TYPE_EMPTY = 0
    }
}
