package com.sterlingng.paylite.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.sterlingng.views.NoScrollingGridLayoutManager
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
    lateinit var gridLayoutManager: NoScrollingGridLayoutManager

    private lateinit var mUserGreetingTextView: TextView
    private lateinit var mMainAmountTextView: TextView
    private lateinit var mFundButton: ImageView

    override fun onSaveInstanceState(outState: Bundle) {
        //No call for super(). Bug on API Level > 11.
    }

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
        mMainAmountTextView = view.findViewById(R.id.main_amount)
        mListModeImageView = view.findViewById(R.id.list_mode_icon)
        mUserGreetingTextView = view.findViewById(R.id.user_greeting)
        mNotificationsImageView = view.findViewById(R.id.notifications_icon)
    }

    @SuppressLint("CheckResult")
    override fun setUp(view: View) {
        mListMenuItemsAdapter = MenuItemsAdapter(baseActivity, MenuItemsAdapter.Mode.LIST)
        mGridMenuItemsAdapter = MenuItemsAdapter(baseActivity, MenuItemsAdapter.Mode.GRID)
        mGridMenuItemsAdapter.mRecyclerViewClickListener = this
        mListMenuItemsAdapter.mRecyclerViewClickListener = this
        mRecyclerView.addItemDecoration(SpacesItemDecoration(0))
        gridLayoutManager.spanCount = 2
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
    }

    override fun onGetWalletSuccessful(wallet: Wallet) {
        mMainAmountTextView.text = String.format("₦%,.2f", wallet.balance.toFloat())
    }

    override fun onGetWalletFailed(response: Response) {

    }

    @SuppressLint("SetTextI18n")
    override fun initView(currentUser: User?, mockMenuItems: ArrayList<MenuItem>) {
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
