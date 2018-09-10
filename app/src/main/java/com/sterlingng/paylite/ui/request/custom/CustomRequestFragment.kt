package com.sterlingng.paylite.ui.request.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.isValidEmail
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class CustomRequestFragment : BaseFragment(), CustomRequestMvpView {

    @Inject
    lateinit var mPresenter: CustomRequestMvpContract<CustomRequestMvpView>

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mMessageEditText: EditText
    private lateinit var mAmountEditText: EditText
    private lateinit var mEmailEditText: EditText

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)

        mEmailEditText = view.findViewById(R.id.email)
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

            if (!mEmailEditText.text.toString().isValidEmail()) {
                show("Email isn't properly formatted", true)
                return@setOnClickListener
            }

            val data = HashMap<String, Any>()
            data["email"] = mEmailEditText.text.toString()
            data["amount"] = mAmountEditText.text.toString()
            data["message"] = mMessageEditText.text.toString()
            mPresenter.requestPaymentLink(data)
        }
    }

    override fun onRequestPaymentLinkSent(response: Response) {
        show("Request Sent", true)
        (baseActivity as DashboardActivity).mNavController.popFragment()
    }

    override fun onSendRequestPaymentLinkFailed(response: Response) {
        show("Something went wrong and your request wasn't successfully processed, please try again", true)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

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

interface CustomRequestMvpView : MvpView {
    fun onRequestPaymentLinkSent(response: Response)
    fun onSendRequestPaymentLinkFailed(response: Response)
}

interface CustomRequestMvpContract<V : CustomRequestMvpView> : MvpPresenter<V> {
    fun requestPaymentLink(data: HashMap<String, Any>)
}

class CustomRequestPresenter<V : CustomRequestMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), CustomRequestMvpContract<V> {

    override fun requestPaymentLink(data: HashMap<String, Any>) {
        dataManager.getCurrentUser()?.firstName?.let { data["username"] = it }
        dataManager.getCurrentUser()?.phoneNumber?.let { data["phone"] = it }

        mvpView.showLoading()
        compositeDisposable.add(
                dataManager.requestPaymentLink(data, "Bearer ${dataManager.getCurrentUser()?.accessToken!!}")
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
                                    return@onErrorReturn AppUtils.gson.fromJson(raw, Response::class.java)
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
                                mvpView.onRequestPaymentLinkSent(it)
                            } else {
                                mvpView.onSendRequestPaymentLinkFailed(it)
                            }
                            mvpView.hideLoading()
                        }
        )
    }
}
