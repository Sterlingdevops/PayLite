package com.sterlingng.paylite.ui.signup.complete


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.signup.SignUpActivity
import com.sterlingng.paylite.utils.AppConstants
import com.sterlingng.paylite.utils.AppUtils
import javax.inject.Inject

class CompleteSignUpFragment : BaseFragment(), CompleteSignUpMvpView {

    @Inject
    lateinit var mPresenter: CompleteSignUpMvpContract<CompleteSignUpMvpView>

    private lateinit var mSkipButton: Button
    private lateinit var mFundWalletButton: Button
    private lateinit var mWelcomeTextView: TextView
    private lateinit var mCompletionType: CompletionType
    private lateinit var mSecurityQuestionsButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_signup_complete, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mSkipButton = view.findViewById(R.id.skip)
        mWelcomeTextView = view.findViewById(R.id.welcome_text)
        mFundWalletButton = view.findViewById(R.id.fund_wallet)
        mSecurityQuestionsButton = view.findViewById(R.id.security_questions)
    }

    @SuppressLint("SetTextI18n")
    override fun setWelcomeText() {
        mWelcomeTextView.text = "Welcome ${(baseActivity as SignUpActivity).signUpRequest.firstName}!"
    }

    override fun setUp(view: View) {
        mPresenter.initView()

        mSecurityQuestionsButton.setOnClickListener {
            mCompletionType = CompletionType.SECURITY_QUESTIONS
            val data = HashMap<String, String>()
            data["username"] = (baseActivity as SignUpActivity).signUpRequest.email
            data["password"] = (baseActivity as SignUpActivity).signUpRequest.password
            mPresenter.doLogIn(data)
        }

        mFundWalletButton.setOnClickListener {
            mCompletionType = CompletionType.FUND_WALLET
            val data = HashMap<String, String>()
            data["username"] = (baseActivity as SignUpActivity).signUpRequest.email
            data["password"] = (baseActivity as SignUpActivity).signUpRequest.password
            mPresenter.doLogIn(data)
        }

        mSkipButton.setOnClickListener {
            mCompletionType = CompletionType.NORMAL
            val data = HashMap<String, String>()
            data["username"] = (baseActivity as SignUpActivity).signUpRequest.email
            data["password"] = (baseActivity as SignUpActivity).signUpRequest.password
            mPresenter.doLogIn(data)
        }
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun onAccessTokenSuccessful() {
        AppUtils.createEvent(
                baseActivity,
                AppConstants.ON_BOARDING,
                AppConstants.EVENT_SEVEN,
                AppConstants.EVENT_SEVEN,
                AppUtils.createId(),
                (baseActivity as SignUpActivity).latitude,
                (baseActivity as SignUpActivity).longitude,
                javaClass.simpleName,
                ""
        )

        val intent: Intent = when (mCompletionType) {
            CompletionType.FUND_WALLET -> {
                DashboardActivity.getStartIntent(baseActivity)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .putExtra(DashboardActivity.SELECTED_ITEM, 0)
                        .putExtra(DashboardActivity.FUND_WALLET, true)
            }
            CompletionType.NORMAL -> {
                DashboardActivity.getStartIntent(baseActivity)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .putExtra(DashboardActivity.SELECTED_ITEM, 0)
            }
            CompletionType.SECURITY_QUESTIONS -> {
                DashboardActivity.getStartIntent(baseActivity)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .putExtra(DashboardActivity.SELECTED_ITEM, 0)
                        .putExtra(DashboardActivity.SECURITY_QUESTIONS, true)
            }
        }

        startActivity(intent)
        baseActivity.finish()
    }

    override fun onAccessTokenFailed() {
        show("An error occurred while processing your request, Please try again", true)
        hideLoading()
    }

    enum class CompletionType {
        FUND_WALLET, SECURITY_QUESTIONS, NORMAL
    }

    companion object {

        fun newInstance(): CompleteSignUpFragment {
            val fragment = CompleteSignUpFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
