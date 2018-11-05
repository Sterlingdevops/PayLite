package com.sterlingng.paylite.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.*
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.airtime.AirTimeFragment
import com.sterlingng.paylite.ui.authpin.AuthPinFragment
import com.sterlingng.paylite.ui.authpin.OpenPinMode
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.getcash.GetCashFragment
import com.sterlingng.paylite.ui.payment.PaymentFragment
import com.sterlingng.paylite.ui.profile.notifications.NotificationsFragment
import com.sterlingng.paylite.ui.request.RequestFragment
import com.sterlingng.paylite.ui.scheduled.ScheduledFragment
import com.sterlingng.paylite.ui.send.SendMoneyFragment
import com.sterlingng.paylite.ui.splitamount.SplitAmountFragment
import com.sterlingng.paylite.utils.SpacesItemDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeMvpView {

    @Inject
    lateinit var mPresenter: HomeMvpContract<HomeMvpView>

    @Inject
    lateinit var eventBus: EventBus

    var listMode: Boolean = true

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mListModeImageView: ImageView
    private lateinit var mNotificationsImageView: ImageView
    private lateinit var mListMenuItemsAdapter: MenuItemsAdapter
    private lateinit var mGridMenuItemsAdapter: MenuItemsAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var gridLayoutManager: GridLayoutManager

//    private lateinit var mRequestMoneyImageView: ImageView
//    private lateinit var mAirTimeDataImageView: ImageView
//    private lateinit var mSendMoneyImageView: ImageView
//    private lateinit var mSplitCostImageView: ImageView
//    private lateinit var mUpComingImageView: ImageView
//    private lateinit var mPayBillsImageView: ImageView
//    private lateinit var mCashOutImageView: ImageView
//    private lateinit var mGetCashImageView: ImageView
//
//    private lateinit var mRequestMoneyTextView: TextView
//    private lateinit var mAirTimeDataTextView: TextView
//    private lateinit var mSendMoneyTextView: TextView
//    private lateinit var mSplitCostTextView: TextView
//    private lateinit var mUpComingTextView: TextView
//    private lateinit var mPayBillsTextView: TextView
//    private lateinit var mGetCashTextView: TextView
//    private lateinit var mCashOutTextView: TextView

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
        mFundButton = view.findViewById(R.id.fund)
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mMainAmountTextView = view.findViewById(R.id.main_amount)
        mListModeImageView = view.findViewById(R.id.list_mode_icon)
        mUserGreetingTextView = view.findViewById(R.id.user_greeting)
        mNotificationsImageView = view.findViewById(R.id.notifications_icon)

//        mAirTimeDataImageView = view.findViewById(R.id.airtime_data)
//        mAirTimeDataTextView = view.findViewById(R.id.airtime_data_text)
//
//        mPayBillsImageView = view.findViewById(R.id.bill_payment)
//        mPayBillsTextView = view.findViewById(R.id.pay_bills_text)
//
//        mSendMoneyImageView = view.findViewById(R.id.send_money)
//        mSendMoneyTextView = view.findViewById(R.id.send_money_text)
//
//        mUpComingImageView = view.findViewById(R.id.upcoming)
//        mUpComingTextView = view.findViewById(R.id.upcoming_text)
//
//        mRequestMoneyImageView = view.findViewById(R.id.payment_request)
//        mRequestMoneyTextView = view.findViewById(R.id.payment_request_text)
//
//        mCashOutImageView = view.findViewById(R.id.cash_out)
//        mCashOutTextView = view.findViewById(R.id.cash_out_text)

//        mGetCashImageView = view.findViewById(R.id.get_cash)
//        mGetCashTextView = view.findViewById(R.id.get_cash_text)
//
//        mSplitCostImageView = view.findViewById(R.id.split_bill)
//        mSplitCostTextView = view.findViewById(R.id.split_bill_text)
    }

    @SuppressLint("CheckResult")
    override fun setUp(view: View) {
        mListMenuItemsAdapter = MenuItemsAdapter(baseActivity, MenuItemsAdapter.Mode.LIST)
        mGridMenuItemsAdapter = MenuItemsAdapter(baseActivity, MenuItemsAdapter.Mode.GRID)
        mGridMenuItemsAdapter.mRecyclerViewClickListener = this
        mListMenuItemsAdapter.mRecyclerViewClickListener = this
        mRecyclerView.addItemDecoration(SpacesItemDecoration(0))
        mRecyclerView.layoutManager = gridLayoutManager
        mRecyclerView.adapter = mGridMenuItemsAdapter

        listMode = false

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

        eventBus.observe(OpenFundWallet::class.java)
                .delay(1L, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    (baseActivity as DashboardActivity).mNavController.pushFragment(PaymentFragment.newInstance())
                }

        mFundButton.setOnClickListener {
            when {
                mPresenter.getAuthPin() -> (baseActivity as DashboardActivity).mNavController.pushFragment(PaymentFragment.newInstance())
                else -> (baseActivity as DashboardActivity)
                        .mNavController
                        .pushFragment(AuthPinFragment.newInstance(OpenPinMode.ENTER_NEW.name, openFund = true))
            }
        }

        mNotificationsImageView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(NotificationsFragment.newInstance())
        }

        mListModeImageView.setOnClickListener {
            when (listMode) {
                true -> {
                    mListModeImageView.setImageDrawable(ContextCompat.getDrawable(baseActivity, R.drawable.icon_list))
                    mRecyclerView.layoutManager = gridLayoutManager
                    mRecyclerView.adapter = mGridMenuItemsAdapter
                    listMode = false
                }
                false -> {
                    mListModeImageView.setImageDrawable(ContextCompat.getDrawable(baseActivity, R.drawable.icon_grid))
                    mRecyclerView.layoutManager = linearLayoutManager
                    mRecyclerView.adapter = mListMenuItemsAdapter
                    listMode = true
                }
            }
        }

