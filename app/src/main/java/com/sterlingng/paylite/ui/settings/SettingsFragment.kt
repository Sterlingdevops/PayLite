package com.sterlingng.paylite.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.main.MainActivity
import javax.inject.Inject

class SettingsFragment : BaseFragment(), SettingsMvpView {

    @Inject
    lateinit var mPresenter: SettingsMvpContract<SettingsMvpView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {

    }

    override fun setUp(view: View) {
//        mHelpAndFeedbackTextView.setOnClickListener {
//            (baseActivity as DashboardActivity)
//                    .mNavController
//                    .pushFragment(HelpFragment.newInstance())
//        }
//
//        mPersonalInfoTextView.setOnClickListener {
//            (baseActivity as DashboardActivity)
//                    .mNavController
//                    .pushFragment(EditProfileFragment.newInstance())
//        }
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

    override fun onLogOutComplete() {
        startActivity(MainActivity.getStartIntent(baseActivity))
        baseActivity.finish()
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): SettingsFragment {
            val fragment = SettingsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
