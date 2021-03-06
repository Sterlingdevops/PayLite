package com.sterlingng.paylite.ui.transactions.paymentcategory

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.PaymentCategory
import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.data.model.UpdateTransaction
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.utils.SpacesItemDecoration
import javax.inject.Inject

class PaymentCategoriesFragment : BaseFragment(), PaymentCategoriesMvpView {

    @Inject
    lateinit var mPresenter: PaymentCategoriesMvpContract<PaymentCategoriesMvpView>

    private lateinit var mGridLayoutManager: GridLayoutManager

    @Inject
    lateinit var mPaymentCategoriesAdapter: PaymentCategoriesAdapter

    @Inject
    lateinit var eventBus: EventBus

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var category: String
    private lateinit var exit: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_payment_categories, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mPresenter.onViewInitialized()
        mPaymentCategoriesAdapter.mRecyclerViewClickListener = this

        mGridLayoutManager = GridLayoutManager(baseActivity, 3)
        mRecyclerView.addItemDecoration(SpacesItemDecoration(1))
        mRecyclerView.adapter = mPaymentCategoriesAdapter
        mRecyclerView.layoutManager = mGridLayoutManager
    }

    override fun initView(paymentCategory: ArrayList<PaymentCategory>) {
        mPaymentCategoriesAdapter.add(paymentCategory)
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mRecyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {
        mPaymentCategoriesAdapter.toggleSelection(position)
        category = mPaymentCategoriesAdapter.get(position).name

        val transaction = arguments?.getParcelable<Transaction>(TRANSACTION)!!
        val data = HashMap<String, Any>()
        data["TransactionID"] = transaction.id
        data["Category"] = category
        transaction.category = category
        mPresenter.updateTransactionCategory(data, transaction)
    }


    override fun logout() {
        baseActivity.logout()
    }

    companion object {
        private const val TRANSACTION = "PaymentCategoriesFragment.TRANSACTION"

        @JvmOverloads
        fun newInstance(transaction: Transaction = Transaction()): PaymentCategoriesFragment {
            val fragment = PaymentCategoriesFragment()
            val args = Bundle()
            args.putParcelable(TRANSACTION, transaction)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onUpdateTransactionCategorySuccessful() {
        eventBus.post(UpdateTransaction())
        show("Transaction category updated", true)
        (baseActivity as DashboardActivity).mNavController.popFragment()
    }

    override fun onUpdateTransactionCategoryFailed() {
        show("An error occurred", true)
    }
}
