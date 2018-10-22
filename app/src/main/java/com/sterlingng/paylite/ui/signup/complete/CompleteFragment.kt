package com.sterlingng.paylite.ui.signup.complete


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
import javax.inject.Inject

class CompleteFragment : BaseFragment(), CompleteMvpView {

    @Inject
    lateinit var mPresenter: CompleteMvpContract<CompleteMvpView>

    private lateinit var next: Button
    private lateinit var mWelcomeTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_complete, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        next = view.findViewById(R.id.next_complete)
        mWelcomeTextView = view.findViewById(R.id.welcome_text)
    }

    override fun setWelcomeText() {
        mWelcomeTextView.text = arguments?.getString(WELCOME_TEXT)
    }

    override fun setUp(view: View) {
        mPresenter.initView()

        next.setOnClickListener {
            if (arguments?.getString(WELCOME_TEXT) == "Welcome back to Paylite") {
                baseActivity.finish()
            } else {
                val data = HashMap<String, String>()
                data["username"] = (baseActivity as SignUpActivity).signUpRequest.email
                data["password"] = (baseActivity as SignUpActivity).signUpRequest.password
                mPresenter.doLogIn(data)
            }
        }
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun onAccessTokenSuccessful() {
        val intent = DashboardActivity.getStartIntent(baseActivity)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtra(DashboardActivity.SELECTED_ITEM, 0)
        startActivity(intent)
        baseActivity.finish()
    }

    override fun onAccessTokenFailed() {
        show("An error occurred while processing your request, Please try again", true)
        hideLoading()
    }

    companion object {

        const val WELCOME_TEXT = "CompleteFragment.WELCOME_TEXT"

        @JvmOverloads
        fun newInstance(welcomeText: String = "Welcome to Paylite"): CompleteFragment {
            val fragment = CompleteFragment()
            val args = Bundle()
            args.putString(WELCOME_TEXT, welcomeText)
            fragment.arguments = args
            return fragment
        }
    }
}
