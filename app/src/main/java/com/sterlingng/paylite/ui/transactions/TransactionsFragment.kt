package com.sterlingng.paylite.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.transactions.categories.CategoriesFragment
import com.sterlingng.paylite.utils.CustomPagerAdapter
import com.sterlingng.views.CustomViewPager
import javax.inject.Inject

class TransactionsFragment : BaseFragment(), TransactionsMvpView {

    @Inject
    lateinit var mPresenter: TransactionsMvpContract<TransactionsMvpView>

    lateinit var mPagerAdapter: CustomPagerAdapter

    private lateinit var mViewPager: CustomViewPager
    private lateinit var mSmartTabLayout: SmartTabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transactions, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        mPagerAdapter = CustomPagerAdapter(childFragmentManager)

        mPagerAdapter.addFragment(CategoriesFragment.newInstance(), "IN")
        mPagerAdapter.addFragment(CategoriesFragment.newInstance(), "OUT")
        mPagerAdapter.addFragment(CategoriesFragment.newInstance(), "All")

        mViewPager.isPagingEnabled = true
        mViewPager.adapter = mPagerAdapter

        mSmartTabLayout.setViewPager(mViewPager)

        mPresenter.getUserTransactions()
    }

    override fun bindViews(view: View) {
        mViewPager = view.findViewById(R.id.viewpager)
        mSmartTabLayout = view.findViewById(R.id.viewpagertab)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    override fun onGetUserTransactionsFailed(response: Response) {

    }

    override fun onGetUserTransactionsSuccessful(response: Response) {

    }

    companion object {

        fun newInstance(): TransactionsFragment {
            val fragment = TransactionsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
