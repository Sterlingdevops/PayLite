package com.sterlingng.paylite.ui.transactions

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.transactions.categories.CategoriesFragment
import com.sterlingng.paylite.utils.CustomPagerAdapter
import com.sterlingng.paylite.utils.widgets.CustomViewPager
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class TransactionsActivity : BaseActivity(), TransactionsMvpView {

    @Inject
    lateinit var mPresenter: TransactionsMvpContract<TransactionsMvpView>

    @Inject
    lateinit var mPagerAdapter: CustomPagerAdapter

    private lateinit var mViewPager: CustomViewPager
    private lateinit var mSmartTabLayout: SmartTabLayout

    private lateinit var exit: ImageView

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun setUp() {
        mPagerAdapter.addFragment(CategoriesFragment.newInstance(), "Merchants")
        mPagerAdapter.addFragment(CategoriesFragment.newInstance(), "Categories")
        mPagerAdapter.addFragment(CategoriesFragment.newInstance(), "All")

        mViewPager.adapter = mPagerAdapter
        mViewPager.isPagingEnabled = true
        mViewPager.offscreenPageLimit = 3

        mSmartTabLayout.setViewPager(mViewPager)

        exit.setOnClickListener {
            onBackPressed()
        }
    }

    override fun bindViews() {
        mViewPager = findViewById(R.id.viewpager)
        mSmartTabLayout = findViewById(R.id.viewpagertab)
        exit = findViewById(R.id.exit)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, TransactionsActivity::class.java)
        }
    }
}
