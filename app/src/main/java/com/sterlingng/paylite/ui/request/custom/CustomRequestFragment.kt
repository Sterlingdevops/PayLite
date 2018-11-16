package com.sterlingng.paylite.ui.request.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.main.MainActivity
import com.sterlingng.paylite.utils.isValidEmail
import java.util.regex.Pattern
import javax.inject.Inject
import kotlin.collections.set

class CustomRequestFragment : BaseFragment(), CustomRequestMvpView {

    @Inject
    lateinit var mPresenter: CustomRequestMvpContract<CustomRequestMvpView>

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mMessageEditText: EditText
    private lateinit var mAmountEditText: EditText
    private lateinit var mEmailEditText: EditText
    private lateinit var mPhoneEditText: EditText

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)

        mEmailEditText = view.findViewById(R.id.email)
        mPhoneEditText = view.findViewById(R.id.phone)
        mAmountEditText = view.findViewById(R.id.amount)
        mMessageEditText = view.findViewById(R.id.message)
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        next.setOnClickListener {
            if (mAmountEditText.text.toString().isEmpty()) {
                show("Amount should be more than NGN 100", true)
                return@setOnClickListener
            }

            try {
                if (mAmountEditText.text.toString().toInt() < 100) {
                    show("Amount should be more than NGN 100", true)
                    throw NumberFormatException("Amount should be more than NGN 100")
                }
            } catch (e: NumberFormatException) {
                show("Amount should be more than NGN 100", true)
                return@setOnClickListener
            }

            val pattern = Pattern.compile("[0-9]{11}")
            val matcher = pattern.matcher(mPhoneEditText.text.toString())
            if (mPhoneEditText.text.length != 11 || !matcher.matches()) {
                show("Please enter a valid phone number", true)
                return@setOnClickListener
            }

            if (!mEmailEditText.text.isEmpty() && !mEmailEditText.text.toString().isValidEmail()) {
                show("Email isn't properly formatted", true)
                return@setOnClickListener
            }

            val data = HashMap<String, Any>()
            data["email"] = mEmailEditText.text.toString()
            data["phone"] = mPhoneEditText.text.toString()
            data["amount"] = mAmountEditText.text.toString()
            data["message"] = mMessageEditText.text.toString()
            mPresenter.requestPaymentLink(data)
        }
    }

    override fun onRequestPaymentLinkSent() {
        show("Request Sent", true)
        (baseActivity as DashboardActivity).mNavController.popFragment()
    }

    override fun onSendRequestPaymentLinkFailed() {
        show("Something went wrong and your request wasn't successfully processed, please try again", true)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun logout() {
        show("Session has timed out", true)
        startActivity(MainActivity.getStartIntent(baseActivity))
        baseActivity.finish()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_custom_request, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    companion object {

        fun newInstance(): CustomRequestFragment {
            val fragment = CustomRequestFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
