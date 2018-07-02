package com.sterlingng.paylite.ui.payment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.payment.pay.PayFragment
import com.sterlingng.paylite.ui.payment.request.RequestFragment
import com.sterlingng.paylite.ui.payment.scheduled.ScheduledFragment
import com.sterlingng.paylite.utils.CustomPagerAdapter
import com.sterlingng.paylite.utils.widgets.CustomViewPager
import javax.inject.Inject

class PaymentFragment : BaseFragment(), PaymentMvpView {

    @Inject
    lateinit var mPresenter: PaymentMvpContract<PaymentMvpView>

    @Inject
    lateinit var mPagerAdapter: CustomPagerAdapter

    private lateinit var mViewPager: CustomViewPager
    private lateinit var mSmartTabLayout: SmartTabLayout

    lateinit var textView: TextView
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_payment, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mViewPager = view.findViewById(R.id.viewpager)
        mSmartTabLayout = view.findViewById(R.id.viewpagertab)
    }

    override fun setUp(view: View) {
        mPagerAdapter.addFragment(PayFragment.newInstance(), "Pay")
        mPagerAdapter.addFragment(RequestFragment.newInstance(), "Request")
        mPagerAdapter.addFragment(ScheduledFragment.newInstance(), "Scheduled")

        mViewPager.adapter = mPagerAdapter
        mViewPager.isPagingEnabled = true

        mSmartTabLayout.setViewPager(mViewPager)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): PaymentFragment {
            val fragment = PaymentFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
