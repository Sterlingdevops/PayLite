package com.sterlingng.paylite.ui.transactions.categories


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.reflect.TypeToken
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.utils.AppUtils.gson
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import javax.inject.Inject

class CategoriesFragment : BaseFragment(), CategoriesMvpView {

    override fun onGetUserTransactionsFailed(response: Response) {
        show("Failed getting transactions", true)
    }

    override fun onGetUserTransactionsSuccessful(response: Response) {
        val transactionType = arguments?.getString(TYPE)!!

        val type = object : TypeToken<ArrayList<Transaction>>() {}.type
        var transactions = gson.fromJson<ArrayList<Transaction>>(gson.toJson(response.data), type)
        transactions.sortByDescending { it.id }

        when (transactionType) {
            "IN" -> transactions = transactions.filter { it.type == "11" }.toList() as ArrayList<Transaction>
            "OUT" -> transactions = transactions.filter { it.type == "00" }.toList() as ArrayList<Transaction>
        }

        mTransactionAdapter.add(transactions)
        recyclerView.scrollToPosition(0)
    }

    @Inject
    lateinit var mPresenter: CategoriesMvpContract<CategoriesMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mTransactionAdapter: TransactionAdapter

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transaction_type, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        mTransactionAdapter.mRecyclerViewClickListener = this

        recyclerView.adapter = mTransactionAdapter
        recyclerView.layoutManager = mLinearLayoutManager
        recyclerView.addItemDecoration(StickyRecyclerHeadersDecoration(mTransactionAdapter))

        mPresenter.loadTransactions()
    }

    override fun updateCategories(it: Collection<Transaction>) {
        mTransactionAdapter.add(it)
        recyclerView.scrollToPosition(0)
    }

    override fun bindViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

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
