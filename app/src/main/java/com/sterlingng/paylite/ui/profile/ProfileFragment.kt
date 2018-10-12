package com.sterlingng.paylite.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.profile.notifications.NotificationActivity
import javax.inject.Inject

class ProfileFragment : BaseFragment(), ProfileMvpView {

    @Inject
    lateinit var mPresenter: ProfileMvpContract<ProfileMvpView>

    private lateinit var exit: ImageView
    private lateinit var editProfile: TextView
    private lateinit var notifications: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        editProfile = view.findViewById(R.id.edit_profile)
        notifications = view.findViewById(R.id.notifications)
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }
        editProfile.setOnClickListener {

        }
        notifications.setOnClickListener {
            startActivity(NotificationActivity.getStartIntent(baseActivity))
        }
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
