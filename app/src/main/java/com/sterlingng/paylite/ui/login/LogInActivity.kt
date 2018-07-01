package com.sterlingng.paylite.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class LogInActivity : BaseActivity(), LogInMvpView {

    @Inject
    lateinit var mPresenter: LogInMvpContract<LogInMvpView>

    lateinit var loginButton: Button

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun bindViews() {
        loginButton = findViewById(R.id.sign_in)
    }

    override fun setUp() {
        loginButton.setOnClickListener {
            val intent = DashboardActivity.getStartIntent(this)
                    .putExtra(DashboardActivity.SELECTED_ITEM, 0)
            startActivity(intent)
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    override fun onLogInClicked(view: View) {

    }

    override fun onForgotPasswordClicked(view: View) {

    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LogInActivity::class.java)
        }
    }
}
