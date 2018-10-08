package com.sterlingng.paylite.ui.scheduled

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.ScheduledPayment
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.sheduledtransaction.ScheduledTransactionFragment
import javax.inject.Inject

class ScheduledFragment : BaseFragment(), ScheduledMvpView {

    @Inject
    lateinit var mPresenter: ScheduledMvpContract<ScheduledMvpView>

    @Inject
    lateinit var mLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mScheduledPaymentAdapter: ScheduledPaymentAdapter

    private lateinit var exit: ImageView
    private lateinit var mBalanceTextView: TextView
    private lateinit var mRecyclerView: RecyclerView

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

        mScheduledPaymentAdapter.mRecyclerViewClickListener = this
        mRecyclerView.adapter = mScheduledPaymentAdapter
        mRecyclerView.layoutManager = mLayoutManager

        mPresenter.loadScheduledPayments()
        mPresenter.onViewInitialized()
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mBalanceTextView = view.findViewById(R.id.balance)
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

    override fun initView(wallet: Wallet, payments: ArrayList<ScheduledPayment>) {
        mBalanceTextView.text = String.format("Balance: ₦%,.2f", wallet.balance.toFloat())
        mScheduledPaymentAdapter.add(payments)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {
        (baseActivity as DashboardActivity)
                .mNavController
                .pushFragment(ScheduledTransactionFragment.newInstance(mScheduledPaymentAdapter.get(position)))
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
