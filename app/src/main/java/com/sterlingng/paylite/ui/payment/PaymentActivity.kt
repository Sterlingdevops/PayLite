package com.sterlingng.paylite.ui.payment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.PaymentMethod
import com.sterlingng.paylite.ui.base.BaseActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class PaymentActivity : BaseActivity(), PaymentMvpView, PaymentMethodsAdapter.OnRetryClicked {

    @Inject
    lateinit var mPresenter: PaymentMvpContract<PaymentMvpView>

    private lateinit var exit: ImageView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mPaymentMethodsAdapter: PaymentMethodsAdapter

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mDividerItemDecoration: DividerItemDecoration

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        mRecyclerView = findViewById(R.id.recyclerView)
    }

    override fun setUp() {
        exit.setOnClickListener {
            onBackPressed()
        }

        mPaymentMethodsAdapter = PaymentMethodsAdapter(this)
        mPaymentMethodsAdapter.mRecyclerViewClickListener = this

        mRecyclerView.adapter = mPaymentMethodsAdapter
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.addItemDecoration(mDividerItemDecoration)

        mPresenter.loadMockPaymentMethods()
    }

    override fun updatePaymentMethods(it: ArrayList<PaymentMethod>) {
        mPaymentMethodsAdapter.add(it)
    }

    override fun onRetryClicked() {
        mPresenter.loadMockPaymentMethods()
    }

    override fun recyclerViewListClicked(v: View, position: Int) {
        mPaymentMethodsAdapter.toggleSelection(position)
    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, PaymentActivity::class.java)
        }
    }
}
