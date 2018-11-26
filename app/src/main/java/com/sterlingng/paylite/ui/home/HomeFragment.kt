package com.sterlingng.paylite.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.MenuItem
import com.sterlingng.paylite.data.model.UpdateWallet
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.airtime.AirTimeFragment
import com.sterlingng.paylite.ui.authpin.AuthPinFragment
import com.sterlingng.paylite.ui.authpin.OpenPinMode
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.getcash.GetCashFragment
import com.sterlingng.paylite.ui.notifications.NotificationsFragment
import com.sterlingng.paylite.ui.payment.PaymentFragment
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

    @Inject
    lateinit var gridLayoutManager: GridLayoutManager

    private lateinit var mFundButton: ImageView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mListModeImageView: ImageView
    private lateinit var mMainAmountTextView: TextView
    private lateinit var mFundWalletTextView: TextView
    private lateinit var mUserGreetingTextView: TextView
    private lateinit var mNotificationsImageView: ImageView
    private lateinit var mGridMenuItemsAdapter: MenuItemsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mFundButton = view.findViewById(R.id.fund_outline)
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mFundWalletTextView = view.findViewById(R.id.fund_wallet)
        mMainAmountTextView = view.findViewById(R.id.main_amount)
        mListModeImageView = view.findViewById(R.id.list_mode_icon)
        mUserGreetingTextView = view.findViewById(R.id.user_greeting)
        mNotificationsImageView = view.findViewById(R.id.notifications_icon)
    }

    @SuppressLint("CheckResult")
    override fun setUp(view: View) {
        mGridMenuItemsAdapter = MenuItemsAdapter(baseActivity, MenuItemsAdapter.Mode.GRID)
        mRecyclerView.addItemDecoration(SpacesItemDecoration(0))
        mGridMenuItemsAdapter.mRecyclerViewClickListener = this
        mRecyclerView.layoutManager = gridLayoutManager
        mRecyclerView.adapter = mGridMenuItemsAdapter

        mPresenter.onViewInitialized()
        mPresenter.loadCachedWallet()
        mPresenter.loadWallet()

        eventBus.observe(UpdateWallet::class.java)
                .delay(1L, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter.loadCachedWallet()
                }

        mFundWalletTextView.setOnClickListener {
            when {
                mPresenter.getAuthPin() -> (baseActivity as DashboardActivity).mNavController.pushFragment(PaymentFragment.newInstance())
                else -> (baseActivity as DashboardActivity).mNavController.pushFragment(AuthPinFragment.newInstance(OpenPinMode.ENTER_NEW.name, openFund = true))
            }
        }

        mFundButton.setOnClickListener {
            when {
                mPresenter.getAuthPin() -> (baseActivity as DashboardActivity).mNavController.pushFragment(PaymentFragment.newInstance())
                else -> (baseActivity as DashboardActivity).mNavController.pushFragment(AuthPinFragment.newInstance(OpenPinMode.ENTER_NEW.name, openFund = true))
            }
        }

        mNotificationsImageView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(NotificationsFragment.newInstance())
        }
    }

    override fun onGetWalletSuccessful(wallet: Wallet) {
        mMainAmountTextView.text = String.format("₦%,.0f", wallet.balance.toFloat())
    }

    override fun onGetWalletFailed() {

    }

    @SuppressLint("SetTextI18n")
    override fun initView(currentUser: User?, mockMenuItems: ArrayList<MenuItem>) {
        mUserGreetingTextView.text = "Welcome ${currentUser?.firstName!!}"
        mGridMenuItemsAdapter.items = mockMenuItems
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
