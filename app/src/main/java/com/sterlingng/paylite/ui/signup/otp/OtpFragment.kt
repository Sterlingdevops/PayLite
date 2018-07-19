package com.sterlingng.paylite.ui.signup.otp

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.goodiebag.pinview.PinView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.utils.Log
import com.sterlingng.paylite.utils.OnChildDidClickNext
import com.sterlingng.paylite.utils.get
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OtpFragment : BaseFragment(), OtpMvpView {

    @Inject
    lateinit var mPresenter: OtpMvpContract<OtpMvpView>

    lateinit var mDidClickNext: OnChildDidClickNext
    lateinit var next: Button
    lateinit var resend: Button
    private lateinit var pinView: PinView
    lateinit var disposable: Disposable

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_otp, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        next = view.findViewById(R.id.next_otp)
        resend = view.findViewById(R.id.resend)
        pinView = view.findViewById(R.id.pin_view)
    }

    override fun setUp(view: View) {
        Log.d((next.parent as ConstraintLayout)[next].toString())

        disposable = Flowable.interval(1, TimeUnit.SECONDS, Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it > 0)
                        resend.text = "Resend code in ${(20 - it) % 20}"
                }

        pinView.setPinViewEventListener { _, _ ->
            mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!)
            disposable.dispose()
            hideKeyboard()
        }
        resend.setOnClickListener {
            show("Resending token", true)
            disposable.dispose()
            hideKeyboard()
        }
        next.setOnClickListener {
            mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!)
            disposable.dispose()
            hideKeyboard()
        }
    }

    override fun onDetach() {
        super.onDetach()
        disposable.dispose()
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
