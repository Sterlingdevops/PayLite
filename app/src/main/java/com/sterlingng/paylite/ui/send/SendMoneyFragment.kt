package com.sterlingng.paylite.ui.send

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Contact
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.newpayment.NewPaymentFragment
import com.sterlingng.views.NoScrollingLinearLayoutManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

interface SendMoneyMvpContract<V : SendMoneyMvpView> : MvpPresenter<V> {
    fun loadContacts()
    fun loadCachedWallet()
}

interface SendMoneyMvpView : MvpView {
    fun updateContacts(it: ArrayList<Contact>)
    fun initView(wallet: Wallet?)
}

class SendMoneyPresenter<V : SendMoneyMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), SendMoneyMvpContract<V> {

    override fun loadCachedWallet() {
        mvpView.initView(dataManager.getWallet())
    }

    override fun loadContacts() {
        mvpView.updateContacts(dataManager.mockContacts())
    }
}

class SendMoneyFragment : BaseFragment(), SendMoneyMvpView, ContactsAdapter.OnRetryClicked {

    @Inject
    lateinit var mPresenter: SendMoneyMvpContract<SendMoneyMvpView>

    private lateinit var exit: ImageView

    private lateinit var mNewPaymentTextView: TextView
    private lateinit var mNewPaymentRefTextView: TextView

    private lateinit var mScheduledTextView: TextView
    private lateinit var mScheduledRefTextView: TextView

    private lateinit var mContactsAdapter: ContactsAdapter
    private lateinit var mRecentAdapter: ContactsAdapter

    private lateinit var mContactsRecyclerView: RecyclerView
    private lateinit var mRecentRecyclerView: RecyclerView

    private lateinit var mContactsLinearLayoutManager: NoScrollingLinearLayoutManager
    private lateinit var mRecentLinearLayoutManager: NoScrollingLinearLayoutManager

    private lateinit var mBalanceTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_send_money, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun updateContacts(it: ArrayList<Contact>) {
        mContactsAdapter.add(it)
        mRecentAdapter.add(it)
    }

    override fun setUp(view: View) {

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        // Contact

        mContactsAdapter = ContactsAdapter(baseActivity, 0)
        mContactsLinearLayoutManager = NoScrollingLinearLayoutManager(baseActivity)
        mContactsLinearLayoutManager.orientation = RecyclerView.HORIZONTAL

        mContactsAdapter.onRetryClickedListener = this
        mContactsRecyclerView.adapter = mContactsAdapter
        mContactsAdapter.mRecyclerViewClickListener = this
        mContactsRecyclerView.layoutManager = mContactsLinearLayoutManager
        mContactsRecyclerView.scrollToPosition(0)

        // Recent

        mRecentAdapter = ContactsAdapter(baseActivity, 0)
        mRecentLinearLayoutManager = NoScrollingLinearLayoutManager(baseActivity)
        mRecentLinearLayoutManager.orientation = RecyclerView.HORIZONTAL

        mRecentRecyclerView.adapter = mRecentAdapter
        mRecentAdapter.onRetryClickedListener = this
        mRecentAdapter.mRecyclerViewClickListener = this
        mRecentRecyclerView.layoutManager = mRecentLinearLayoutManager
        mRecentRecyclerView.scrollToPosition(0)

        mNewPaymentTextView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(NewPaymentFragment.newInstance())
        }

        mNewPaymentRefTextView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(NewPaymentFragment.newInstance())
        }

        mScheduledRefTextView.setOnClickListener {

        }

        mScheduledTextView.setOnClickListener {

        }

        mPresenter.loadCachedWallet()
        mPresenter.loadContacts()
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mContactsRecyclerView = view.findViewById(R.id.recyclerView)
        mRecentRecyclerView = view.findViewById(R.id.recyclerView2)

        mNewPaymentTextView = view.findViewById(R.id.new_payment)
        mNewPaymentRefTextView = view.findViewById(R.id.new_payment_ref)

        mScheduledTextView = view.findViewById(R.id.scheduled_payments)
        mScheduledRefTextView = view.findViewById(R.id.scheduled_payments_ref)

        mBalanceTextView = view.findViewById(R.id.balance)
    }

    override fun initView(wallet: Wallet?) {
        mBalanceTextView.text = String.format("Balance ₦%,.2f", wallet?.balance?.toFloat())
    }

    override fun onRetryClicked() {

    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): SendMoneyFragment {
            val fragment = SendMoneyFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}