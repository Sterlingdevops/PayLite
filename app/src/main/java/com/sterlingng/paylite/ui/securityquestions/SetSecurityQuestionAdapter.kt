package com.sterlingng.paylite.ui.securityquestions

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Question
import com.sterlingng.paylite.ui.base.BaseViewHolder

class SetSecurityQuestionAdapter(val mContext: Context) : RecyclerView.Adapter<BaseViewHolder>() {

    private val questions: ArrayList<Question> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view: View?
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_question_item, parent, false)
                ViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_view, parent, false)
                EmptyViewHolder(view)
            }
        }
    }

    fun get(position: Int): Question = questions[position]

    fun add(contact: Question) {
        questions += contact
        notifyDataSetChanged()
    }

    fun add(newContacts: ArrayList<Question>) {
        questions += newContacts
        notifyDataSetChanged()
    }

    fun remove(index: Int) {
        this.questions.removeAt(index)
        notifyDataSetChanged()
    }

    fun clear() {
        for (index in 0 until questions.size) {
            questions.removeAt(0)
            notifyItemRemoved(0)
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (questions.size > 0) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun getItemCount(): Int {
        return if (questions.size > 0) {
            questions.size
        } else {
            1
        }
    }

    inner class ViewHolder(itemView: View) : BaseViewHolder(itemView) {
        private val questionTextView: EditText = itemView.findViewById(R.id.question)

        override fun onBind(position: Int) {
            super.onBind(adapterPosition)

            questionTextView.setText(questions[position].question)
        }
    }

    inner class EmptyViewHolder(itemView: View) : BaseViewHolder(itemView)

    companion object {

        private const val VIEW_TYPE_NORMAL = 1
        private const val VIEW_TYPE_EMPTY = 0
    }
}