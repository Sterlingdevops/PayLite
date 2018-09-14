package com.sterlingng.paylite.ui.authpin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.goodiebag.pinview.PinView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import javax.inject.Inject

class AuthPinFragment : BaseFragment(), AuthPinMvpView {

    @Inject
    lateinit var mPresenter: AuthPinMvpContract<AuthPinMvpView>

    private lateinit var mSubTitleTextView: TextView
    private lateinit var mTitleTextView: TextView
    private lateinit var mPinView: PinView
    private lateinit var exit: ImageView
    private lateinit var next: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pin, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)
        mPinView = view.findViewById(R.id.pin_view)
        mTitleTextView = view.findViewById(R.id.textView3)
        mSubTitleTextView = view.findViewById(R.id.textView2)
    }

    override fun setUp(view: View) {


        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mPinView.setPinViewEventListener { _, _ ->
            validate()
        }

        next.setOnClickListener {
            validate()
        }
    }

    private fun validate() {
        val mode = OpenPinMode.valueOf(arguments?.getString(OPEN_PIN_MODE)!!)
        val extra = arguments?.getString(PIN_EXTRA)!!
        when (mode) {
            OpenPinMode.VALIDATE_FOR_USE -> {
                if (mPresenter.validate(mPinView.value)) {
                    (baseActivity as DashboardActivity)
                            .mNavController.clearStack()
                }
            }
            OpenPinMode.ENTER_NEW -> {
                (baseActivity as DashboardActivity)
                        .mNavController
                        .pushFragment(AuthPinFragment.newInstance(OpenPinMode.CONFIRM_NEW.name, extra))
            }
            OpenPinMode.CONFIRM_NEW -> {
                if (mPinView.value == extra) {
                    mPresenter.savePin(extra)
                    (baseActivity as DashboardActivity)
                            .mNavController.clearStack()
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

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        private const val OPEN_PIN_MODE = "MODE"
        private const val PIN_EXTRA = "PIN_EXTRA"

        @JvmOverloads
        fun newInstance(mode: String, extra: String = ""): AuthPinFragment {
            val fragment = AuthPinFragment()
            val args = Bundle()
            args.putString(OPEN_PIN_MODE, mode)
            args.putString(PIN_EXTRA, extra)
            fragment.arguments = args
            return fragment
        }
    }
}

