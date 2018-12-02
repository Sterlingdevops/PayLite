package mr.robot.scheduleview


import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment

class FilterBottomSheetFragment : SuperBottomSheetFragment(), RecyclerViewClickListener {
    lateinit var onFilterItemSelectedListener: OnFilterItemSelected

    private lateinit var titleTextView: TextView
    private lateinit var closeImageView: ImageView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mFilterAdapter: FilterAdapter

    lateinit var items: List<String>
    lateinit var title: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_filter_bottom_sheet, container, false)

        titleTextView = view.findViewById(R.id.title)
        closeImageView = view.findViewById(R.id.close)
        mRecyclerView = view.findViewById(R.id.recyclerView)

        mFilterAdapter = FilterAdapter(context!!)
        mFilterAdapter.mRecyclerViewClickListener = this
        mFilterAdapter.add(items)
        mRecyclerView.adapter = mFilterAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        titleTextView.text = title
        closeImageView.setOnClickListener {
            dialog.dismiss()
        }

        return view
    }

    override fun getStatusBarColor() = Color.RED

    override fun recyclerViewItemClicked(v: View, position: Int) {
        onFilterItemSelectedListener.onFilterItemSelected(dialog, items[position])
    }

    interface OnFilterItemSelected {
        fun onFilterItemSelected(dialog: Dialog, s: String)
    }

    companion object {

        fun newInstance(): FilterBottomSheetFragment {
            val dialog = FilterBottomSheetFragment()
            val args = Bundle()
            dialog.arguments = args
            return dialog
        }
    }
}
