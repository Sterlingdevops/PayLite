package com.sterlingng.paylite.ui.charity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.charity.about.AboutFragment
import com.sterlingng.paylite.ui.charity.program.ProgramFragment
import com.sterlingng.paylite.ui.donate.DonateActivity
import com.sterlingng.paylite.utils.CustomPagerAdapter
import com.sterlingng.views.CustomViewPager
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class CharityActivity : BaseActivity(), CharityMvpView {

    @Inject
    lateinit var mPresenter: CharityMvpContract<CharityMvpView>

    lateinit var mPagerAdapter: CustomPagerAdapter

    private lateinit var mViewPager: CustomViewPager
    private lateinit var mSmartTabLayout: SmartTabLayout

    private lateinit var exit: ImageView
    private lateinit var giveButton2: TextView

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charity)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun setUp() {
        mPagerAdapter = CustomPagerAdapter(supportFragmentManager)

        mPagerAdapter.addFragment(AboutFragment.newInstance(), "About")
        mPagerAdapter.addFragment(ProgramFragment.newInstance(), "Program")

        mViewPager.adapter = mPagerAdapter
        mViewPager.isPagingEnabled = true

        mSmartTabLayout.setViewPager(mViewPager)

        exit.setOnClickListener {
            onBackPressed()
        }

        giveButton2.setOnClickListener {
            val intent = DonateActivity.getStartIntent(this)
                    .putExtra(DonateActivity.donateTitle, "Give")
                    .putExtra(DonateActivity.donateType, 0)
            startActivity(intent)
        }
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        giveButton2 = findViewById(R.id.give2)
        mViewPager = findViewById(R.id.viewpager)
        mSmartTabLayout = findViewById(R.id.viewpagertab)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, CharityActivity::class.java)
        }
    }
}
