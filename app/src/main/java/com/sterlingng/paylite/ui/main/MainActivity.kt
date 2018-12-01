package com.sterlingng.paylite.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.login.LogInActivity
import com.sterlingng.paylite.ui.signup.SignUpActivity
import javax.inject.Inject


class MainActivity : BaseActivity(), MainMvpView {

    @Inject
    lateinit var mPresenter: MainMvpContract<MainMvpView>

    private lateinit var mLogInButton: Button
    private lateinit var mSignInButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        activityComponent.inject(this)
        mPresenter.onAttach(this)

        AppCenter.start(application, "3e9e59d2-f183-4729-a723-cc2fea013055", Analytics::class.java, Crashes::class.java)
    }

    override fun initView(currentUser: User) {
        val intent: Intent = LogInActivity.getStartIntent(this)
        startActivity(intent)
    }

    override fun bindViews() {
        mLogInButton = findViewById(R.id.log_in)
        mSignInButton = findViewById(R.id.sign_in)
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

        mPresenter.onViewInitialized()
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
