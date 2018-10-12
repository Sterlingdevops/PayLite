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
import com.sterlingng.paylite.ui.authpin.AuthPinFragment
import com.sterlingng.paylite.ui.authpin.OpenPinMode
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.cashoutbank.CashOutFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.getcash.GetCashFragment
import com.sterlingng.paylite.ui.payment.PaymentFragment
import com.sterlingng.paylite.ui.profile.notifications.NotificationsFragment
import com.sterlingng.paylite.ui.request.RequestFragment
import com.sterlingng.paylite.ui.scheduled.ScheduledFragment
import com.sterlingng.paylite.ui.send.SendMoneyFragment
import com.sterlingng.paylite.ui.splitamount.SplitAmountFragment
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
    private lateinit var mSplitCostImageView: ImageView
    private lateinit var mUpComingImageView: ImageView
    private lateinit var mPayBillsImageView: ImageView
    private lateinit var mCashOutImageView: ImageView
    private lateinit var mGetCashImageView: ImageView

    private lateinit var mRequestMoneyTextView: TextView
    private lateinit var mAirTimeDataTextView: TextView
    private lateinit var mSendMoneyTextView: TextView
    private lateinit var mSplitCostTextView: TextView
    private lateinit var mUpComingTextView: TextView
    private lateinit var mPayBillsTextView: TextView
    private lateinit var mGetCashTextView: TextView
    private lateinit var mCashOutTextView: TextView

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

        mUpComingImageView = view.findViewById(R.id.upcoming)
        mUpComingTextView = view.findViewById(R.id.upcoming_text)

        mRequestMoneyImageView = view.findViewById(R.id.payment_request)
        mRequestMoneyTextView = view.findViewById(R.id.payment_request_text)

        mCashOutImageView = view.findViewById(R.id.cash_out)
        mCashOutTextView = view.findViewById(R.id.cash_out_text)

        mMainAmountTextView = view.findViewById(R.id.main_amount)
        mFundButton = view.findViewById(R.id.fund)

        mGetCashImageView = view.findViewById(R.id.get_cash)
        mGetCashTextView = view.findViewById(R.id.get_cash_text)

        mSplitCostImageView = view.findViewById(R.id.split_bill)
        mSplitCostTextView = view.findViewById(R.id.split_bill_text)

        mUserGreetingTextView = view.findViewById(R.id.user_greeting)
    }

    override fun setUp(view: View) {
        mPresenter.onViewInitialized()
        mPresenter.loadCachedWallet()
        mPresenter.loadWallet()
        hideKeyboard()

        eventBus.observe(UpdateWallet::class.java)
                .delay(1L, TimeUnit.MILLISECONDS)
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
            (baseActivity as DashboardActivity).mNavController.pushFragment(NotificationsFragment.newInstance())
        }

        mFundButton.setOnClickListener {
            when {
                mPresenter.getAuthPin() -> (baseActivity as DashboardActivity).mNavController.pushFragment(PaymentFragment.newInstance())
                else -> (baseActivity as DashboardActivity)
                        .mNavController
                        .pushFragment(AuthPinFragment.newInstance(OpenPinMode.ENTER_NEW.name))
            }
        }

        mCashOutImageView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(CashOutFragment.newInstance())
        }

        mCashOutTextView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(CashOutFragment.newInstance())
        }

        mSplitCostImageView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(SplitAmountFragment.newInstance())
        }

        mSplitCostTextView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(SplitAmountFragment.newInstance())
        }

        mGetCashImageView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(GetCashFragment.newInstance())
        }

        mGetCashTextView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(GetCashFragment.newInstance())
        }

        mUpComingImageView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(ScheduledFragment.newInstance())
        }

        mUpComingTextView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(ScheduledFragment.newInstance())
        }
    }

    override fun onGetWalletSuccessful(wallet: Wallet) {
        mMainAmountTextView.text = String.format("₦%,.2f", wallet.balance.toFloat())
    }

    override fun onGetWalletFailed(response: Response) {

    }

    @SuppressLint("SetTextI18n")
    override fun initView(currentUser: User?) {
        mUserGreetingTextView.text = "Hi ${currentUser?.firstName!!}"
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun logout() {
        baseActivity.logout()
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
