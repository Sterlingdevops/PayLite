package com.sterlingng.paylite.ui.forgot

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.ForgotPasswordRequest
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.complete.CompleteFragment
import com.sterlingng.paylite.ui.forgot.email.ForgotPhoneFragment
import com.sterlingng.paylite.ui.forgot.reset.ResetPasswordFragment
import com.sterlingng.paylite.ui.forgot.token.TokenFragment
import com.sterlingng.paylite.utils.CustomPagerAdapter
import com.sterlingng.paylite.utils.OnChildDidClickNext
import com.sterlingng.views.CustomViewPager
import javax.inject.Inject

class ForgotActivity : BaseActivity(), ForgotMvpView, OnChildDidClickNext {

    @Inject
    lateinit var mPresenter: ForgotMvpContract<ForgotMvpView>

    lateinit var mPagerAdapter: CustomPagerAdapter

    private lateinit var mViewPager: CustomViewPager

    val forgotPasswordRequest = ForgotPasswordRequest()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun bindViews() {
        mViewPager = findViewById(R.id.viewpager)
    }

    override fun setUp() {
        val emailForgotFragment = ForgotPhoneFragment.newInstance(1)
        emailForgotFragment.mDidClickNext = this

        val tokenFragment = TokenFragment.newInstance(2)
        tokenFragment.mDidClickNext = this

        val resetPasswordFragment = ResetPasswordFragment.newInstance(3)
        resetPasswordFragment.mDidClickNext = this

        val confirmResetPasswordFragment = ResetPasswordFragment.newInstance(4)
        confirmResetPasswordFragment.mDidClickNext = this

        val completeFragment = CompleteFragment.newInstance(welcomeText = "Welcome back to GoPay")

        mPagerAdapter = CustomPagerAdapter(supportFragmentManager)
        mPagerAdapter.addFragment(emailForgotFragment, "email")
        mPagerAdapter.addFragment(tokenFragment, "token")
        mPagerAdapter.addFragment(resetPasswordFragment, "reset")
        mPagerAdapter.addFragment(confirmResetPasswordFragment, "confirm-reset")
        mPagerAdapter.addFragment(completeFragment, "complete")

        mViewPager.adapter = mPagerAdapter
        mViewPager.isPagingEnabled = false
    }

    override fun onBackPressed() {
        if (mViewPager.currentItem == 0)
            super.onBackPressed()
        else
            --mViewPager.currentItem
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun onPreviousClick(index: Int) {
        mViewPager.currentItem = index % mPagerAdapter.count
    }

    override fun onNextClick(index: Int, data: Any) {
        when (index) {
            1 -> {
                forgotPasswordRequest.mobile = data as String
            }
            2 -> {
                forgotPasswordRequest.token = data as String
            }
            3 -> {
                forgotPasswordRequest.newPassword = data as String
            }
        }
        mViewPager.currentItem = index % mPagerAdapter.count
    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, ForgotActivity::class.java)
        }
    }
}
