package com.sterlingng.paylite.ui.contacts

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
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Contact
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseDialog
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

interface SelectContactsMvpView : MvpView

interface SelectContactsMvpContract<V : SelectContactsMvpView> : MvpPresenter<V>

class SelectContactPresenter<V : SelectContactsMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), SelectContactsMvpContract<V>

class SelectContactsBottomSheetFragment : BaseDialog(), SelectContactsMvpView, RecyclerViewClickListener, SelectContactsAdapter.OnRetryClicked {

    @Inject
    lateinit var mPresenter: SelectContactsMvpContract<SelectContactsMvpView>

    lateinit var onSelectContactsItemSelectedListener: OnSelectContactsItemSelected

    private lateinit var titleTextView: TextView
    private lateinit var closeImageView: ImageView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSelectContactsAdapter: SelectContactsAdapter

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mDividerItemDecoration: DividerItemDecoration

    lateinit var items: List<Contact>
    lateinit var title: String
    var selector: Int = -1

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
        titleTextView = view.findViewById(R.id.title)
        closeImageView = view.findViewById(R.id.close)
        mRecyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun setUp(view: View) {
        mSelectContactsAdapter = SelectContactsAdapter(baseActivity)
        mSelectContactsAdapter.mRecyclerViewClickListener = this
        mSelectContactsAdapter.onRetryClickedListener = this
        mSelectContactsAdapter.add(items)
        mRecyclerView.adapter = mSelectContactsAdapter
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.addItemDecoration(mDividerItemDecoration)

        titleTextView.text = title
        closeImageView.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun show(message: String, useToast: Boolean) {
        baseActivity.show(message, useToast)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {
        onSelectContactsItemSelectedListener.onSelectContactsItemSelected(dialog, selector, items[position])
    }

    override fun onRetryClicked() {

    }

    interface OnSelectContactsItemSelected {
        fun onSelectContactsItemSelected(dialog: Dialog, selector: Int, contact: Contact)
    }

    companion object {

        fun newInstance(): SelectContactsBottomSheetFragment {
            val dialog = SelectContactsBottomSheetFragment()
            val args = Bundle()
            dialog.arguments = args
            return dialog
        }
    }
}
