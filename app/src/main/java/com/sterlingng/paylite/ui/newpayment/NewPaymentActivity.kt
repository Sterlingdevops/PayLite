package com.sterlingng.paylite.ui.newpayment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.SendMoneyRequest
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import io.reactivex.disposables.CompositeDisposable
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

interface NewPaymentMvpContract<V : NewPaymentMvpView> : MvpPresenter<V> {
    fun loadCachedWallet()
}

interface NewPaymentMvpView : MvpView {
    fun initView(wallet: Wallet?)
}

class NewPaymentPresenter<V : NewPaymentMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), NewPaymentMvpContract<V> {

    override fun loadCachedWallet() {
        mvpView.initView(dataManager.getWallet())
    }
}

class NewPaymentActivity : BaseActivity(), NewPaymentMvpView {

    @Inject
    lateinit var mPresenter: NewPaymentMvpContract<NewPaymentMvpView>

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mRecipientNameEditText: EditText
    private lateinit var mPhoneEmailEditText: EditText
    private lateinit var mBalanceTextView: TextView

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_payment)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        next = findViewById(R.id.next)

        mRecipientNameEditText = findViewById(R.id.recipient_name)
        mPhoneEmailEditText = findViewById(R.id.phone_email)
        mBalanceTextView = findViewById(R.id.balance)
    }

    override fun setUp() {
        mPresenter.loadCachedWallet()

        exit.setOnClickListener {
            onBackPressed()
        }

        next.setOnClickListener {
            if (mPhoneEmailEditText.text.isEmpty() || mRecipientNameEditText.text.isEmpty()) {
                show("The fields are required and cannot be empty", true)
                return@setOnClickListener
            }

            val intent = NewPaymentAmountActivity.getStartIntent(this)

            val request = SendMoneyRequest()
            request.rcpt = mPhoneEmailEditText.text.toString()
            request.channel = "Android Application"

            intent.putExtra(REQUEST, request)
            startActivity(intent)
        }
    }

    override fun initView(wallet: Wallet?) {
        mBalanceTextView.text = String.format("Balance ₦%,.2f", wallet?.balance?.toFloat())
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        const val REQUEST = "NewPaymentActivity.REQUEST"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, NewPaymentActivity::class.java)
        }
    }
}