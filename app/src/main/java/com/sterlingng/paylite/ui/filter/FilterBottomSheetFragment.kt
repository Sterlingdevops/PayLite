package com.sterlingng.paylite.ui.filter

import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseDialog
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import com.sterlingng.paylite.utils.then
import javax.inject.Inject

class FilterBottomSheetFragment : BaseDialog(), FilterMvpView, RecyclerViewClickListener {

    @Inject
    lateinit var mPresenter: FilterMvpContract<FilterMvpView>

    lateinit var onFilterItemSelectedListener: OnFilterItemSelected

    private lateinit var titleTextView: TextView
    private lateinit var closeImageView: ImageView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mFilterAdapter: FilterAdapter

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mDividerItemDecoration: DividerItemDecoration

    lateinit var items: List<String>
    lateinit var title: String
    var selector: Int = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_filter_bottom_sheet, container, false)

        val component = activityComponent
        dialog.setContentView(view)
        component.inject(this)
        mPresenter.onAttach(this)

        showsDialog = false
        bindViews(view)
        setUp(view)

        return view
    }

    override fun bindViews(view: View) {
        titleTextView = view.findViewById(R.id.title)
        closeImageView = view.findViewById(R.id.close)
        mRecyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun setUp(view: View) {
        mFilterAdapter = FilterAdapter(baseActivity)
        mFilterAdapter.mRecyclerViewClickListener = this
        mFilterAdapter.add(items)
        mRecyclerView.adapter = mFilterAdapter
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.addItemDecoration(mDividerItemDecoration)

        titleTextView.text = title
        closeImageView.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun getPeekHeight(): Int {
        val displayMetrics = baseActivity.resources.displayMetrics
        val height = displayMetrics.heightPixels
        return arguments?.getBoolean(HEIGHT)!! then super.getPeekHeight() ?: (height * 0.9).toInt()
    }

    override fun show(message: String, useToast: Boolean) {
        baseActivity.show(message, useToast)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {
        onFilterItemSelectedListener.onFilterItemSelected(dialog, selector, items[position])
    }

    interface OnFilterItemSelected {
        fun onFilterItemSelected(dialog: Dialog, selector: Int, s: String)
    }

    companion object {

        const val HEIGHT = "FilterBottomSheetFragment.HEIGHT"

        @JvmOverloads
        fun newInstance(height: Boolean = false): FilterBottomSheetFragment {
            val dialog = FilterBottomSheetFragment()
            val args = Bundle()
            args.putBoolean(HEIGHT, height)
            dialog.arguments = args
            return dialog
        }
    }
}
