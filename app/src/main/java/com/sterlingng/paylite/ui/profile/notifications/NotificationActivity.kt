package com.sterlingng.paylite.ui.profile.notifications

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Notification
import com.sterlingng.paylite.ui.base.BaseActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class NotificationActivity : BaseActivity(), NotificationMvpView, NotificationsAdapter.OnRetryClicked {

    @Inject
    lateinit var mPresenter: NotificationMvpContract<NotificationMvpView>

    private lateinit var exit: ImageView

    @Inject
    lateinit var mNotificationsAdapter: NotificationsAdapter

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mClearTextView: TextView

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun setUp() {
        exit.setOnClickListener {
            onBackPressed()
        }

        mClearTextView.setOnClickListener {
            mNotificationsAdapter.clear()
        }

        mNotificationsAdapter.mRecyclerViewClickListener = this
        mNotificationsAdapter.onRetryClickedListener = this
        mRecyclerView.adapter = mNotificationsAdapter
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.scrollToPosition(0)

        mPresenter.loadNotifications()
    }

    override fun updateNotifications(it: ArrayList<Notification>) {
        mNotificationsAdapter.add(it)
        mRecyclerView.scrollToPosition(0)
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        mClearTextView = findViewById(R.id.clear)
        mRecyclerView = findViewById(R.id.recyclerView)
    }

    override fun onRetryClicked() {

    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, NotificationActivity::class.java)
        }
    }
}
