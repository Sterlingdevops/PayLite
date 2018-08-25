package com.sterlingng.paylite.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.utils.AppUtils.hasInternetConnection
import com.sterlingng.views.LargeLabelEditText
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class LogInActivity : BaseActivity(), LogInMvpView {

    @Inject
    lateinit var mPresenter: LogInMvpContract<LogInMvpView>

    private lateinit var loginButton: Button
    private lateinit var mPasswordEditText: LargeLabelEditText
    private lateinit var mUsernameEditText: LargeLabelEditText

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
        mPasswordEditText = findViewById(R.id.password)
        mUsernameEditText = findViewById(R.id.username)
    }

    override fun setUp() {
        mPresenter.onViewInitialized()

        loginButton.setOnClickListener {
            if (hasInternetConnection(this@LogInActivity)) {
                val data = HashMap<String, Any>()
                data["username"] = mUsernameEditText.mTextEditText.text.toString()
                data["password"] = mPasswordEditText.mTextEditText.text.toString()
                mPresenter.doLogIn(data)
            } else {
                show("Please check you internet connection", true)
            }
        }
    }

    override fun initView(currentUser: User) {
        mUsernameEditText.mTextEditText.setText(currentUser.username)
        mPasswordEditText.mTextEditText.requestFocus()
//        showKeyboard()

        val intent = DashboardActivity.getStartIntent(this)
                .putExtra(DashboardActivity.SELECTED_ITEM, 0)
        startActivity(intent)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    override fun onDoSignInSuccessful(response: Response) {
        if (response.status == "success") {
            val intent = DashboardActivity.getStartIntent(this)
                    .putExtra(DashboardActivity.SELECTED_ITEM, 0)
            startActivity(intent)
        }
    }

    override fun onDoSignInFailed(response: Response) {
        show(response.message!!, true)
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LogInActivity::class.java)
        }
    }
}
