package com.sterlingng.paylite.ui.transactions.categories

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.utils.Log
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CategoriesFragment : BaseFragment(), CategoriesMvpView, TransactionAdapter.OnCreateNewClicked {

    @Inject
    lateinit var mPresenter: CategoriesMvpContract<CategoriesMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mTransactionAdapter: TransactionAdapter

    lateinit var recyclerView: RecyclerView

    lateinit var disposable: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transaction_type, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        mPresenter.onViewInitialized()

        mTransactionAdapter.mRecyclerViewClickListener = this
        mTransactionAdapter.onCreateNewClickedListener = this

        recyclerView.adapter = mTransactionAdapter
        recyclerView.layoutManager = mLinearLayoutManager
        recyclerView.addItemDecoration(StickyRecyclerHeadersDecoration(mTransactionAdapter))

        mPresenter.loadTransactions()
        disposable = Observable.timer(60L, TimeUnit.SECONDS)
                .repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter.loadTransactions()
                }
    }

    override fun updateCategories(it: ArrayList<Transaction>) {
        mTransactionAdapter.add(it)
        recyclerView.scrollToPosition(0)
    }

    override fun bindViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    override fun initView(transactions: ArrayList<Transaction>) {
        val transactionType = arguments?.getString(TYPE)!!
        val newTransactions: ArrayList<Transaction>

        newTransactions = when (transactionType) {
            "IN" -> transactions.filter { it.credit == "11" } as ArrayList<Transaction>
            "OUT" -> transactions.filter { it.credit == "00" } as ArrayList<Transaction>
            else -> transactions
        }

        mTransactionAdapter.add(newTransactions)
        recyclerView.scrollToPosition(0)
    }

    override fun onGetUserTransactionsFailed(response: Response) {
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

        mTransactionAdapter.add(newTransactions)
        recyclerView.scrollToPosition(0)
    }

    override fun onCreateNew() {
        show("Loading transactions", true)
        mPresenter.loadTransactions()
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("CategoriesFragment::onDetach")
        disposable.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CategoriesFragment::onDestroy")
        disposable.dispose()
    }

    override fun logout() {
        baseActivity.logout()
    }

    companion object {

        private const val TYPE = "CategoriesFragment.TYPE"

        fun newInstance(type: String): CategoriesFragment {
            val fragment = CategoriesFragment()
            val args = Bundle()
            args.putString(TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }
}
