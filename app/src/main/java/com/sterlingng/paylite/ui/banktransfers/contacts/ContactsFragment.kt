package com.sterlingng.paylite.ui.banktransfers.contacts

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arlib.floatingsearchview.FloatingSearchView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject

class ContactsFragment : BaseFragment(), ContactsMvpView {

    @Inject
    lateinit var mPresenter: ContactsMvpContract<ContactsMvpView>

    private lateinit var mFloatingSearchView: FloatingSearchView
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_choose_contact, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {

    }

    override fun bindViews(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mFloatingSearchView = view.findViewById(R.id.floating_search_view)
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
