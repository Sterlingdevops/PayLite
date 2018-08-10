package com.sterlingng.paylite.ui.request


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class RequestActivity : BaseActivity(), RequestMvpView {

    @Inject
    lateinit var mPresenter: RequestMvpContract<RequestMvpView>

    private lateinit var exit: ImageView

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun setUp() {
        exit.setOnClickListener {
            onBackPressed()
        }
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, RequestActivity::class.java)
        }
    }
}
