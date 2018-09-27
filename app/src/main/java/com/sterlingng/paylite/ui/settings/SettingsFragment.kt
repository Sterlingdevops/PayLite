package com.sterlingng.paylite.ui.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.help.HelpFragment
import com.sterlingng.paylite.ui.main.MainActivity
import com.sterlingng.paylite.ui.payment.PaymentFragment
import com.sterlingng.paylite.ui.security.LoginAndSecurityFragment
import javax.inject.Inject

class SettingsFragment : BaseFragment(), SettingsMvpView {

    @Inject
    lateinit var mPresenter: SettingsMvpContract<SettingsMvpView>

    private lateinit var mLoginAndSecurityTextView: TextView
    private lateinit var mHelpAndFeedbackTextView: TextView
    private lateinit var mPaymentMethodsTextView: TextView
    private lateinit var mLogOutTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mLoginAndSecurityTextView = view.findViewById(R.id.login_and_security)
        mHelpAndFeedbackTextView = view.findViewById(R.id.help_and_feedback)
        mPaymentMethodsTextView = view.findViewById(R.id.payment_methods)
        mLogOutTextView = view.findViewById(R.id.log_out)
    }

    override fun setUp(view: View) {
        mHelpAndFeedbackTextView.setOnClickListener {
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(HelpFragment.newInstance())
        }

        mPaymentMethodsTextView.setOnClickListener {
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(PaymentFragment.newInstance())
        }

        mLoginAndSecurityTextView.setOnClickListener {
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(LoginAndSecurityFragment.newInstance())
        }

        mLogOutTextView.setOnClickListener {
            mPresenter.logOut()
        }
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
