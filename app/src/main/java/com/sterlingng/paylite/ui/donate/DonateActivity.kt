package com.sterlingng.paylite.ui.donate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class DonateActivity : BaseActivity(), DonateMvpView {

    @Inject
    lateinit var mPresenter: DonateMvpContract<DonateMvpView>

    private lateinit var exit: ImageView

    private lateinit var setDateTextView: TextView
    private lateinit var setRepeatTextView: TextView
    private lateinit var repeatTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var dateView: View
    private lateinit var repeatView: View
    private lateinit var switch: Switch

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)

        switch = findViewById(R.id.schedule_switch)

        setDateTextView = findViewById(R.id.set_date)
        dateTextView = findViewById(R.id.date)
        dateView = findViewById(R.id.set_date_view)

        setRepeatTextView = findViewById(R.id.set_repeat)
        repeatTextView = findViewById(R.id.repeat)
        repeatView = findViewById(R.id.set_repeat_view)
    }

    override fun setUp() {
        exit.setOnClickListener {
            onBackPressed()
        }
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setDateTextView.visibility = View.VISIBLE
                dateTextView.visibility = View.VISIBLE
                dateView.visibility = View.VISIBLE
                setRepeatTextView.visibility = View.VISIBLE
                repeatTextView.visibility = View.VISIBLE
                repeatView.visibility = View.VISIBLE
            } else {
                setDateTextView.visibility = View.GONE
                dateTextView.visibility = View.GONE
                dateView.visibility = View.GONE
                setRepeatTextView.visibility = View.GONE
                repeatTextView.visibility = View.GONE
                repeatView.visibility = View.GONE
            }
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, DonateActivity::class.java)
        }
    }
}
