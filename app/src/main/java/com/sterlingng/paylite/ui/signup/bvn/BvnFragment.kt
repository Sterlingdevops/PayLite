package com.sterlingng.paylite.ui.signup.bvn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import com.sterlingng.paylite.ui.signup.SignUpActivity
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

class BvnFragment : BaseFragment(), BvnMvpView {

    @Inject
    lateinit var mPresenter: BvnMvpContract<BvnMvpView>

    private lateinit var mBvnEditText: LargeLabelEditText
    lateinit var mDidClickNext: OnChildDidClickNext
    lateinit var exit: ImageView
    lateinit var next: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bvn, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next_bvn)
        mBvnEditText = view.findViewById(R.id.bvn)
    }

    override fun setUp(view: View) {
        next.setOnClickListener {
            if (mBvnEditText.text().isEmpty()) {
                show("BVN is required", true)
                return@setOnClickListener
            }

            if (mBvnEditText.text().length != 11) {
                show("BVN should be 11 characters long", true)
                return@setOnClickListener
            }

            val data = (baseActivity as SignUpActivity).signUpRequest.toHashMap()
            data["bvn"] = mBvnEditText.text()
            mPresenter.doSignUp(data)
            hideKeyboard()
        }

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }
    }

    override fun onDoSignUpSuccessful(response: Response) {
        mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, "")
    }

    override fun onDoSignUpFailed(response: Response) {
        show(response.message!!, true)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        private const val INDEX = "BvnFragment.INDEX"

        fun newInstance(index: Int): BvnFragment {
            val fragment = BvnFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}

interface BvnMvpView : MvpView {
    fun onDoSignUpSuccessful(response: Response)
    fun onDoSignUpFailed(response: Response)
}

interface BvnMvpContract<V : BvnMvpView> : MvpPresenter<V> {
    fun doSignUp(data: HashMap<String, Any>)
}

class BvnPresenter<V : BvnMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), BvnMvpContract<V> {

    override fun doSignUp(data: HashMap<String, Any>) {
        mvpView.showLoading()
        compositeDisposable.add(
                dataManager.signup(data, AppUtils.gson.toJson(data).sha256())
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
                                val user = AppUtils.gson.fromJson(AppUtils.gson.toJson(it.data), User::class.java)
                                dataManager.saveUser(user)
                                mvpView.onDoSignUpSuccessful(it)
                            } else {
                                mvpView.onDoSignUpFailed(it)
                            }
                            mvpView.hideLoading()
                        }
        )
    }
}