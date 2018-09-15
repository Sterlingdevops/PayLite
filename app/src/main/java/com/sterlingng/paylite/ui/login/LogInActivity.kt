package com.sterlingng.paylite.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.goodiebag.pinview.PinView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.forgot.ForgotActivity
import com.sterlingng.paylite.utils.AppUtils.hasInternetConnection
import javax.inject.Inject

class LogInActivity : BaseActivity(), LogInMvpView {

    @Inject
    lateinit var mPresenter: LogInMvpContract<LogInMvpView>

    private lateinit var mPinView: PinView
    private lateinit var mForgotPinTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun bindViews() {
        mPinView = findViewById(R.id.pin_view)
        mForgotPinTextView = findViewById(R.id.forgot)
    }

    override fun setUp() {
        mPinView.setOnClickListener {
            mPinView.requestPinEntryFocus()
        }

        mForgotPinTextView.setOnClickListener {
            startActivity(ForgotActivity.getStartIntent(this))
        }

        mPinView.setPinViewEventListener { _, _ ->
            if (hasInternetConnection(this@LogInActivity)) {
                val data = HashMap<String, String>()
                data["password"] = mPinView.value
                mPresenter.doLogIn(data)
            } else {
                mPinView.clearValue()
                show("Internet connection required", true)
            }
        }
    }

    override fun onUserNotRegistered() {
        mPinView.clearValue()
        show("No User has be registered on this device", true)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    override fun onDoSignInSuccessful() {
        hideKeyboard()
        val intent = DashboardActivity.getStartIntent(this)
                .putExtra(DashboardActivity.SELECTED_ITEM, 0)
        startActivity(intent)
    }

    override fun onDoSignInFailed() {
        show("An error while logging you in. Please check the PIN you entered", true)
        mPinView.clearValue()
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LogInActivity::class.java)
        }
    }
}
