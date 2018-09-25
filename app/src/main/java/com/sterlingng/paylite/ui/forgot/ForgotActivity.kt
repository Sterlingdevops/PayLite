package com.sterlingng.paylite.ui.forgot

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.ForgotPasswordRequest
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.forgot.email.EmailForgotFragment
import com.sterlingng.paylite.ui.forgot.reset.ResetFragment
import com.sterlingng.paylite.ui.forgot.token.TokenFragment
import com.sterlingng.paylite.ui.signup.complete.CompleteFragment
import com.sterlingng.paylite.utils.CustomPagerAdapter
import com.sterlingng.paylite.utils.Log
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
        val emailForgotFragment = EmailForgotFragment.newInstance(1)
        emailForgotFragment.mDidClickNext = this

        val tokenFragment = TokenFragment.newInstance(2)
        tokenFragment.mDidClickNext = this

        val resetFragment = ResetFragment.newInstance(3)
        resetFragment.mDidClickNext = this

        val completeFragment = CompleteFragment.newInstance()

        mPagerAdapter = CustomPagerAdapter(supportFragmentManager)
        mPagerAdapter.addFragment(emailForgotFragment, "email")
        mPagerAdapter.addFragment(tokenFragment, "token")
        mPagerAdapter.addFragment(resetFragment, "reset")
        mPagerAdapter.addFragment(completeFragment, "complete")

        mViewPager.adapter = mPagerAdapter
        mViewPager.isPagingEnabled = true
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
                val response = data as Response
                Log.d(response.toString())
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
