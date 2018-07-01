package com.sterlingng.paylite.ui.signup.otp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.goodiebag.pinview.PinView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.utils.OnChildDidClickNext
import javax.inject.Inject

class OtpFragment : BaseFragment(), OtpMvpView {

    @Inject
    lateinit var mPresenter: OtpMvpContract<OtpMvpView>

    lateinit var mDidClickNext: OnChildDidClickNext
    lateinit var next: Button
    lateinit var resend: Button
    private lateinit var pinView: PinView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_otp, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        pinView.setPinViewEventListener { _, _ ->
            mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!)
            hideKeyboard()
        }
        resend.setOnClickListener {
            show("Resending token", true)
        }
        next.setOnClickListener {
            mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!)
            hideKeyboard()
        }
    }

    override fun bindViews(view: View) {
        next = view.findViewById(R.id.next)
        resend = view.findViewById(R.id.resend)
        pinView = view.findViewById(R.id.pin_view)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        private const val INDEX = "OtpFragment.INDEX"

        fun newInstance(index: Int): OtpFragment {
            val fragment = OtpFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}
