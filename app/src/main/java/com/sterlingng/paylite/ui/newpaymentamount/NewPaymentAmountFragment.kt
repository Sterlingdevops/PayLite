package com.sterlingng.paylite.ui.newpaymentamount

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.data.model.SendMoneyRequest
import com.sterlingng.paylite.data.model.UpdateWallet
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.confirm.ConfirmFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.utils.isValidEmail
import com.sterlingng.paylite.utils.then
import mr.robot.scheduleview.ScheduleView
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class NewPaymentAmountFragment : BaseFragment(), NewPaymentAmountMvpView, ConfirmFragment.OnPinValidated {

    @Inject
    lateinit var mPresenter: NewPaymentAmountMvpContract<NewPaymentAmountMvpView>

    @Inject
    lateinit var eventBus: EventBus

    private lateinit var mScheduleView: ScheduleView
    private lateinit var mScheduleTextView: TextView
    private lateinit var mScheduleRepeatSwitch: Switch
    private lateinit var mScheduleReferenceTextView: TextView

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mAmountReferenceEditText: TextView
    private lateinit var mAmountEditText: TextView

    private lateinit var mBalanceTextView: TextView
    private lateinit var mTitleTextView: TextView

    private var request = SendMoneyRequest()

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

        mScheduleView = view.findViewById(R.id.scheduleView)

        mAmountReferenceEditText = view.findViewById(R.id.reference)
        mAmountEditText = view.findViewById(R.id.amount)

        mScheduleReferenceTextView = view.findViewById(R.id.schedule_payment_ref)
        mScheduleRepeatSwitch = view.findViewById(R.id.schedule_switch)
        mScheduleTextView = view.findViewById(R.id.schedule_payment)

        mBalanceTextView = view.findViewById(R.id.balance)
        mTitleTextView = view.findViewById(R.id.title)
    }

    @SuppressLint("SetTextI18n")
    override fun setUp(view: View) {
        mPresenter.loadCachedWallet()
        request = arguments?.getParcelable(REQUEST)!!

        mTitleTextView.text = "Send Money to ${request.recipientName}"

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        next.setOnClickListener {
            if (mAmountEditText.text.toString().isEmpty()) {
                show("Amount should be more than NGN100", true)
                return@setOnClickListener
            }

            try {
                request.amount = mAmountEditText.text.toString()
            } catch (e: NumberFormatException) {
                show("Amount should be more than NGN100", true)
            }

            request.paymentReference = mAmountReferenceEditText.text.toString()

            if (request.amount.toInt() >= 100) {
                val confirmFragment = ConfirmFragment.newInstance()
                confirmFragment.onPinValidatedListener = this
                (baseActivity as DashboardActivity)
                        .mNavController
                        .showDialogFragment(confirmFragment)
            } else {
                show("Amount should be more than NGN100", true)
            }
        }

        mScheduleView.visibility = View.GONE

        mScheduleTextView.setOnClickListener {
            mScheduleRepeatSwitch.toggle()
            mScheduleView.visibility = mScheduleRepeatSwitch.isChecked then View.VISIBLE ?: View.GONE
        }

        mScheduleReferenceTextView.setOnClickListener {
            mScheduleRepeatSwitch.toggle()
            mScheduleView.visibility = mScheduleRepeatSwitch.isChecked then View.VISIBLE ?: View.GONE
        }

        mScheduleRepeatSwitch.setOnCheckedChangeListener { _, isChecked ->
            mScheduleView.visibility = isChecked then View.VISIBLE ?: View.GONE
        }
    }

    override fun logout() {
        baseActivity.logout()
    }

    override fun onPinCorrect() {
        if (mScheduleRepeatSwitch.isChecked) {
            val dateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH)
            var end: Date = dateFormat.parse(mScheduleView.endDate)
            val start: Date = dateFormat.parse(mScheduleView.startDate)
            val interval = when (mScheduleView.frequency.toLowerCase()) {
                "daily" -> 1
                "weekly" -> 2
                "monthly" -> 3
                "yearly" -> 4
                "never" -> 0
                else -> 0
            }

            if (interval == 0) end = start

            if (end.time - start.time >= 0) {
                // The start date is not today
                if (Date().time - start.time < 0) {
                    schedulePayment()
                } else {
                    mPresenter.sendMoney(request.toHashMap())
                }
            } else {
                show("End date cannot come before start date", true)
                return
            }
        } else {
            mPresenter.sendMoney(request.toHashMap())
        }
    }

    override fun onPinIncorrect() {
        show("The PIN entered is incorrect", true)
    }

    override fun initView(wallet: Wallet) {
        mBalanceTextView.text = String.format("Balance: ₦%,.0f", wallet.balance.toFloat())
    }

    override fun onSendMoneySuccessful() {
        if (mScheduleRepeatSwitch.isChecked) {
            schedulePayment()
        } else {
            val contact = arguments?.getParcelable<PayliteContact>(CONTACT)!!
            if (contact.name.isNotEmpty()) mPresenter.saveContact(contact)
            eventBus.post(UpdateWallet())
            (baseActivity as DashboardActivity)
                    .mNavController.clearStack()
//                    .pushFragment(PaymentCategoriesFragment.newInstance())
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
        (baseActivity as DashboardActivity)
                .mNavController.clearStack()
//                .pushFragment(PaymentCategoriesFragment.newInstance())
    }

    override fun onSendMoneyFailed() {
        show("An error occurred while processing the transaction", true)
    }

    private fun schedulePayment() {
        val request = arguments?.getParcelable<SendMoneyRequest>(REQUEST)
        val data = HashMap<String, Any>()

        val dateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH)

        var end: Date = dateFormat.parse(mScheduleView.endDate)
        val start: Date = dateFormat.parse(mScheduleView.startDate)

        data["interval"] = when (mScheduleView.frequency.toLowerCase()) {
            "daily" -> 1
            "weekly" -> 2
            "monthly" -> 3
            "yearly" -> 4
            "never" -> 0
            else -> 0
        }

        if (data["interval"] == 0) end = start
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)

        val endDate = formatter.format(end)
        val startDate = formatter.format(start)

        data["amount"] = mAmountEditText.text.toString()
        data["narration"] = mAmountReferenceEditText.text.toString()
        data["payment_ref"] = System.currentTimeMillis().toString()
        data["beneficiary"] = (request?.email?.isValidEmail()!!) then request.email ?: request.phone
        data["end_date"] = endDate
        data["start_date"] = startDate
        mPresenter.schedulePayment(data)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        private const val REQUEST = "NewPaymentAmountFragment.REQUEST"
        private const val CONTACT = "NewPaymentAmountFragment.CONTACT"

        @JvmOverloads
        fun newInstance(request: SendMoneyRequest, contact: PayliteContact = PayliteContact()): NewPaymentAmountFragment {
            val fragment = NewPaymentAmountFragment()
            val args = Bundle()
            args.putParcelable(REQUEST, request)
            args.putParcelable(CONTACT, contact)
            fragment.arguments = args
            return fragment
        }
    }
}