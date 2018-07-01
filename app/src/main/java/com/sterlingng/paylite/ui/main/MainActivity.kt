package com.sterlingng.paylite.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.main.MainMvpContract
import com.sterlingng.paylite.ui.main.MainMvpView
import com.sterlingng.paylite.ui.login.LogInActivity
import com.sterlingng.paylite.ui.signup.SignUpActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMvpView {

    @Inject
    lateinit var mPresenter: MainMvpContract<MainMvpView>

    private lateinit var mLogInButton: Button
    private lateinit var mSignInButton: Button

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
    }

    override fun setUp() {

    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    override fun onLogInClicked(view: View) {
        val intent: Intent = LogInActivity.getStartIntent(this)
        startActivity(intent)
    }

    override fun onSignUpClicked(view: View) {
        val intent: Intent = SignUpActivity.getStartIntent(this)
        startActivity(intent)
    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
