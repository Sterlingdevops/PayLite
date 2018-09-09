package com.sterlingng.paylite.ui.bills

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.utils.CustomPagerAdapter
import com.sterlingng.views.CustomViewPager
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class BillsActivity : BaseActivity(), BillsMvpView {

    @Inject
    lateinit var mPresenter: BillsMvpContract<BillsMvpView>

    @Inject
    lateinit var mPagerAdapter: CustomPagerAdapter

    private lateinit var mViewPager: CustomViewPager
    private lateinit var mSmartTabLayout: SmartTabLayout

    private lateinit var exit: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bills)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun setUp() {
        mViewPager.adapter = mPagerAdapter
        mViewPager.isPagingEnabled = true

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
            return Intent(context, BillsActivity::class.java)
        }
    }
}
