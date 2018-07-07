package com.sterlingng.paylite.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import com.sterlingng.paylite.utils.widgets.CustomViewPager
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
        mSignInButton = findViewById(R.id.sign_in)
        mViewPager = findViewById(R.id.viewpager)
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
            addFragment(OnBoardingFragment.newInstance(R.drawable.onboarding_1, true, ScreenData("", "")), "")
            addFragment(OnBoardingFragment.newInstance(R.drawable.onboarding_2, false,
                    ScreenData(
                            title = "Send Money", info = "Send money to anyone with just\n their phone number, email or social handle."
                    )), "")
            addFragment(OnBoardingFragment.newInstance(R.drawable.onboarding_3, false,
                    ScreenData(
                            title = "Generate Paycodes", info = "Generate cash withdrawal codes\n that can be collected at ATMs, branches or agents."
                    )), "")
            addFragment(OnBoardingFragment.newInstance(R.drawable.onboarding_4, false,
                    ScreenData(
                            title = "Give", info = "Donate to projects close\n to your heart or charities you love"
                    )), "")
            addFragment(OnBoardingFragment.newInstance(R.drawable.onboarding_5, false,
                    ScreenData(
                            title = "Request Payment", info = "Ask family, friends or clients\n for money using our customised payment links"
                    )), "")
        }

        mViewPager.adapter = mPagerAdapter
        mViewPager.isPagingEnabled = true
        mViewPager.offscreenPageLimit = mPagerAdapter.count

        mSmartTabLayout.setViewPager(mViewPager)
    }

//    override fun onBackPressed() {
//        if (mViewPager.currentItem == 0)
//            super.onBackPressed()
//        else
//            --mViewPager.currentItem
//    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
