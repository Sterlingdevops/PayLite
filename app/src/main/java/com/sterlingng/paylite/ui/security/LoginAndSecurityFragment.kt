package com.sterlingng.paylite.ui.security

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.authpin.AuthPinFragment
import com.sterlingng.paylite.ui.authpin.OpenPinMode
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.securityquestions.SetSecurityQuestionFragment
import javax.inject.Inject

class LoginAndSecurityFragment : BaseFragment(), LoginAndSecurityMvpView {

    @Inject
    lateinit var mPresenter: LoginAndSecurityMvpContract<LoginAndSecurityMvpView>

    private lateinit var mChangeTransactionPinTextView: TextView
    private lateinit var mSecurityQuestionsTextView: TextView
    private lateinit var mChangeLoginPinTextView: TextView
    private lateinit var exit: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login_and_security, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mChangeLoginPinTextView = view.findViewById(R.id.change_login_pin)
        mSecurityQuestionsTextView = view.findViewById(R.id.security_questions)
        mChangeTransactionPinTextView = view.findViewById(R.id.change_transaction_pin)
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mSecurityQuestionsTextView.setOnClickListener {
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(SetSecurityQuestionFragment.newInstance())
        }

        mChangeLoginPinTextView.setOnClickListener {

        }

        mChangeTransactionPinTextView.setOnClickListener {
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(AuthPinFragment.newInstance(OpenPinMode.VALIDATE_FOR_CHANGE.name))
        }
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): LoginAndSecurityFragment {
            val fragment = LoginAndSecurityFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }
}