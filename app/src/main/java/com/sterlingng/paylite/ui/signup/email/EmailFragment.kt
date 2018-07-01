package com.sterlingng.paylite.ui.signup.email


import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.i18n.phonenumbers.AsYouTypeFormatter
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.lamudi.phonefield.PhoneInputLayout
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.utils.OnChildDidClickNext
import kotlinx.android.synthetic.main.fragment_email.*
import javax.inject.Inject

class EmailFragment : BaseFragment(), EmailMvpView {

    @Inject
    lateinit var mPresenter: EmailMvpContract<EmailMvpView>

    lateinit var mDidClickNext: OnChildDidClickNext

    lateinit var phoneInput: PhoneInputLayout
    lateinit var next: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_email, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        next = view.findViewById(R.id.next)
        phoneInput = view.findViewById(R.id.phone_input_layout)
    }

    override fun setUp(view: View) {
        phoneInput.setHint(R.string.phone)
        phoneInput.setDefaultCountry("NG")
        phoneInput.editText?.addTextChangedListener(PhoneNumberTextWatcher())
        phoneInput.editText?.setRawInputType(InputType.TYPE_CLASS_NUMBER)
        next.setOnClickListener {
            mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!)
            hideKeyboard()
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {
        private const val INDEX: String = "EmailFragment.INDEX"

        fun newInstance(index: Int): EmailFragment {
            val fragment = EmailFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }

    private val phoneUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()
    private val asYouTypeFormatter: AsYouTypeFormatter = phoneUtil.getAsYouTypeFormatter("US")

    inner class PhoneNumberTextWatcher : TextWatcher {
        override fun afterTextChanged(s: Editable) {
            if (!phone_input_layout.editText?.text?.contains("+")!!) {
                asYouTypeFormatter.clear()
                asYouTypeFormatter.inputDigit('+')
                phone_input_layout.editText.text.forEach { it ->
                    phone_input_layout.editText.setText(asYouTypeFormatter.inputDigit(it))
                }
                phone_input_layout.editText?.setSelection(phone_input_layout.editText?.length()!!)
            }

            if (phone_input_layout.isValid) {
                phone_input_layout.setError(null)
            } else {
                phone_input_layout.setError(getString(R.string.invalid_phone_number))
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }
}
