package com.sterlingng.paylite.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.UpdateWallet
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.airtime.AirTimeFragment
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.cashoutbank.CashOutFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.fund.FundFragment
import com.sterlingng.paylite.ui.profile.ProfileFragment
import com.sterlingng.paylite.ui.request.RequestFragment
import com.sterlingng.paylite.ui.send.SendMoneyFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeMvpView {

    @Inject
    lateinit var mPresenter: HomeMvpContract<HomeMvpView>

    @Inject
    lateinit var eventBus: EventBus

    private lateinit var mNotificationsImageView: ImageView
    private lateinit var mRequestMoneyImageView: ImageView
    private lateinit var mAirTimeDataImageView: ImageView
    private lateinit var mSendMoneyImageView: ImageView
    private lateinit var mPayBillsImageView: ImageView
    private lateinit var mScheduledImageView: ImageView
    private lateinit var mCashOutImageView: ImageView

    private lateinit var mRequestMoneyTextView: TextView
    private lateinit var mAirTimeDataTextView: TextView
    private lateinit var mSendMoneyTextView: TextView
    private lateinit var mPayBillsTextView: TextView
    private lateinit var mCashOutTextView: TextView
    private lateinit var mScheduledTextView: TextView

    private lateinit var mUserGreetingTextView: TextView
    private lateinit var mMainAmountTextView: TextView
    private lateinit var mFundButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mNotificationsImageView = view.findViewById(R.id.notifications_icon)

        mAirTimeDataImageView = view.findViewById(R.id.airtime_data)
        mAirTimeDataTextView = view.findViewById(R.id.airtime_data_text)

        mPayBillsImageView = view.findViewById(R.id.bill_payment)
        mPayBillsTextView = view.findViewById(R.id.pay_bills_text)

        mSendMoneyImageView = view.findViewById(R.id.send_money)
        mSendMoneyTextView = view.findViewById(R.id.send_money_text)

        mRequestMoneyImageView = view.findViewById(R.id.payment_request)
        mRequestMoneyTextView = view.findViewById(R.id.payment_request_text)

        mCashOutImageView = view.findViewById(R.id.cash_out)
        mCashOutTextView = view.findViewById(R.id.cash_out_text)

        mScheduledImageView = view.findViewById(R.id.scheduled)
        mScheduledTextView = view.findViewById(R.id.scheduled_text)

        mMainAmountTextView = view.findViewById(R.id.main_amount)
        mFundButton = view.findViewById(R.id.fund)

        mUserGreetingTextView = view.findViewById(R.id.user_greeting)
    }

    override fun setUp(view: View) {
        mPresenter.onViewInitialized()
        mPresenter.loadWallet()

        eventBus.observe(UpdateWallet::class.java)
                .delay(1L, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter.loadCachedWallet()
                }

        mAirTimeDataImageView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(AirTimeFragment.newInstance())
        }

        mAirTimeDataTextView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(AirTimeFragment.newInstance())
        }

        mRequestMoneyImageView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(RequestFragment.newInstance())
        }

        mRequestMoneyTextView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(RequestFragment.newInstance())
        }

        mSendMoneyImageView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(SendMoneyFragment.newInstance())
        }

        mSendMoneyTextView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(SendMoneyFragment.newInstance())
        }

        mNotificationsImageView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(ProfileFragment.newInstance())
        }

        mFundButton.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(FundFragment.newInstance())
        }

        mCashOutImageView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(CashOutFragment.newInstance())
        }

        mCashOutTextView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(CashOutFragment.newInstance())
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView(currentUser: User?) {
        mUserGreetingTextView.text = "Hi ${currentUser?.username!!}"
    }

    override fun onGetWalletFailed(response: Response?) {
        show(response?.message!!, true)
    }

    override fun onGetWalletSuccessful(wallet: Wallet) {
        mMainAmountTextView.text = String.format("₦%,.2f", wallet.balance.toFloat())
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

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
