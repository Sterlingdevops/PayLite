package com.sterlingng.paylite.ui.fund

import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.*
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.confirm.ConfirmFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.CardExpiryTextWatcher
import com.sterlingng.paylite.utils.then
import mostafa.ma.saleh.gmail.com.editcredit.EditCredit
import javax.inject.Inject

class FundFragment : BaseFragment(), FundMvpView, ConfirmFragment.OnPinValidated {


    @Inject
    lateinit var mPresenter: FundMvpContract<FundMvpView>

    @Inject
    lateinit var eventBus: EventBus

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mBalanceTextView: TextView

    private lateinit var mCardNumberEditCredit: EditCredit
    private lateinit var mCardExpiryEditText: EditText
    private lateinit var mCardNameEditText: EditText

    private lateinit var mCardCvvEditText: EditText

    private lateinit var mSaveCardTextView: TextView
    private lateinit var mSaveCardSwitch: Switch

    private lateinit var mSaveBankTextView: TextView
    private lateinit var mSaveBankSwitch: Switch

    private lateinit var mCardTextView: TextView
    private lateinit var mBankTextView: TextView

    private lateinit var mCardCheckBox: CheckBox
    private lateinit var mBankCheckBox: CheckBox

    private lateinit var mFundBankNestedScrollView: NestedScrollView
    private lateinit var mFundCardNestedScrollView: NestedScrollView

    private var isCard = true

    private lateinit var mAccountNameTextView: TextView
    private lateinit var mAccountNumberEditText: EditText
    private lateinit var mFundAmountCardEditText: EditText
    private lateinit var mFundAmountBankEditText: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fund, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        mPresenter.loadCachedWallet()

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        val paymentMethod = arguments?.getParcelable<PaymentMethod>(PAYMENT_METHOD)
        if (paymentMethod?.number != "") {
            paymentMethod.run {
                this?.isCard?.let {
                    this@FundFragment.isCard = it
                    mBankCheckBox.isChecked = !it
                    mCardCheckBox.isChecked = it
                    mFundBankNestedScrollView.visibility = it then View.GONE ?: View.VISIBLE
                    mFundCardNestedScrollView.visibility = it then View.VISIBLE ?: View.GONE

                    if (it) {
                        mCardNumberEditCredit.setText(paymentMethod?.number)
                        mCardExpiryEditText.setText(paymentMethod?.expiry)
                        mCardNameEditText.setText(paymentMethod?.name)
                    } else {
                        mAccountNumberEditText.setText(paymentMethod?.number)
                        mAccountNameTextView.text = paymentMethod?.name
                    }
                }
            }
        } else {
            isCard = true
            mBankCheckBox.isChecked = false
            mCardCheckBox.isChecked = true
            mFundBankNestedScrollView.visibility = View.GONE
            mFundCardNestedScrollView.visibility = View.VISIBLE
        }

        next.setOnClickListener {
            if (isCard && (mCardNameEditText.text.isEmpty() || mCardExpiryEditText.text.isEmpty() || mCardCvvEditText.text.isEmpty() || mCardNumberEditCredit.text?.isEmpty()!!)) {
                show("Please fill in the required fields", true)
                return@setOnClickListener
            }

            if (isCard && mFundAmountCardEditText.text.toString().toInt() < 100) {
                show("Amount should be greater than 100", true)
                return@setOnClickListener
            }

            if (isCard) {
                val confirmFragment = ConfirmFragment.newInstance()
                confirmFragment.onPinValidatedListener = this
                (baseActivity as DashboardActivity).mNavController.showDialogFragment(confirmFragment)
                return@setOnClickListener
            }

            if (!isCard && (mAccountNumberEditText.length() == 0 || mAccountNameTextView.length() == 0 || mFundAmountBankEditText.text.isEmpty())) {
                show("Please fill in the required fields", true)
                return@setOnClickListener
            }

            if (!isCard && mFundAmountBankEditText.text.toString().toInt() < 100) {
                show("Amount should be greater than 100", true)
                return@setOnClickListener
            }

            if (!isCard) {
                val confirmFragment = ConfirmFragment.newInstance()
                confirmFragment.onPinValidatedListener = this
                (baseActivity as DashboardActivity).mNavController.showDialogFragment(confirmFragment)
                return@setOnClickListener
            }
        }

        mCardTextView.setOnClickListener {
            isCard = true
            mBankCheckBox.isChecked = false
            mCardCheckBox.isChecked = true
            mFundBankNestedScrollView.visibility = View.GONE
            mFundCardNestedScrollView.visibility = View.VISIBLE
        }

        mBankTextView.setOnClickListener {
            isCard = false
            mBankCheckBox.isChecked = true
            mCardCheckBox.isChecked = false
            mFundBankNestedScrollView.visibility = View.VISIBLE
            mFundCardNestedScrollView.visibility = View.GONE
        }

        mCardExpiryEditText.addTextChangedListener(CardExpiryTextWatcher(mCardExpiryEditText))

        mSaveCardTextView.setOnClickListener {
            mSaveCardSwitch.toggle()
        }

        mSaveBankTextView.setOnClickListener {
            mSaveBankSwitch.toggle()
        }

