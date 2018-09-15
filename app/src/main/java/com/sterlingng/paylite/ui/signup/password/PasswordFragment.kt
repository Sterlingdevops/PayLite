package com.sterlingng.paylite.ui.signup.password


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.signup.SignUpActivity
import com.sterlingng.paylite.utils.OnChildDidClickNext
import com.sterlingng.views.LargeLabelEditText
import javax.inject.Inject

class PasswordFragment : BaseFragment(), PasswordMvpView {

    @Inject
    lateinit var mPresenter: PasswordMvpContract<PasswordMvpView>

    private lateinit var mConfirmPasswordEditText: LargeLabelEditText
    private lateinit var mPasswordEditText: LargeLabelEditText

    lateinit var mDidClickNext: OnChildDidClickNext
    lateinit var next: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_password, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        next = view.findViewById(R.id.next_password)
        mPasswordEditText = view.findViewById(R.id.password)
        mConfirmPasswordEditText = view.findViewById(R.id.confirm_password)
    }

    override fun setUp(view: View) {
        next.setOnClickListener {
            if (mPasswordEditText.mTextEditText.text.isEmpty() || mConfirmPasswordEditText.mTextEditText.text.isEmpty()) {
                show("Password fields cannot be empty", true)
                return@setOnClickListener
            }

            if (mPasswordEditText.mTextEditText.text.length < 6 || mConfirmPasswordEditText.mTextEditText.text.length < 6) {
                show("Password should be 6 characters or more", true)
                return@setOnClickListener
            }

            if (mPasswordEditText.mTextEditText.text.toString() != mConfirmPasswordEditText.mTextEditText.text.toString()) {
                show("Passwords do not match", true)
                return@setOnClickListener
            }

            val signUpRequest = (baseActivity as SignUpActivity).signUpRequest
            signUpRequest.password = mPasswordEditText.text()
            mPresenter.doSignUp(signUpRequest.toHashMap())
            hideKeyboard()
        }
    }

    override fun onDoSignUpSuccessful(response: Response) {
        mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, mPasswordEditText.mTextEditText.text.toString())
    }

    override fun onDoSignUpFailed(response: Response) {
        show(response.message!!, true)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        private const val INDEX = "PasswordPinFragment.INDEX"

        fun newInstance(index: Int): PasswordFragment {
            val fragment = PasswordFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}
