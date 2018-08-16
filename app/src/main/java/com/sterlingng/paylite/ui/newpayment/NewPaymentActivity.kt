package com.sterlingng.paylite.ui.newpayment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import io.reactivex.disposables.CompositeDisposable
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

interface NewPaymentMvpContract<V : NewPaymentMvpView> : MvpPresenter<V>

interface NewPaymentMvpView : MvpView

class NewPaymentPresenter<V : NewPaymentMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), NewPaymentMvpContract<V>

class NewPaymentActivity : BaseActivity(), NewPaymentMvpView {

    @Inject
    lateinit var mPresenter: NewPaymentMvpContract<NewPaymentMvpView>

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mRecipientNameTextView: TextView
    private lateinit var mPhoneEmailTextView: TextView

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

        mRecipientNameTextView = findViewById(R.id.recipient_name)
        mPhoneEmailTextView = findViewById(R.id.phone_email)
    }

    override fun setUp() {
        exit.setOnClickListener {
            onBackPressed()
        }

        next.setOnClickListener {
            startActivity(NewPaymentAmountActivity.getStartIntent(this))
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, NewPaymentActivity::class.java)
        }
    }
}