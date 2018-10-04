package com.sterlingng.paylite.ui.scheduled

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.ScheduledPayment
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject

class ScheduledFragment : BaseFragment(), ScheduledMvpView {

    @Inject
    lateinit var mPresenter: ScheduledMvpContract<ScheduledMvpView>

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    private lateinit var exit: ImageView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mScheduledPaymentAdapter: ScheduledPaymentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_scheduled, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mPresenter.loadScheduledPayments()

        mScheduledPaymentAdapter = ScheduledPaymentAdapter(baseActivity)
        mScheduledPaymentAdapter.mRecyclerViewClickListener = this
        mRecyclerView.adapter = mScheduledPaymentAdapter
        mRecyclerView.layoutManager = mLayoutManager
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mRecyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun onScheduledPaymentsSuccessful(payments: ArrayList<ScheduledPayment>) {
        mScheduledPaymentAdapter.add(payments)
    }

    override fun onScheduledPaymentsFailed(response: Response) {
        show("Failed getting transactions", true)
    }

    override fun logout() {
        baseActivity.logout()
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): ScheduledFragment {
            val fragment = ScheduledFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }
}