        mAccountNumberEditText.addTextChangedListener(AccountNumberTextWatcher())
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)

        mBalanceTextView = view.findViewById(R.id.balance)

        mCardNumberEditCredit = view.findViewById(R.id.card_number)
        mCardNameEditText = view.findViewById(R.id.card_name)
        mCardTextView = view.findViewById(R.id.card_text)
        mBankTextView = view.findViewById(R.id.bank_text)

        mCardCheckBox = view.findViewById(R.id.card_checkBox)
        mBankCheckBox = view.findViewById(R.id.bank_checkBox)

        mAccountNameTextView = view.findViewById(R.id.name)
        mAccountNumberEditText = view.findViewById(R.id.account_number)
        mFundAmountBankEditText = view.findViewById(R.id.fund_amount_bank)
        mFundAmountCardEditText = view.findViewById(R.id.fund_amount_card)

        mCardCvvEditText = view.findViewById(R.id.card_cvv)
        mCardExpiryEditText = view.findViewById(R.id.card_expiry)

        mSaveCardTextView = view.findViewById(R.id.save_card)
        mSaveCardSwitch = view.findViewById(R.id.save_card_switch)

        mSaveBankTextView = view.findViewById(R.id.save_bank)
        mSaveBankSwitch = view.findViewById(R.id.save_bank_switch)

        mFundBankNestedScrollView = view.findViewById(R.id.fund_bank)
        mFundCardNestedScrollView = view.findViewById(R.id.fund_card)
    }

    override fun onLoadBanksFailed() {
        show("An error occurred", true)
    }

    override fun onLoadBanksSuccessful(it: ArrayList<Bank>) {

    }

    override fun initView(wallet: Wallet) {
        mBalanceTextView.text = String.format("Balance: ₦%,.0f", wallet.balance.toFloat())
    }

    override fun onResolveAccountNumberFailed() {
        show("Could not resolve account name.", true)
        mAccountNameTextView.text = ""
        hideKeyboard()
    }

    override fun onResolveAccountNumberSuccessful(it: Response) {
        val data = gson.fromJson(gson.toJson(it.data), AccountDetails::class.java)
        mAccountNameTextView.text = data.name
        hideKeyboard()
    }

    override fun onResolveCardNumberFailed() {
        show("An error occurred while processing the transaction", true)
    }

    override fun onResolveCardNumberSuccessful(it: Response) {
        val data = HashMap<String, Any>()
        data["pin"] = "1234"
        data["currency"] = "NGN"
        data["cvv"] = mCardCvvEditText.text.toString()
        data["pan"] = mCardNumberEditCredit.textWithoutSeparator
        data["amount"] = mFundAmountCardEditText.text.toString()
        data["expiry_date"] = with(mCardExpiryEditText.text.toString().split("/")) {
            "${get(1)}${get(0)}"
        }
        mPresenter.fundWalletWithCard(data)
    }

    override fun onGetWalletSuccessful(wallet: Wallet?) {

    }

    override fun onGetWalletFailed() {
        show("An error occurred while processing the transaction", true)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun onPinCorrect() {
        if (isCard) {
            if (mSaveCardSwitch.isChecked) {
                val card = Card()
                card.default = false
                card.name = mCardNameEditText.text.toString()
                card.expiry = mCardExpiryEditText.text.toString()
                card.number = mCardNumberEditCredit.textWithoutSeparator
                mPresenter.saveCard(card)
            }
            mPresenter.resolveCardNumber(mCardNumberEditCredit.textWithoutSeparator.substring(0, 6))
        } else {
            if (mSaveBankSwitch.isChecked) {
                val bank = Bank()
                bank.default = false
                bank.bankcode = "232" // TODO: Update this when we add other banks
                bank.bankname = "Sterling Bank" // TODO: Update this when we add other banks
                bank.accountname = mAccountNameTextView.text.toString()
                bank.accountnumber = mAccountNumberEditText.text.toString()
                mPresenter.saveBank(bank)
            }
            val data = HashMap<String, Any>()
            data["amt"] = mFundAmountBankEditText.text.toString()
            data["frmacct"] = mAccountNumberEditText.text.toString()
            data["remarks"] = "Fund Wallet (Via Bank Account: ${mAccountNumberEditText.text})"
            data["paymentRef"] = System.currentTimeMillis().toString()
            mPresenter.fundWalletWithBankAccount(data)
        }
    }

    override fun onPinIncorrect() {
        show("The PIN entered is incorrect", true)
    }

    override fun onFundWalletFailed() {
        show("An error occurred while processing the transaction", true)
    }

    override fun onFundWalletSuccessful() {
        show("Successfully funded wallet", true)
        eventBus.post(UpdateWallet())
        (baseActivity as DashboardActivity).mNavController.clearStack()
        hideKeyboard()
    }

    private inner class AccountNumberTextWatcher : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s?.length == 10) {
                mPresenter.resolveAccountNumber(mAccountNumberEditText.text.toString(), "232")
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }

    override fun logout() {
        baseActivity.logout()
    }

    companion object {

        private const val PAYMENT_METHOD = "FundFragment.PAYMENT_METHOD"

        @JvmOverloads
        fun newInstance(paymentMethod: PaymentMethod = PaymentMethod()): FundFragment {
            val fragment = FundFragment()
            val args = Bundle()
            args.putParcelable(PAYMENT_METHOD, paymentMethod)
            fragment.arguments = args
            return fragment
        }
    }
}
