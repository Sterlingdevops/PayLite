package com.sterlingng.paylite.ui.settings.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.BuildConfig
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.editprofile.EditProfileFragment
import com.sterlingng.paylite.ui.main.MainActivity
import com.sterlingng.paylite.ui.security.LoginAndSecurityFragment
import com.sterlingng.paylite.utils.toSentenceCase
import com.sterlingng.views.TitleIconView
import javax.inject.Inject

class ProfileFragment : BaseFragment(), ProfileMvpView {

    @Inject
    lateinit var mPresenter: ProfileMvpContract<ProfileMvpView>

    private lateinit var mPersonDetailsTextView: TitleIconView
    private lateinit var mPasswordsTextView: TitleIconView
    private lateinit var mFullNameTextView: TextView
    private lateinit var mVersionTextView: TextView
    private lateinit var mLogoutTextView: TextView
    private lateinit var exit: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun setUp(view: View) {
        mPresenter.onViewInitialized()
        mVersionTextView.text = "Version " + BuildConfig.VERSION_NAME

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mLogoutTextView.setOnClickListener {
            mPresenter.logOut()
        }

        mPersonDetailsTextView.setOnClickListener {
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(EditProfileFragment.newInstance())
        }

        mPasswordsTextView.setOnClickListener {
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(LoginAndSecurityFragment.newInstance())
        }
    }

    override fun onLogOutComplete() {
        startActivity(MainActivity.getStartIntent(baseActivity))
        baseActivity.finish()
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mLogoutTextView = view.findViewById(R.id.log_out)
        mVersionTextView = view.findViewById(R.id.version)
        mPasswordsTextView = view.findViewById(R.id.passwords)
        mFullNameTextView = view.findViewById(R.id.profile_name)
        mPersonDetailsTextView = view.findViewById(R.id.personal_details)
    }

    @SuppressLint("SetTextI18n")
    override fun initView(user: User) {
        mFullNameTextView.text = "${user.firstName} ${user.lastName}".toSentenceCase()
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
