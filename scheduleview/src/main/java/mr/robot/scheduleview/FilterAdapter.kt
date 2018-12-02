package mr.robot.scheduleview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*

class FilterAdapter(val mContext: Context) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {

    private val items: ArrayList<String> = ArrayList()
    lateinit var mRecyclerViewClickListener: RecyclerViewClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.layout_filter_item, parent, false)
        return ViewHolder(view, mRecyclerViewClickListener)
    }

    fun add(String: String) {
        items.add(String)
        notifyItemInserted(this.items.size - 1)
    }

    fun add(Strings: Collection<String>) {
        val index = this.items.size - 1
        this.items.addAll(Strings)
        notifyItemRangeInserted(index, Strings.size - 1)
    }

    fun remove(index: Int) {
        this.items.removeAt(index)
        notifyItemRemoved(index)
    }

    fun clear() {
        items.clear()
        notifyItemRangeRemoved(0, this.items.size)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.filterItemTextView.text = items[viewHolder.adapterPosition]
        viewHolder.itemView.setOnClickListener {
            viewHolder.recyclerViewClickListener.recyclerViewItemClicked(it, position)
        }
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View, var recyclerViewClickListener: RecyclerViewClickListener) : RecyclerView.ViewHolder(itemView) {
        val filterItemTextView: TextView = itemView.findViewById(R.id.filter_item)
    }
}