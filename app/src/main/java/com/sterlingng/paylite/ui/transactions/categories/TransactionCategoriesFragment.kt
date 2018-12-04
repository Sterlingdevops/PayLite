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
import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.data.model.UpdateTransaction
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.transactions.detail.TransactionDetailFragment
import com.sterlingng.paylite.utils.Log
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
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

    private lateinit var monthsAdapter: MonthsAdapter
    private val months = ArrayList<String>()

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mMonthsRecyclerView: RecyclerView
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transaction_type, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    @SuppressLint("CheckResult")
    override fun setUp(view: View) {
        mTransactionCategoriesAdapter.mRecyclerViewClickListener = this

        recyclerView.adapter = mTransactionCategoriesAdapter
        recyclerView.layoutManager = mLinearLayoutManager
        recyclerView.addItemDecoration(StickyRecyclerHeadersDecoration(mTransactionCategoriesAdapter))

        monthsAdapter = MonthsAdapter(baseActivity)
        monthsAdapter.mRecyclerViewClickListener = this
        mMonthsRecyclerView.adapter = monthsAdapter
        recyclerView.layoutManager = LinearLayoutManager(baseActivity, LinearLayoutManager.VERTICAL, false)

        eventBus.observe(UpdateTransaction::class.java)
                .delay(1L, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter.loadTransactions()
                    mSwipeRefreshLayout.isRefreshing = true
                }

        mSwipeRefreshLayout.setOnRefreshListener {
            mPresenter.loadTransactions()
        }

        mPresenter.onViewInitialized()
    }

    override fun updateCategories(it: ArrayList<Transaction>) {
        mTransactionCategoriesAdapter.add(it)
        recyclerView.scrollToPosition(0)
    }

    override fun bindViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefresh)
        mMonthsRecyclerView = view.findViewById(R.id.monthsRecyclerView)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {
        when (v.id) {
            R.id.month -> {
                Log.d(months[position])
            }
            else -> {
                (baseActivity as DashboardActivity)
                        .mNavController
                        .pushFragment(TransactionDetailFragment.newInstance(mTransactionCategoriesAdapter.get(position), position))
            }
        }
    }

    override fun initView(transactions: ArrayList<Transaction>) {
        val transactionType = arguments?.getString(TYPE)!!
        val newTransactions: ArrayList<Transaction>

        newTransactions = when (transactionType) {
            "IN" -> transactions.filter { it.credit == "11" } as ArrayList<Transaction>
            "OUT" -> transactions.filter { it.credit == "00" } as ArrayList<Transaction>
            else -> transactions
        }

        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val monthFormat = SimpleDateFormat("MMM", Locale.ENGLISH)
        months += newTransactions.map { monthFormat.format(formatter.parse(it.date)) }.toSortedSet()

        monthsAdapter.items += months
        mTransactionCategoriesAdapter.add(newTransactions)
        recyclerView.scrollToPosition(0)
    }

    override fun onGetUserTransactionsFailed() {
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun onGetUserTransactionsSuccessful(transactions: ArrayList<Transaction>) {
        val transactionType = arguments?.getString(TYPE)!!
        val newTransactions: ArrayList<Transaction>

        newTransactions = when (transactionType) {
            "IN" -> transactions.filter { it.credit == "11" } as ArrayList<Transaction>
            "OUT" -> transactions.filter { it.credit == "00" } as ArrayList<Transaction>
            else -> transactions
        }

        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        val monthFormat = SimpleDateFormat("MMM", Locale.ENGLISH)
        months += newTransactions.map { monthFormat.format(formatter.parse(it.date)) }.toSortedSet()

        monthsAdapter.items += months

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
