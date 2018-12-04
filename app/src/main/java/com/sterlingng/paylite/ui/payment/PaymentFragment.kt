package com.sterlingng.paylite.ui.payment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.PaymentMethod
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.fund.FundFragment
import com.sterlingng.paylite.utils.then
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

class PaymentFragment : BaseFragment(), PaymentMvpView, PaymentMethodsAdapter.OnAddPaymentMethod, PaymentMethodsAdapter.OnDeletePaymentMethod {

    @Inject
    lateinit var mPresenter: PaymentMvpContract<PaymentMvpView>

    private lateinit var add: TextView
    private lateinit var exit: ImageView
    private lateinit var mAddImageView: ImageView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAddBackImageView: CircleImageView
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
        add = view.findViewById(R.id.add)
        exit = view.findViewById(R.id.exit)
        mAddImageView = view.findViewById(R.id.plus)
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mAddBackImageView = view.findViewById(R.id.circleImageView)
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        add.setOnClickListener {
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(FundFragment.newInstance())
        }

        mPaymentMethodsAdapter = PaymentMethodsAdapter(baseActivity)
        mPaymentMethodsAdapter.mRecyclerViewClickListener = this
        mPaymentMethodsAdapter.onAddPaymentMethodListener = this
        mPaymentMethodsAdapter.onDeletePaymentMethodListener = this

        mRecyclerView.adapter = mPaymentMethodsAdapter
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.addItemDecoration(mDividerItemDecoration)

        mPresenter.loadPaymentMethods()
    }

    override fun updatePaymentMethods(it: ArrayList<PaymentMethod>) {
        mPaymentMethodsAdapter.add(it)
        add.visibility = (mPaymentMethodsAdapter.paymentMethods.size == 0) then View.GONE ?: View.VISIBLE
        mAddImageView.visibility = (mPaymentMethodsAdapter.paymentMethods.size == 0) then View.GONE ?: View.VISIBLE
        mAddBackImageView.visibility = (mPaymentMethodsAdapter.paymentMethods.size == 0) then View.GONE ?: View.VISIBLE
    }

    override fun onAddPaymentMethodClicked() {
        (baseActivity as DashboardActivity)
                .mNavController
                .pushFragment(FundFragment.newInstance())
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {
        when (v.id) {
            R.id.payment_method -> (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(FundFragment.newInstance(mPaymentMethodsAdapter.get(position)))
        }
    }

    override fun onPaymentMethodDeleted(adapterPosition: Int) {
        show("Delete ${mPaymentMethodsAdapter.get(adapterPosition)}", true)
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
