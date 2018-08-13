package com.sterlingng.paylite.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.Button
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.login.LogInActivity
import com.sterlingng.paylite.ui.main.onboarding.OnBoardingFragment
import com.sterlingng.paylite.ui.main.onboarding.ScreenData
import com.sterlingng.paylite.ui.signup.SignUpActivity
import com.sterlingng.paylite.utils.CustomPagerAdapter
import com.sterlingng.views.CustomViewPager
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMvpView {

    @Inject
    lateinit var mPresenter: MainMvpContract<MainMvpView>

    private lateinit var mLogInButton: Button
    private lateinit var mSignInButton: Button

    @Inject
    lateinit var mPagerAdapter: CustomPagerAdapter

    private lateinit var mSmartTabLayout: SmartTabLayout
    private lateinit var mViewPager: CustomViewPager

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun bindViews() {
        mLogInButton = findViewById(R.id.log_in)
        mViewPager = findViewById(R.id.viewpager)
        mSignInButton = findViewById(R.id.sign_in)
        mSmartTabLayout = findViewById(R.id.viewpagertab)
    }

    override fun setUp() {
        mLogInButton.setOnClickListener {
            val intent: Intent = LogInActivity.getStartIntent(this)
            startActivity(intent)
        }

        mSignInButton.setOnClickListener {
            val intent: Intent = SignUpActivity.getStartIntent(this)
            startActivity(intent)
        }
        mPagerAdapter.run {
            addFragment(OnBoardingFragment.newInstance(R.drawable.bg_splash, true, R.color.black, ScreenData("", "")), "")
//            addFragment(OnBoardingFragment.newInstance(R.drawable.onboarding_2, false, R.color.dark_sage,
//                    ScreenData(
//                            title = "Send Money", info = "Send money to anyone with just\n their phone number,\n email or social handle."
//                    )), "")
//            addFragment(OnBoardingFragment.newInstance(R.drawable.onboarding_3, false, R.color.blueberry,
//                    ScreenData(
//                            title = "Generate Paycodes", info = "Generate cash withdrawal codes\n that can be collected\n at ATMs, branches or agents."
//                    )), "")
//            addFragment(OnBoardingFragment.newInstance(R.drawable.onboarding_4, false, R.color.sienna,
//                    ScreenData(
//                            title = "Give", info = "Donate to projects close\n to your heart or\n notifications you love"
//                    )), "")
//            addFragment(OnBoardingFragment.newInstance(R.drawable.onboarding_5, false, R.color.reddish_purple,
//                    ScreenData(
//                            title = "Request Payment", info = "Ask family, friends or clients\n for money using\n our customised payment links"
//                    )), "")
        }

        mViewPager.adapter = mPagerAdapter
        mViewPager.isPagingEnabled = true
        mViewPager.offscreenPageLimit = mPagerAdapter.count
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                mSignInButton.setTextColor(ContextCompat.getColor(this@MainActivity,
                        (mPagerAdapter.getFragment(position) as OnBoardingFragment).colorId))
            }

        })

        mSmartTabLayout.setViewPager(mViewPager)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
