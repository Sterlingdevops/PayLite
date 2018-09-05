package com.sterlingng.paylite.ui.newpayment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.SendMoneyRequest
import com.sterlingng.paylite.data.model.UpdateWallet
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import com.sterlingng.paylite.utils.AppUtils
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.AppUtils.isJSONValid
import com.tsongkha.spinnerdatepicker.DatePicker
import com.tsongkha.spinnerdatepicker.DatePickerDialog
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


interface NewPaymentAmountMvpContract<V : NewPaymentAmountMvpView> : MvpPresenter<V> {
    fun loadCachedWallet()
    fun sendMoney(data: HashMap<String, Any>)
}

interface NewPaymentAmountMvpView : MvpView {
    fun initView(wallet: Wallet?)
    fun onSendMoneySuccessful(wallet: Wallet)
    fun onSendMoneyFailed(response: Response)
}

class NewPaymentAmountPresenter<V : NewPaymentAmountMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), NewPaymentAmountMvpContract<V> {

    override fun sendMoney(data: HashMap<String, Any>) {
        val user = dataManager.getCurrentUser()
        data["user"] = user?.username!!

        mvpView.showLoading()
        compositeDisposable.add(
                dataManager.sendMoney(data)
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
                                if (isJSONValid(raw!!)) {
                                    return@onErrorReturn gson.fromJson(raw, Response::class.java)
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
                                val wallet = gson.fromJson(AppUtils.gson.toJson(it.data), Wallet::class.java)
                                dataManager.saveWallet(wallet)
                                mvpView.hideLoading()
                                mvpView.onSendMoneySuccessful(wallet)
                            } else {
                                mvpView.hideLoading()
                                mvpView.onSendMoneyFailed(it)
                            }
                        }
        )
    }

    override fun loadCachedWallet() {
        mvpView.initView(dataManager.getWallet())
    }
}

