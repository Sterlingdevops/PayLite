package com.sterlingng.paylite.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.transactions.categories.CategoriesFragment
import com.sterlingng.paylite.utils.CustomPagerAdapter
import com.sterlingng.views.CustomViewPager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TransactionsFragment : BaseFragment(), TransactionsMvpView {

    @Inject
    lateinit var mPresenter: TransactionsMvpContract<TransactionsMvpView>

    lateinit var mPagerAdapter: CustomPagerAdapter

    private lateinit var disposable: Disposable

    private lateinit var mBalanceTextView: TextView
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

        mPagerAdapter.addFragment(CategoriesFragment.newInstance("ALL"), "ALL")
        mPagerAdapter.addFragment(CategoriesFragment.newInstance("IN"), "IN")
        mPagerAdapter.addFragment(CategoriesFragment.newInstance("OUT"), "OUT")

        mViewPager.isPagingEnabled = true
        mViewPager.adapter = mPagerAdapter
        mViewPager.offscreenPageLimit = 3

        mSmartTabLayout.setViewPager(mViewPager)

        disposable = Observable.timer(1L, TimeUnit.SECONDS)
                .repeat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mPresenter.onViewInitialized()
                }
    }

    override fun initView(wallet: Wallet) {
        mBalanceTextView.text = String.format("₦%,.2f", wallet.balance.toFloat())
    }

    override fun bindViews(view: View) {
        mViewPager = view.findViewById(R.id.viewpager)
        mBalanceTextView = view.findViewById(R.id.balance)
        mSmartTabLayout = view.findViewById(R.id.viewpagertab)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun onDetach() {
        super.onDetach()
        disposable.dispose()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
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
