package com.sterlingng.paylite.ui.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Deal
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeMvpView, DealsAdapter.OnRetryClicked {

    @Inject
    lateinit var mPresenter: HomeMvpContract<HomeMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mDealsAdapter: DealsAdapter

    @Inject
    lateinit var mSnapHelper: SnapHelper

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mTopUpButton: Button
    private lateinit var mHistoryButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mTopUpButton = view.findViewById(R.id.top_up)
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mHistoryButton = view.findViewById(R.id.view_history)
    }

    override fun setUp(view: View) {
        mLinearLayoutManager.orientation = RecyclerView.HORIZONTAL
        mDealsAdapter.mRecyclerViewClickListener = this
        mDealsAdapter.onRetryClickedListener = this
        mRecyclerView.adapter = mDealsAdapter
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.scrollToPosition(0)
        mSnapHelper.attachToRecyclerView(mRecyclerView)

        mPresenter.loadDeals()
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    override fun onRetryClicked() {

    }

    override fun updateDeals(it: ArrayList<Deal>) {
        mDealsAdapter.addDeals(it)
        mRecyclerView.scrollToPosition(0)
    }

    companion object {

        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
