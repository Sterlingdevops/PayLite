package com.sterlingng.paylite.ui.transactions.categories


import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject

class CategoriesFragment : BaseFragment(), CategoriesMvpView {

    @Inject
    lateinit var mPresenter: CategoriesMvpContract<CategoriesMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mDividerItemDecoration: DividerItemDecoration

    @Inject
    lateinit var mCategoriesAdapter: CategoriesAdapter

    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        mCategoriesAdapter.mRecyclerViewClickListener = this

        recyclerView.adapter = mCategoriesAdapter
        recyclerView.layoutManager = mLinearLayoutManager
        recyclerView.addItemDecoration(mDividerItemDecoration)

        mPresenter.loadCreditCards()
    }

    override fun updateCategories(it: Collection<Transaction>) {
        mCategoriesAdapter.addTransactions(it)
        recyclerView.scrollToPosition(0)
    }

    override fun bindViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): CategoriesFragment {
            val fragment = CategoriesFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
