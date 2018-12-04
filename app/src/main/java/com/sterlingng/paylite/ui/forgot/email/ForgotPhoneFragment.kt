package com.sterlingng.paylite.ui.forgot.email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.utils.OnChildDidClickNext
import com.sterlingng.views.LargeLabelEditText
import javax.inject.Inject

class ForgotPhoneFragment : BaseFragment(), ForgotPhoneMvpView {

    @Inject
    lateinit var mPresenter: ForgotPhoneMvpContract<ForgotPhoneMvpView>

    private lateinit var exit: ImageView
    private lateinit var next: Button
    private lateinit var mPhoneEditText: LargeLabelEditText
    lateinit var mDidClickNext: OnChildDidClickNext

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_forgot_phone, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        next.setOnClickListener {
            if (mPhoneEditText.text.isEmpty() || mPhoneEditText.text.length != 10) {
                show("Phone number is required", true)
                return@setOnClickListener
            }

            val data = HashMap<String, Any>()
            data["mobile"] = "0${mPhoneEditText.text}"
            mPresenter.sendPasswordResetToken(data)
        }
    }

    override fun onPasswordResetTokenFailed() {
        show("An error occurred, please try again", false)
    }

    override fun onPasswordResetTokenSuccessful() {
        mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, mPhoneEditText.text)
        hideKeyboard()
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next_reset)
        mPhoneEditText = view.findViewById(R.id.phone)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun logout() {
        baseActivity.logout()
    }

    companion object {

        private const val INDEX = "ForgotPhoneFragment.INDEX"

        fun newInstance(index: Int): ForgotPhoneFragment {
            val fragment = ForgotPhoneFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}
