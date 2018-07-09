package com.sterlingng.paylite.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.profile.edit.EditActivity
import com.sterlingng.paylite.ui.profile.notifications.NotificationActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class ProfileActivity : BaseActivity(), ProfileMvpView {

    @Inject
    lateinit var mPresenter: ProfileMvpContract<ProfileMvpView>

    private lateinit var exit: ImageView
    private lateinit var editProfile: TextView
    private lateinit var notifications: TextView

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        editProfile = findViewById(R.id.edit_profile)
        notifications = findViewById(R.id.notifications)
    }

    override fun setUp() {
        exit.setOnClickListener {
            onBackPressed()
        }
        editProfile.setOnClickListener {
            startActivity(EditActivity.getStartIntent(this))
        }
        notifications.setOnClickListener {
            startActivity(NotificationActivity.getStartIntent(this))
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, ProfileActivity::class.java)
        }
    }
}
