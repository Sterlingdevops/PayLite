package com.sterlingng.paylite.ui.send

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Contact
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import com.sterlingng.paylite.ui.newpayment.NewPaymentActivity
import com.sterlingng.views.NoScrollingLinearLayoutManager
import io.reactivex.disposables.CompositeDisposable
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
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

class SendMoneyActivity : BaseActivity(), SendMoneyMvpView, ContactsAdapter.OnRetryClicked {

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

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_money)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun updateContacts(it: ArrayList<Contact>) {
        mContactsAdapter.add(it)
        mRecentAdapter.add(it)
    }

    override fun setUp() {

        exit.setOnClickListener {
            onBackPressed()
        }

        // Contact

        mContactsAdapter = ContactsAdapter(this, 0)
        mContactsLinearLayoutManager = NoScrollingLinearLayoutManager(this)
        mContactsLinearLayoutManager.orientation = RecyclerView.HORIZONTAL

        mContactsAdapter.onRetryClickedListener = this
        mContactsRecyclerView.adapter = mContactsAdapter
        mContactsAdapter.mRecyclerViewClickListener = this
        mContactsRecyclerView.layoutManager = mContactsLinearLayoutManager
        mContactsRecyclerView.scrollToPosition(0)

        // Recent

        mRecentAdapter = ContactsAdapter(this, 0)
        mRecentLinearLayoutManager = NoScrollingLinearLayoutManager(this)
        mRecentLinearLayoutManager.orientation = RecyclerView.HORIZONTAL

        mRecentRecyclerView.adapter = mRecentAdapter
        mRecentAdapter.onRetryClickedListener = this
        mRecentAdapter.mRecyclerViewClickListener = this
        mRecentRecyclerView.layoutManager = mRecentLinearLayoutManager
        mRecentRecyclerView.scrollToPosition(0)

        mNewPaymentTextView.setOnClickListener {
            startActivity(NewPaymentActivity.getStartIntent(this))
        }

        mNewPaymentRefTextView.setOnClickListener {
            startActivity(NewPaymentActivity.getStartIntent(this))
        }

        mScheduledRefTextView.setOnClickListener {

        }

        mScheduledTextView.setOnClickListener {

        }

        mPresenter.loadCachedWallet()
        mPresenter.loadContacts()
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        mContactsRecyclerView = findViewById(R.id.recyclerView)
        mRecentRecyclerView = findViewById(R.id.recyclerView2)

        mNewPaymentTextView = findViewById(R.id.new_payment)
        mNewPaymentRefTextView = findViewById(R.id.new_payment_ref)

        mScheduledTextView = findViewById(R.id.scheduled_payments)
        mScheduledRefTextView = findViewById(R.id.scheduled_payments_ref)

        mBalanceTextView = findViewById(R.id.balance)
    }

    override fun initView(wallet: Wallet?) {
        mBalanceTextView.text = String.format("Balance ₦%,.2f", wallet?.balance?.toFloat())
    }

    override fun onRetryClicked() {

    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, SendMoneyActivity::class.java)
        }
    }
}