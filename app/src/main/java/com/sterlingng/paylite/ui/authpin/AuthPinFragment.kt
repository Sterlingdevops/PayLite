package com.sterlingng.paylite.ui.authpin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.davidmiguel.numberkeyboard.NumberKeyboard
import com.davidmiguel.numberkeyboard.NumberKeyboardListener
import com.goodiebag.pinview.PinView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.fund.FundFragment
import javax.inject.Inject

class AuthPinFragment : BaseFragment(), AuthPinMvpView, NumberKeyboardListener {

    @Inject
    lateinit var mPresenter: AuthPinMvpContract<AuthPinMvpView>

    @Inject
    lateinit var eventBus: EventBus

    private lateinit var mNumberKeyboard: NumberKeyboard
    private lateinit var mSubTitleTextView: TextView
    private lateinit var mTitleTextView: TextView
    private lateinit var mPinView: PinView
    private lateinit var exit: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pin, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mPinView = view.findViewById(R.id.pin_view)
        mTitleTextView = view.findViewById(R.id.textView3)
        mSubTitleTextView = view.findViewById(R.id.textView2)
        mNumberKeyboard = view.findViewById(R.id.numberKeyboard)
    }

    override fun setUp(view: View) {
        mNumberKeyboard.setListener(this)

        val mode = OpenPinMode.valueOf(arguments?.getString(OPEN_PIN_MODE)!!)
        when (mode) {
            OpenPinMode.NONE -> {
                mTitleTextView.text = ""
                mSubTitleTextView.text = ""
            }
            OpenPinMode.VALIDATE_FOR_CHANGE -> {
                mTitleTextView.text = "Change PIN"
                mSubTitleTextView.text = "Enter your current PIN"
            }
            OpenPinMode.ENTER_NEW -> {
                mTitleTextView.text = "Create a new Transaction PIN"
                mSubTitleTextView.text = "Enter a PIN"
            }
            OpenPinMode.CONFIRM_NEW -> {
                mTitleTextView.text = "Confirm PIN"
                mSubTitleTextView.text = "Please re-enter your PIN"
            }
        }

        exit.setOnClickListener {
            baseActivity.onBackPressed()
            hideKeyboard()
        }

        mPinView.setPinViewEventListener { _, _ ->
            validate()
        }
    }

    private fun validate() {
        val mode = OpenPinMode.valueOf(arguments?.getString(OPEN_PIN_MODE)!!)
        val extra = arguments?.getString(PIN_EXTRA)!!
        when (mode) {
            OpenPinMode.ENTER_NEW -> {
                (baseActivity as DashboardActivity)
                        .mNavController
                        .pushFragment(AuthPinFragment.newInstance(OpenPinMode.CONFIRM_NEW.name,
                                openFund = arguments?.getBoolean(OPEN_FRAGMENT)!!,
                                extra = mPinView.value))
            }
            OpenPinMode.CONFIRM_NEW -> {
                if (mPinView.value == extra) {
                    mPresenter.savePin(extra)

                    (baseActivity as DashboardActivity)
                            .mNavController
                            .clearStack()
                    hideKeyboard()

                    if (arguments?.getBoolean(OPEN_FRAGMENT)!!)
                        (baseActivity as DashboardActivity).mNavController.pushFragment(FundFragment.newInstance())
                } else {
                    show("The PINs you entered do not match", true)
                }
            }
            OpenPinMode.VALIDATE_FOR_CHANGE -> {
                if (mPresenter.validate(mPinView.value)) {
                    (baseActivity as DashboardActivity)
                            .mNavController
                            .pushFragment(AuthPinFragment.newInstance(OpenPinMode.ENTER_NEW.name))
                } else {
                    show("The PINs you entered is incorrect", true)
                }
            }
            OpenPinMode.NONE -> {

            }
        }
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun onNumberClicked(number: Int) {
        mPinView.append(number.toString())
    }

    override fun onLeftAuxButtonClicked() {

    }

    override fun onRightAuxButtonClicked() {
        mPinView.backSpaceClicked()
    }

    companion object {

        private const val OPEN_PIN_MODE = "AuthPinFragment.MODE"
        private const val PIN_EXTRA = "AuthPinFragment.PIN_EXTRA"
        private const val OPEN_FRAGMENT = "AuthPinFragment.OPEN_FRAGMENT"

        @JvmOverloads
        fun newInstance(mode: String, openFund: Boolean = false, extra: String = ""): AuthPinFragment {
            val fragment = AuthPinFragment()
            val args = Bundle()
            args.putString(PIN_EXTRA, extra)
            args.putString(OPEN_PIN_MODE, mode)
            args.putBoolean(OPEN_FRAGMENT, openFund)
            fragment.arguments = args
            return fragment
        }
    }
}


