package com.sterlingng.paylite.ui.fund.choose

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseViewHolder
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import java.util.*

data class Card(val name: String, val logo: Int)

class ChooseCardAdapter(val mContext: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    val cards: ArrayList<Card> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_card_item, parent, false)
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

    fun getCardAtPosition(position: Int): Card = cards[position]

    fun addCard(card: Card) {
        cards.add(card)
        notifyItemInserted(this.cards.size - 1)
    }

    fun addCards(cards: List<Card>) {
        val index = this.cards.size - 1
        this.cards.addAll(cards)
        notifyItemRangeInserted(index, cards.size - 1)
    }

    fun removeCard(index: Int) {
        this.cards.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clear() {
        cards.clear()
        notifyItemRangeRemoved(0, this.cards.size)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (cards.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun getItemCount(): Int {
        return if (cards.size > 0) {
            cards.size
        } else {
            1
        }
    }

    inner class ViewHolder(cardView: View, private var recyclerViewClickListener: RecyclerViewClickListener) : BaseViewHolder(cardView) {

        private val cardNameTextView: TextView = cardView.findViewById(R.id.card_name)
        private val cardLogoImageView: ImageView = cardView.findViewById(R.id.card_logo)

        override fun onBind(position: Int) {
            super.onBind(position)

            with(cards[position]) {
                cardNameTextView.text = name
                cardLogoImageView.setImageDrawable(ContextCompat.getDrawable(mContext, logo))
            }
            itemView.setOnClickListener {
                recyclerViewClickListener.recyclerViewListClicked(it, position)
            }
        }
    }

    inner class EmptyViewHolder(cardView: View) : BaseViewHolder(cardView) {

        private var errorText: TextView = cardView.findViewById(R.id.error_text)
        private var retry: TextView = cardView.findViewById(R.id.retry)

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
        }
    }

    companion object {

        private const val VIEW_TYPE_NORMAL = 1
        private const val VIEW_TYPE_EMPTY = 0
    }
}