class NewPaymentAmountFragment : BaseFragment(), NewPaymentAmountMvpView, DatePickerDialog.OnDateSetListener,
        FilterBottomSheetFragment.OnFilterItemSelected {

    @Inject
    lateinit var mPresenter: NewPaymentAmountMvpContract<NewPaymentAmountMvpView>

    @Inject
    lateinit var eventBus: EventBus

    private var now = Calendar.getInstance()
    private var type: Int = -1

    private lateinit var mScheduleReferenceTextView: TextView

    private lateinit var mSetStartDateTextView: TextView
    private lateinit var mSetEndDateTextView: TextView

    private lateinit var mStartDateTextView: TextView
    private lateinit var mEndDateTextView: TextView

    private lateinit var mScheduleRepeatSwitch: Switch
    private lateinit var mSetRepeatTextView: TextView

    private lateinit var mScheduleTextView: TextView
    private lateinit var mRepeatTextView: TextView

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mAmountReferenceEditText: TextView
    private lateinit var mAmountEditText: TextView

    private lateinit var mBalanceTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_payment_amount, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)

        mAmountReferenceEditText = view.findViewById(R.id.reference)
        mAmountEditText = view.findViewById(R.id.amount)

        mScheduleReferenceTextView = view.findViewById(R.id.schedule_payment_ref)
        mScheduleRepeatSwitch = view.findViewById(R.id.schedule_switch)
        mScheduleTextView = view.findViewById(R.id.schedule_payment)

        mSetStartDateTextView = view.findViewById(R.id.set_start_date)
        mStartDateTextView = view.findViewById(R.id.start_date)

        mSetEndDateTextView = view.findViewById(R.id.set_end_date)
        mEndDateTextView = view.findViewById(R.id.end_date)

        mSetRepeatTextView = view.findViewById(R.id.set_repeat)
        mRepeatTextView = view.findViewById(R.id.repeat)

        mBalanceTextView = view.findViewById(R.id.balance)
    }

    override fun setUp(view: View) {
        val request = arguments?.getParcelable<SendMoneyRequest>(REQUEST)

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        next.setOnClickListener {
            if (mAmountEditText.text.toString().isEmpty()) {
                show("Amount should be more than NGN100", true)
                return@setOnClickListener
            }

            try {
                request?.amount = mAmountEditText.text.toString().toInt()
            } catch (e: NumberFormatException) {
                show("Amount should be more than NGN100", true)
            }

            if (request == null) {
                show("Request object is null", true)
                return@setOnClickListener
            }

            request.comments = mAmountReferenceEditText.text.toString()

            if (request.amount > 100) {
                mPresenter.sendMoney(request.toHashMap())
            } else {
                show("Amount should be more than NGN100", true)
            }
        }

        mSetStartDateTextView.visibility = View.GONE
        mSetEndDateTextView.visibility = View.GONE
        mStartDateTextView.visibility = View.GONE
        mSetRepeatTextView.visibility = View.GONE
        mEndDateTextView.visibility = View.GONE
        mRepeatTextView.visibility = View.GONE

        val simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.US)
        mStartDateTextView.text = simpleDateFormat.format(Date())
        mEndDateTextView.text = simpleDateFormat.format(Date())

        mScheduleTextView.setOnClickListener {
            if (mScheduleRepeatSwitch.isChecked) {
                mSetStartDateTextView.visibility = View.VISIBLE
                mSetEndDateTextView.visibility = View.VISIBLE
                mStartDateTextView.visibility = View.VISIBLE
                mSetRepeatTextView.visibility = View.VISIBLE
                mEndDateTextView.visibility = View.VISIBLE
                mRepeatTextView.visibility = View.VISIBLE
            } else {
                mSetStartDateTextView.visibility = View.GONE
                mSetEndDateTextView.visibility = View.GONE
                mSetRepeatTextView.visibility = View.GONE
                mStartDateTextView.visibility = View.GONE
                mEndDateTextView.visibility = View.GONE
                mRepeatTextView.visibility = View.GONE
            }
        }

        mScheduleReferenceTextView.setOnClickListener {
            if (mScheduleRepeatSwitch.isChecked) {
                mSetStartDateTextView.visibility = View.VISIBLE
                mSetEndDateTextView.visibility = View.VISIBLE
                mStartDateTextView.visibility = View.VISIBLE
                mSetRepeatTextView.visibility = View.VISIBLE
                mEndDateTextView.visibility = View.VISIBLE
                mRepeatTextView.visibility = View.VISIBLE
            } else {
                mSetStartDateTextView.visibility = View.GONE
                mSetEndDateTextView.visibility = View.GONE
                mSetRepeatTextView.visibility = View.GONE
                mStartDateTextView.visibility = View.GONE
                mEndDateTextView.visibility = View.GONE
                mRepeatTextView.visibility = View.GONE
            }
        }

        mSetRepeatTextView.setOnClickListener {
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.title = "Repeat Payment"
            filterBottomSheetFragment.items = listOf("Never", "Daily", "Weekly", "Monthly", "Yearly")
            filterBottomSheetFragment.show(childFragmentManager, "filter")
        }

        mSetStartDateTextView.setOnClickListener {
            showDatePicker(0)
            hideKeyboard()
        }

        mSetEndDateTextView.setOnClickListener {
            showDatePicker(1)
            hideKeyboard()
        }

        mScheduleRepeatSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mSetStartDateTextView.visibility = View.VISIBLE
                mSetEndDateTextView.visibility = View.VISIBLE
                mStartDateTextView.visibility = View.VISIBLE
                mSetRepeatTextView.visibility = View.VISIBLE
                mEndDateTextView.visibility = View.VISIBLE
                mRepeatTextView.visibility = View.VISIBLE
            } else {
                mSetStartDateTextView.visibility = View.GONE
                mSetEndDateTextView.visibility = View.GONE
                mSetRepeatTextView.visibility = View.GONE
                mStartDateTextView.visibility = View.GONE
                mEndDateTextView.visibility = View.GONE
                mRepeatTextView.visibility = View.GONE
            }
        }
    }

    override fun initView(wallet: Wallet?) {
        mBalanceTextView.text = String.format("Balance ₦%,.2f", wallet?.balance?.toFloat())
    }

    override fun onSendMoneySuccessful(wallet: Wallet) {
        eventBus.post(UpdateWallet())
        val intent = DashboardActivity.getStartIntent(baseActivity)
                .putExtra(DashboardActivity.SELECTED_ITEM, 0)
        startActivity(intent)
    }

    override fun onSendMoneyFailed(response: Response) {
        show(response.message!!, true)
    }

    private fun showDatePicker(type: Int) {
        this.type = type
        SpinnerDatePickerDialogBuilder()
                .context(baseActivity)
                .callback(this@NewPaymentAmountFragment)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .defaultDate(now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH))
                .minDate(now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH))
                .showDaySpinner(true)
                .build()
                .show()
    }

    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = "$year/${monthOfYear + 1}/$dayOfMonth"
        val df = SimpleDateFormat("yyyy/MM/dd", Locale.US)
        val today = df.parse(date)
        val simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.US)
        when (type) {
            0 -> {
                mStartDateTextView.text = simpleDateFormat.format(today)
            }
            1 -> {
                mEndDateTextView.text = simpleDateFormat.format(today)
            }
        }
    }

    override fun onFilterItemSelected(dialog: Dialog, selector: Int, s: String) {
        mRepeatTextView.text = s
        dialog.dismiss()
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        const val REQUEST = "NewPaymentAmountFragment.REQUEST"

        fun newInstance(request: SendMoneyRequest): NewPaymentAmountFragment {
            val fragment = NewPaymentAmountFragment()
            val args = Bundle()
            args.putParcelable(REQUEST, request)
            fragment.arguments = args
            return fragment
        }
    }
}