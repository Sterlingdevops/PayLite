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
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Contact
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseDialog
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import com.sterlingng.paylite.ui.send.ContactsAdapter
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

interface ContactsMvpView : MvpView

interface ContactsMvpContract<V : ContactsMvpView> : MvpPresenter<V>

class SelectContactPresenter<V : ContactsMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), ContactsMvpContract<V>

class ContactsBottomSheetFragment : BaseDialog(), ContactsMvpView, RecyclerViewClickListener, ContactsAdapter.OnRetryClicked {

    @Inject
    lateinit var mPresenter: ContactsMvpContract<ContactsMvpView>

    lateinit var onContactsItemSelectedListener: OnContactsItemSelected

    private lateinit var mSearchView: FloatingSearchView
    private lateinit var titleTextView: TextView
    private lateinit var closeImageView: ImageView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mContactsAdapter: SelectContactAdapter

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mDividerItemDecoration: DividerItemDecoration

    lateinit var contacts: List<Contact>
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

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_select_contact_bottom_sheet, null)

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
        mSearchView = view.findViewById(R.id.floating_search_view)
    }

    override fun setUp(view: View) {
        mContactsAdapter = SelectContactAdapter(baseActivity)
        mContactsAdapter.mRecyclerViewClickListener = this
        mContactsAdapter.add(contacts)
        mRecyclerView.adapter = mContactsAdapter
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.addItemDecoration(mDividerItemDecoration)

        titleTextView.text = title
        closeImageView.setOnClickListener {
            dialog.dismiss()
        }

        initSearchView()
    }


    private fun initSearchView() {
        mSearchView.setShowSearchKey(true)
        mSearchView.setOnQueryChangeListener { oldQuery, newQuery ->
            if (oldQuery != "" && newQuery == "") {
                mSearchView.clearSuggestions()
                mContactsAdapter.clear()
                mContactsAdapter.add(contacts)
            } else {
                val clone = ArrayList<Contact>()
                for (i in contacts.indices) {
                    if (contacts[i].name.toLowerCase().contains(newQuery.toLowerCase())) {
                        clone.add(contacts[i])
                    }
                    if (contacts[i].name.toLowerCase().contains(newQuery.toLowerCase())) {
                        clone.add(contacts[i])
                    }
                }
                mSearchView.swapSuggestions(clone)
                mContactsAdapter.clear()
                mContactsAdapter.add(clone)
            }
        }

        mSearchView.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSearchAction(currentQuery: String) {
                val clone = ArrayList<Contact>()
                for (i in contacts.indices) {
                    if (contacts[i].name.toLowerCase().contains(currentQuery.toLowerCase())) {
                        clone.add(contacts[i])
                    }
                    if (contacts[i].name.toLowerCase().contains(currentQuery.toLowerCase())) {
                        clone.add(contacts[i])
                    }
                }
                mSearchView.swapSuggestions(clone)
                mContactsAdapter.clear()
                mContactsAdapter.add(clone)
            }

            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion) {
                hideKeyboard()
            }
        })

        mSearchView.setOnClearSearchActionListener {
            mSearchView.clearSuggestions()
            mContactsAdapter.clear()
            mContactsAdapter.add(contacts)
        }

        //listen for when suggestion list expands/shrinks in order to move down/up the
        //search results list
        mSearchView.setOnSuggestionsListHeightChanged { newHeight -> mRecyclerView.translationY = newHeight }
    }

    override fun show(message: String, useToast: Boolean) {
        baseActivity.show(message, useToast)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {
        onContactsItemSelectedListener.onContactsItemSelected(dialog, selector, mContactsAdapter.get(position))
    }

    override fun onRetryClicked() {

    }

    interface OnContactsItemSelected {
        fun onContactsItemSelected(dialog: Dialog, selector: Int, contact: Contact)
    }

    companion object {

        fun newInstance(): ContactsBottomSheetFragment {
            val dialog = ContactsBottomSheetFragment()
            val args = Bundle()
            dialog.arguments = args
            return dialog
        }
    }
}