//        mAirTimeDataImageView.setOnClickListener {
//            (baseActivity as DashboardActivity).mNavController.pushFragment(AirTimeFragment.newInstance())
//        }
//
//        mAirTimeDataTextView.setOnClickListener {
//            (baseActivity as DashboardActivity).mNavController.pushFragment(AirTimeFragment.newInstance())
//        }
//
//        mRequestMoneyImageView.setOnClickListener {
//            (baseActivity as DashboardActivity).mNavController.pushFragment(RequestFragment.newInstance())
//        }
//
//        mRequestMoneyTextView.setOnClickListener {
//            (baseActivity as DashboardActivity).mNavController.pushFragment(RequestFragment.newInstance())
//        }
//
//        mSendMoneyImageView.setOnClickListener {
//            (baseActivity as DashboardActivity).mNavController.pushFragment(SendMoneyFragment.newInstance())
//        }
//
//        mSendMoneyTextView.setOnClickListener {
//            (baseActivity as DashboardActivity).mNavController.pushFragment(SendMoneyFragment.newInstance())
//        }
//
//        mCashOutImageView.setOnClickListener {
//            (baseActivity as DashboardActivity).mNavController.pushFragment(CashOutFragment.newInstance())
//        }
//
//        mCashOutTextView.setOnClickListener {
//            (baseActivity as DashboardActivity).mNavController.pushFragment(CashOutFragment.newInstance())
//        }
//
//        mSplitCostImageView.setOnClickListener {
//            (baseActivity as DashboardActivity).mNavController.pushFragment(SplitAmountFragment.newInstance())
//        }
//
//        mSplitCostTextView.setOnClickListener {
//            (baseActivity as DashboardActivity).mNavController.pushFragment(SplitAmountFragment.newInstance())
//        }
//
//        mGetCashImageView.setOnClickListener {
//            (baseActivity as DashboardActivity).mNavController.pushFragment(GetCashFragment.newInstance())
//        }
//
//        mGetCashTextView.setOnClickListener {
//            (baseActivity as DashboardActivity).mNavController.pushFragment(GetCashFragment.newInstance())
//        }
//
//        mUpComingImageView.setOnClickListener {
//            (baseActivity as DashboardActivity).mNavController.pushFragment(ScheduledFragment.newInstance())
//        }
//
//        mUpComingTextView.setOnClickListener {
//            (baseActivity as DashboardActivity).mNavController.pushFragment(ScheduledFragment.newInstance())
//        }
    }

    override fun onGetWalletSuccessful(wallet: Wallet) {
        mMainAmountTextView.text = String.format("₦%,.2f", wallet.balance.toFloat())
    }

    override fun onGetWalletFailed(response: Response) {

    }

    @SuppressLint("SetTextI18n")
    override fun initView(currentUser: User?, mockMenuItems: ArrayList<MenuItem>) {
        mUserGreetingTextView.text = "Hi ${currentUser?.firstName!!}"
        mGridMenuItemsAdapter.items = mockMenuItems
        mListMenuItemsAdapter.items = mockMenuItems
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {
        when (position) {
            0 -> {
                (baseActivity as DashboardActivity).mNavController.pushFragment(SendMoneyFragment.newInstance())
            }
            1 -> {
                (baseActivity as DashboardActivity).mNavController.pushFragment(RequestFragment.newInstance())
            }
            2 -> {
                (baseActivity as DashboardActivity).mNavController.pushFragment(GetCashFragment.newInstance())
            }
            3 -> {
                (baseActivity as DashboardActivity).mNavController.pushFragment(SplitAmountFragment.newInstance())
            }
            4 -> {
                (baseActivity as DashboardActivity).mNavController.pushFragment(ScheduledFragment.newInstance())
            }
            5 -> {
                (baseActivity as DashboardActivity).mNavController.pushFragment(AirTimeFragment.newInstance())
            }
        }
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
