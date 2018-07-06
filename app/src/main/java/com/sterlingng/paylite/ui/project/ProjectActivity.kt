package com.sterlingng.paylite.ui.project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.donate.DonateActivity
import me.chensir.expandabletextview.ExpandableTextView
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class ProjectActivity : BaseActivity(), ProjectMvpView {

    @Inject
    lateinit var mPresenter: ProjectMvpContract<ProjectMvpView>

    private lateinit var mAboutExpandableTextView: ExpandableTextView

    private lateinit var exit: ImageView
    private lateinit var giveButton: Button
    private lateinit var giveButton2: Button

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun setUp() {
        exit.setOnClickListener {
            onBackPressed()
        }

        giveButton.setOnClickListener {
            val intent = DonateActivity.getStartIntent(this)
            startActivity(intent)
        }

        giveButton2.setOnClickListener {
            val intent = DonateActivity.getStartIntent(this)
            startActivity(intent)
        }

        mAboutExpandableTextView.text = getString(R.string.placeholder)
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        giveButton = findViewById(R.id.give)
        giveButton2 = findViewById(R.id.give2)
        mAboutExpandableTextView = findViewById(R.id.text)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, ProjectActivity::class.java)
        }
    }
}
