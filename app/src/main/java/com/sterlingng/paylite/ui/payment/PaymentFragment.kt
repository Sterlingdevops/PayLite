package com.sterlingng.paylite.ui.payment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.PaymentMethod
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject

class PaymentFragment : BaseFragment(), PaymentMvpView, PaymentMethodsAdapter.OnRetryClicked {

    @Inject
    lateinit var mPresenter: PaymentMvpContract<PaymentMvpView>

    private lateinit var exit: ImageView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mPaymentMethodsAdapter: PaymentMethodsAdapter

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mDividerItemDecoration: DividerItemDecoration

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_payment, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mRecyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mPaymentMethodsAdapter = PaymentMethodsAdapter(baseActivity)
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

        fun newInstance(): PaymentFragment {
            val fragment = PaymentFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
