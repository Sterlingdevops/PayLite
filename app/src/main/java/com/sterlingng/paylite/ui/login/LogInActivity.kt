package com.sterlingng.paylite.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.goodiebag.pinview.PinView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.utils.AppUtils.hasInternetConnection
import javax.inject.Inject

class LogInActivity : BaseActivity(), LogInMvpView {

    @Inject
    lateinit var mPresenter: LogInMvpContract<LogInMvpView>

    private lateinit var mPinView: PinView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun bindViews() {
        mPinView = findViewById(R.id.pin_view)
    }

    override fun setUp() {
        mPresenter.onViewInitialized()

        mPinView.setOnClickListener {
            showKeyboard()
            mPinView.requestPinEntryFocus()
        }

        mPinView.setPinViewEventListener { _, _ ->
            if (hasInternetConnection(this@LogInActivity)) {
                val data = HashMap<String, Any>()
                data["password"] = mPinView.value
                mPresenter.doLogIn(data)
            } else {
                mPinView.clearValue()
                show("Internet connection required", true)
            }
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    override fun onDoSignInSuccessful(response: Response) {
        hideKeyboard()
        val intent = DashboardActivity.getStartIntent(this)
                .putExtra(DashboardActivity.SELECTED_ITEM, 0)
        startActivity(intent)
    }

    override fun onDoSignInFailed(response: Response) {
        show("Error logging in. Please check the email and password", true)
        mPinView.clearValue()
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LogInActivity::class.java)
        }
    }
}
