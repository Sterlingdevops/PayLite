package com.sterlingng.paylite.ui.banktransfers.contacts

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arlib.floatingsearchview.FloatingSearchView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.send.ContactsAdapter
import com.sterlingng.paylite.utils.RecyclerViewLongClickListener
import javax.inject.Inject

class ContactsFragment : BaseFragment(), ContactsMvpView, RecyclerViewLongClickListener {

    @Inject
    lateinit var mPresenter: ContactsMvpContract<ContactsMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    private lateinit var mFloatingSearchView: FloatingSearchView
    private lateinit var mContactsAdapter: ContactsAdapter
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_choose_contact, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        mContactsAdapter = ContactsAdapter(baseActivity, 1)
        mContactsAdapter.mRecyclerViewLongClickListener = this
        mContactsAdapter.mRecyclerViewClickListener = this
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.adapter = mContactsAdapter
        mPresenter.onViewInitialized()
    }

    override fun bindViews(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mFloatingSearchView = view.findViewById(R.id.floating_search_view)
    }

    override fun initView(contacts: ArrayList<PayliteContact>) {
        mContactsAdapter.add(contacts)
    }

    override fun recyclerViewItemLongClicked(v: View, position: Int) {

    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): ContactsFragment {
            val fragment = ContactsFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }
}
