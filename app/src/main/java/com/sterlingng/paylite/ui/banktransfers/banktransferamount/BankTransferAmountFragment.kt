package com.sterlingng.paylite.ui.banktransfers.banktransferamount

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
import com.sterlingng.paylite.data.model.CashOutToBankAccountRequest
import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.data.model.UpdateWallet
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.confirm.ConfirmFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import com.sterlingng.paylite.utils.then
import com.tsongkha.spinnerdatepicker.DatePicker
import com.tsongkha.spinnerdatepicker.DatePickerDialog
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class BankTransferAmountFragment : BaseFragment(), BankTransferAmountMvpView,
        DatePickerDialog.OnDateSetListener,
        FilterBottomSheetFragment.OnFilterItemSelected, ConfirmFragment.OnPinValidated {

    @Inject
    lateinit var mPresenter: BankTransferAmountMvpContract<BankTransferAmountMvpView>

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

    private var cashOutRequest = CashOutToBankAccountRequest()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bank_transfer_amount, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        cashOutRequest = arguments?.getParcelable(CASH_OUT_REQUEST)!!
        mPresenter.loadCachedWallet()

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        next.setOnClickListener {
            if (mAmountEditText.text.toString().isEmpty()) {
                show("Amount should be more than NGN100", true)
                return@setOnClickListener
            }

            try {
                cashOutRequest.amount = mAmountEditText.text.toString().toInt()
            } catch (e: NumberFormatException) {
                show("Amount should be more than NGN100", true)
            }

            cashOutRequest.paymentReference = mAmountReferenceEditText.text.toString()

            if (cashOutRequest.amount >= 100) {
                val confirmFragment = ConfirmFragment.newInstance()
                confirmFragment.onPinValidatedListener = this
                (baseActivity as DashboardActivity)
                        .mNavController
                        .showDialogFragment(confirmFragment)
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
            mScheduleRepeatSwitch.toggle()
            if (mScheduleRepeatSwitch.isChecked) {
                mSetEndDateTextView.visibility = (mRepeatTextView.text.toString().toLowerCase() == "never") then View.GONE ?: View.VISIBLE
                mSetStartDateTextView.visibility = View.VISIBLE
                mStartDateTextView.visibility = View.VISIBLE
                mSetRepeatTextView.visibility = View.VISIBLE
                mEndDateTextView.visibility = View.VISIBLE
                mRepeatTextView.visibility = View.VISIBLE
            } else {
                mSetEndDateTextView.visibility = View.GONE
                mSetStartDateTextView.visibility = View.GONE
                mSetRepeatTextView.visibility = View.GONE
                mStartDateTextView.visibility = View.GONE
                mEndDateTextView.visibility = View.GONE
                mRepeatTextView.visibility = View.GONE
            }
        }

        mScheduleReferenceTextView.setOnClickListener {
            mScheduleRepeatSwitch.toggle()
            if (mScheduleRepeatSwitch.isChecked) {
                mSetEndDateTextView.visibility = (mRepeatTextView.text.toString().toLowerCase() == "never") then View.GONE ?: View.VISIBLE
                mSetStartDateTextView.visibility = View.VISIBLE
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
            filterBottomSheetFragment.show(baseActivity.supportFragmentManager, "filter")
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
                mSetEndDateTextView.visibility = (mRepeatTextView.text.toString().toLowerCase() == "never") then View.GONE ?: View.VISIBLE
                mSetStartDateTextView.visibility = View.VISIBLE
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

    override fun onPinCorrect() {
        if (cashOutRequest.destinationBankCode == "000001")
            mPresenter.bankTransferSterling(cashOutRequest.toHashMap())
        else mPresenter.bankTransfer(cashOutRequest.toHashMap())
    }

    override fun onPinIncorrect() {
        show("The PIN entered is incorrect", true)
    }

    override fun initView(wallet: Wallet) {
        mBalanceTextView.text = String.format("Balance: ₦%,.0f", wallet.balance.toFloat())
    }

    override fun logout() {
        baseActivity.logout()
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

    private fun showDatePicker(type: Int) {
        this.type = type
        SpinnerDatePickerDialogBuilder()
                .context(baseActivity)
                .callback(this@BankTransferAmountFragment)
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

    override fun onBankTransferSuccessful() {
        if (mScheduleRepeatSwitch.isChecked) {
            val data = HashMap<String, Any>()

            val dateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH)

            val end: Date = dateFormat.parse(mEndDateTextView.text.toString())
            val start: Date = dateFormat.parse(mStartDateTextView.text.toString())

            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)

            val endDate = formatter.format(end)
            val startDate = formatter.format(start)

            data["interval"] = when (mRepeatTextView.text.toString().toLowerCase()) {
                "daily" -> 1
                "weekly" -> 2
                "monthly" -> 3
                "yearly" -> 4
                else -> 0
            }
            data["amount"] = mAmountEditText.text.toString()
            data["narration"] = mAmountReferenceEditText.text.toString()
            data["payment_ref"] = System.currentTimeMillis().toString()
            data["beneficiary"] = "" //(request?.email?.isValidEmail()!!) then request.email ?: request.phone
            data["end_date"] = endDate
            data["start_date"] = startDate
            mPresenter.schedulePayment(data)
        } else {
            val contact = arguments?.getParcelable<PayliteContact>(CONTACT)!!
            if (contact.name.isNotEmpty()) mPresenter.saveContact(contact)
            eventBus.post(UpdateWallet())
            show("Transaction successful", true)
            (baseActivity as DashboardActivity).mNavController.clearStack()
            hideKeyboard()
        }
    }

    override fun onSchedulePaymentFailed() {
        show("An error occurred while processing the transaction", true)
    }

    override fun onSchedulePaymentSuccessful() {
        val contact = arguments?.getParcelable<PayliteContact>(CONTACT)!!
        if (contact.name.isNotEmpty()) mPresenter.saveContact(contact)
        eventBus.post(UpdateWallet())
        (baseActivity as DashboardActivity).mNavController.clearStack()
        hideKeyboard()
    }

    override fun onBankTransferFailed() {
        show("Server timed out, please try again", true)
    }

    override fun onFilterItemSelected(dialog: Dialog, selector: Int, s: String) {
        mRepeatTextView.text = s
        when (s.toLowerCase()) {
            "never" -> {
                mSetEndDateTextView.visibility = View.GONE
            }

            "daily" -> {
                mSetEndDateTextView.visibility = View.VISIBLE
            }

            "weekly" -> {
                mSetEndDateTextView.visibility = View.VISIBLE
            }

            "monthly" -> {
                mSetEndDateTextView.visibility = View.VISIBLE
            }

            "yearly" -> {
                mSetEndDateTextView.visibility = View.VISIBLE
            }
        }
        dialog.dismiss()
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        private const val CONTACT = "BankTransferAmountFragment.CONTACT"
        private const val CASH_OUT_REQUEST = "BankTransferAmountFragment.CASH_OUT_REQUEST"

        @JvmOverloads
        fun newInstance(cashOutRequest: CashOutToBankAccountRequest, contact: PayliteContact = PayliteContact()): BankTransferAmountFragment {
            val fragment = BankTransferAmountFragment()
            val args = Bundle()
            args.putParcelable(CONTACT, contact)
            args.putParcelable(CASH_OUT_REQUEST, cashOutRequest)
            fragment.arguments = args
            return fragment
        }
    }
}
