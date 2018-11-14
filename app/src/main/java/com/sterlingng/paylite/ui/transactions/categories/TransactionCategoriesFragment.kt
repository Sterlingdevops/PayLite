package com.sterlingng.paylite.ui.transactions.categories

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.data.model.UpdateTransaction
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.transactions.detail.TransactionDetailFragment
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TransactionCategoriesFragment : BaseFragment(), TransactionCategoriesMvpView {

    @Inject
    lateinit var mPresenter: TransactionCategoriesMvpContract<TransactionCategoriesMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mTransactionCategoriesAdapter: TransactionCategoriesAdapter

    @Inject
    lateinit var eventBus: EventBus

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transaction_type, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    @SuppressLint("CheckResult")
    override fun setUp(view: View) {
        mPresenter.onViewInitialized()
        mTransactionCategoriesAdapter.mRecyclerViewClickListener = this

        recyclerView.adapter = mTransactionCategoriesAdapter
        recyclerView.layoutManager = mLinearLayoutManager
        recyclerView.addItemDecoration(StickyRecyclerHeadersDecoration(mTransactionCategoriesAdapter))

        eventBus.observe(UpdateTransaction::class.java)
                .delay(1L, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    recyclerView.scrollToPosition(it.position)
                }

        mPresenter.loadTransactions()
        mSwipeRefreshLayout.isRefreshing = true

        mSwipeRefreshLayout.setOnRefreshListener {
            mPresenter.loadTransactions()
        }
    }

    override fun updateCategories(it: ArrayList<Transaction>) {
        mTransactionCategoriesAdapter.add(it)
        recyclerView.scrollToPosition(0)
    }

    override fun bindViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {
        (baseActivity as DashboardActivity)
                .mNavController
                .pushFragment(TransactionDetailFragment.newInstance(mTransactionCategoriesAdapter.get(position), position))
    }

    override fun initView(transactions: ArrayList<Transaction>) {
        val transactionType = arguments?.getString(TYPE)!!
        val newTransactions: ArrayList<Transaction>

        newTransactions = when (transactionType) {
            "IN" -> transactions.filter { it.credit == "11" } as ArrayList<Transaction>
            "OUT" -> transactions.filter { it.credit == "00" } as ArrayList<Transaction>
            else -> transactions
        }

        mTransactionCategoriesAdapter.add(newTransactions)
        recyclerView.scrollToPosition(0)
    }

    override fun onGetUserTransactionsFailed(response: Response) {
        mSwipeRefreshLayout.isRefreshing = false
        show("Failed getting transactions", true)
    }

    override fun onGetUserTransactionsSuccessful(transactions: ArrayList<Transaction>) {
        val transactionType = arguments?.getString(TYPE)!!
        val newTransactions: ArrayList<Transaction>

        newTransactions = when (transactionType) {
            "IN" -> transactions.filter { it.credit == "11" } as ArrayList<Transaction>
            "OUT" -> transactions.filter { it.credit == "00" } as ArrayList<Transaction>
            else -> transactions
        }

        mTransactionCategoriesAdapter.add(newTransactions)
        recyclerView.scrollToPosition(0)

        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun logout() {
        baseActivity.logout()
    }

    companion object {

        private const val TYPE = "TransactionCategoriesFragment.TYPE"

        fun newInstance(type: String): TransactionCategoriesFragment {
            val fragment = TransactionCategoriesFragment()
            val args = Bundle()
            args.putString(TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }
}
