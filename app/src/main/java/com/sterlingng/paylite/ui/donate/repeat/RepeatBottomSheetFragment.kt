package com.sterlingng.paylite.ui.donate.repeat


import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseDialog
import com.sterlingng.paylite.ui.give.filter.FilterAdapter
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import javax.inject.Inject

class RepeatBottomSheetFragment : BaseDialog(), RepeatMvpView, RecyclerViewClickListener {

    @Inject
    lateinit var mPresenter: RepeatMvpContract<RepeatMvpView>

    lateinit var mOnRepeatItemSelectedListener: OnRepeatItemSelected

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mRepeatAdapter: FilterAdapter

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mDividerItemDecoration: DividerItemDecoration

    private val items = listOf("Never", "Daily", "Weekly", "Monthly", "Yearly")

    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {

        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(baseActivity, theme)
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_filter_bottom_sheet, null)

        val component = activityComponent
        dialog.setContentView(view)
        component.inject(this)
        mPresenter.onAttach(this)

        val params = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior

        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
        }

        bindViews(view)
        setUp(view)
    }

    override fun bindViews(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun setUp(view: View) {
        mRepeatAdapter = FilterAdapter(baseActivity)
        mRepeatAdapter.mRecyclerViewClickListener = this
        mRepeatAdapter.addItems(items)
        mRecyclerView.adapter = mRepeatAdapter
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.addItemDecoration(mDividerItemDecoration)
    }

    override fun show(message: String, useToast: Boolean) {
        baseActivity.show(message, useToast)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {
        mOnRepeatItemSelectedListener.onRepeatItemSelected(dialog, items[position])
    }

    interface OnRepeatItemSelected {
        fun onRepeatItemSelected(dialog: Dialog, s: String)
    }

    companion object {

        fun newInstance(): RepeatBottomSheetFragment {
            val dialog = RepeatBottomSheetFragment()
            val args = Bundle()
            dialog.arguments = args
            return dialog
        }
    }
}
