package com.sterlingng.paylite.ui.transactions.insights

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.PaymentCategory
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject

class InsightsFragment : BaseFragment(), InsightsMvpView {

    @Inject
    lateinit var mPresenter: InsightsMvpContract<InsightsMvpView>

    lateinit var mLinearLayoutManager: LinearLayoutManager

    private lateinit var mInsightsAdapter: InsightsAdapter
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_insights, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        mLinearLayoutManager = object : LinearLayoutManager(baseActivity) {
            override fun isAutoMeasureEnabled(): Boolean = true
        }
        mRecyclerView.layoutManager = mLinearLayoutManager

        mInsightsAdapter = InsightsAdapter(baseActivity)
        mInsightsAdapter.mRecyclerViewClickListener = this
        mRecyclerView.adapter = mInsightsAdapter

        ViewCompat.setNestedScrollingEnabled(mRecyclerView, false)

        mPresenter.onViewInitialized()
    }

    override fun bindViews(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun initView(mockPaymentCategories: ArrayList<PaymentCategory>) {
        mInsightsAdapter.add(mockPaymentCategories)
    }

    companion object {

        fun newInstance(): InsightsFragment {
            val fragment = InsightsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
