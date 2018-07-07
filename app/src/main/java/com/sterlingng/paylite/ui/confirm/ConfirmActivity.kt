package com.sterlingng.paylite.ui.confirm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class ConfirmActivity : BaseActivity(), ConfirmMvpView {

    @Inject
    lateinit var mPresenter: ConfirmMvpContract<ConfirmMvpView>

    private lateinit var exit: TextView

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
    }

    override fun setUp() {
        exit.setOnClickListener {
            onBackPressed()
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, ConfirmActivity::class.java)
        }
    }
}
