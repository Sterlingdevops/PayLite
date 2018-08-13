package com.sterlingng.paylite.ui.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.signup.complete.CompleteFragment
import com.sterlingng.paylite.ui.signup.email.EmailFragment
import com.sterlingng.paylite.ui.signup.name.NameFragment
import com.sterlingng.paylite.ui.signup.otp.OtpFragment
import com.sterlingng.paylite.ui.signup.password.PasswordFragment
import com.sterlingng.paylite.ui.signup.pin.PinFragment
import com.sterlingng.paylite.utils.CustomPagerAdapter
import com.sterlingng.paylite.utils.OnChildDidClickNext
import com.sterlingng.views.CustomViewPager
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class SignUpActivity : BaseActivity(), SignUpMvpView, OnChildDidClickNext {

    @Inject
    lateinit var mPresenter: SignUpMvpContract<SignUpMvpView>

    @Inject
    lateinit var mPagerAdapter: CustomPagerAdapter

    private lateinit var mViewPager: CustomViewPager

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun bindViews() {
        mViewPager = findViewById(R.id.view_pager)
    }

    override fun setUp() {
        val nameFragment = NameFragment.newInstance(1)
        nameFragment.mDidClickNext = this

        val emailFragment = EmailFragment.newInstance(2)
        emailFragment.mDidClickNext = this

        val otpFragment = OtpFragment.newInstance(3)
        otpFragment.mDidClickNext = this

        val passwordFragment = PasswordFragment.newInstance(4)
        passwordFragment.mDidClickNext = this

        val pinFragment = PinFragment.newInstance(5)
        pinFragment.mDidClickNext = this

        val completeFragment = CompleteFragment.newInstance()

        mPagerAdapter.run {
            addFragment(nameFragment, getString(R.string.personal_details))
            addFragment(emailFragment, getString(R.string.email))
            addFragment(otpFragment, getString(R.string.otp_ph))
            addFragment(passwordFragment, getString(R.string.password))
            addFragment(pinFragment, getString(R.string.pin))
            addFragment(completeFragment, getString(R.string.account_setup_completed))
        }

        mViewPager.adapter = mPagerAdapter
        mViewPager.isPagingEnabled = false
        mViewPager.offscreenPageLimit = 1
    }

    override fun onBackPressed() {
        if (mViewPager.currentItem == 0)
            super.onBackPressed()
        else
            --mViewPager.currentItem
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    override fun onPreviousClick(index: Int) {
        mViewPager.currentItem = index % mPagerAdapter.count
    }

    override fun onNextClick(index: Int) {
        mViewPager.currentItem = index % mPagerAdapter.count
    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }
}
