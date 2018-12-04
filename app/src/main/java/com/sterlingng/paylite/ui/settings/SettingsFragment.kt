package com.sterlingng.paylite.ui.settings

import android.os.Bundle
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.securityquestions.SetSecurityQuestionFragment
import com.sterlingng.paylite.ui.settings.profile.ProfileFragment
import javax.inject.Inject

class SettingsFragment : BaseFragment(), SettingsMvpView {

    @Inject
    lateinit var mPresenter: SettingsMvpContract<SettingsMvpView>

    private lateinit var mProfileCardView: CardView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mProfileCardView = view.findViewById(R.id.profile_settings)
    }

    override fun setUp(view: View) {
        mProfileCardView.setOnClickListener {
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(ProfileFragment.newInstance())
        }

        if (arguments?.getBoolean(SECURITY)!!) {
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(SetSecurityQuestionFragment.newInstance())
        }
//        mHelpAndFeedbackTextView.setOnClickListener {
//            (baseActivity as DashboardActivity)
//                    .mNavController
//                    .pushFragment(HelpFragment.newInstance())
//        }
//
//
//        mPaymentMethodsTextView.setOnClickListener {
//            (baseActivity as DashboardActivity)
//                    .mNavController
//                    .pushFragment(PaymentFragment.newInstance())
//        }
//
//        mLoginAndSecurityTextView.setOnClickListener {
//            (baseActivity as DashboardActivity)
//                    .mNavController
//                    .pushFragment(LoginAndSecurityFragment.newInstance())
//        }
//
//        mNotificationTextView.setOnClickListener {
//            (baseActivity as DashboardActivity)
//                    .mNavController
//                    .pushFragment(NotificationsFragment.newInstance())
//        }
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        private const val SECURITY = "SettingsFragment.SECURITY"

        @JvmOverloads
        fun newInstance(security: Boolean = false): SettingsFragment {
            val fragment = SettingsFragment()
            val args = Bundle()
            args.putBoolean(SECURITY, security)
            fragment.arguments = args
            return fragment
        }
    }
}
