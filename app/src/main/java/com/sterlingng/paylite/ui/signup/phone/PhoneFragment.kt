package com.sterlingng.paylite.ui.signup.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.OnChildDidClickNext
import com.sterlingng.paylite.utils.sha256
import com.sterlingng.views.LargeLabelEditText
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class PhoneFragment : BaseFragment(), PhoneMvpView {

    @Inject
    lateinit var mPresenter: PhoneMvpContract<PhoneMvpView>

    private lateinit var mPhoneEditText: LargeLabelEditText
    lateinit var mDidClickNext: OnChildDidClickNext
    lateinit var exit: ImageView
    lateinit var next: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_phone, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next_phone)
        mPhoneEditText = view.findViewById(R.id.phone)
    }

    override fun setUp(view: View) {
        next.setOnClickListener {
            if (mPhoneEditText.text().isEmpty() || mPhoneEditText.text().length != 10) {
                show("Phone number is required", true)
                return@setOnClickListener
            }

            val data = HashMap<String, Any>()
            data["mobile"] = "0${mPhoneEditText.text()}"
            mPresenter.sendOtp(data)
        }

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }
    }

    override fun onSendOTPFailed(it: Response) {
        show("Error sending OTP. Please ensure you've entered a valid phone number $it", true)
    }

    override fun onSendOTPSuccessful(it: Response) {
        mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, mPhoneEditText.text())
    }


    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        private const val INDEX = "PhoneFragment.INDEX"

        fun newInstance(index: Int): PhoneFragment {
            val fragment = PhoneFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}

interface PhoneMvpView : MvpView {
    fun onSendOTPFailed(it: Response)
    fun onSendOTPSuccessful(it: Response)
}

interface PhoneMvpContract<V : PhoneMvpView> : MvpPresenter<V> {
    fun sendOtp(data: HashMap<String, Any>)
}

class PhonePresenter<V : PhoneMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), PhoneMvpContract<V> {
    override fun sendOtp(data: HashMap<String, Any>) {
        mvpView.showLoading()
        compositeDisposable.add(
                dataManager.sendOtp(data, AppUtils.gson.toJson(data).sha256())
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .onErrorReturn {
                            if (it is java.net.SocketTimeoutException) {
                                val response = Response()
                                response.data = SocketTimeoutException()
                                response.message = "Error!!! The server didn't respond fast enough and the request timed out"
                                response.response = "failed"
                                return@onErrorReturn response
                            } else {
                                val raw = (it as HttpException).response().errorBody()?.string()
                                if (AppUtils.isJSONValid(raw!!)) {
                                    val response = AppUtils.gson.fromJson(raw, Response::class.java)
                                    response.code = it.code()
                                    return@onErrorReturn response
                                }
                                val response = Response()
                                response.data = HttpException(retrofit2.Response.error<String>(500,
                                        ResponseBody.create(MediaType.parse("text/html; charset=utf-8"), raw)))
                                response.message = "Error!!! The server didn't respond fast enough and the request timed out"
                                response.response = "failed"
                                return@onErrorReturn response
                            }
                        }
                        .subscribe {
                            if (it.response != null && it.response == "00") {
                                mvpView.onSendOTPSuccessful(it)
                            } else {
                                mvpView.onSendOTPFailed(it)
                            }
                            mvpView.hideLoading()
                        }
        )
    }
}