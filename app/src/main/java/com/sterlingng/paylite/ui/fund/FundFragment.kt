package com.sterlingng.paylite.ui.fund

import android.app.Dialog
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
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.CardExpiryTextWatcher
import mostafa.ma.saleh.gmail.com.editcredit.EditCredit
import javax.inject.Inject

class FundFragment : BaseFragment(), FundMvpView, FilterBottomSheetFragment.OnFilterItemSelected {

    @Inject
    lateinit var mPresenter: FundMvpContract<FundMvpView>

    @Inject
    lateinit var eventBus: EventBus

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mCardNumberEditCredit: EditCredit
    private lateinit var mCardExpiryTextView: EditText
    private lateinit var mCardNameEditText: EditText
    private lateinit var mCardCvvTextView: EditText

    private lateinit var mSaveCardTextView: TextView
    private lateinit var mSaveCardSwitch: Switch

    private lateinit var mCardTextView: TextView
    private lateinit var mBankTextView: TextView

    private lateinit var mCardCheckBox: CheckBox
    private lateinit var mBankCheckBox: CheckBox

    private lateinit var mFundBankNestedScrollView: NestedScrollView
    private lateinit var mFundCardNestedScrollView: NestedScrollView

    private var chosenBank: Bank? = null
    private var banks = ArrayList<Bank>()
    private var isCard = true

    private lateinit var mBankNameTextView: TextView
    private lateinit var mAccountNameTextView: TextView
    private lateinit var mAccountNumberTextView: TextView
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
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        next.setOnClickListener {
            if (isCard && (mCardNameEditText.text.isEmpty() || mCardExpiryTextView.text.isEmpty() || mCardCvvTextView.text.isEmpty() || mCardNumberEditCredit.text.isEmpty())) {
                show("Please fill in the required fields", true)
                return@setOnClickListener
            }

            if (isCard && mFundAmountCardEditText.text.toString().toInt() < 100) {
                show("Amount should be greater than 100", true)
                return@setOnClickListener
            }

            if (isCard && mCardNumberEditCredit.isCardValid) {
                mPresenter.resolveCardNumber(mCardNumberEditCredit.textWithoutSeparator.substring(0, 5))
                return@setOnClickListener
            }

            if (!isCard && (mAccountNumberTextView.length() == 0 || chosenBank == null || mAccountNameTextView.length() == 0 || mFundAmountBankEditText.text.isEmpty())) {
                show("Please fill in the required fields", true)
                return@setOnClickListener
            }

            if (!isCard && mFundAmountBankEditText.text.toString().toInt() < 100) {
                show("Amount should be greater than 100", true)
                return@setOnClickListener
            }

            if (!isCard) {
                val data = HashMap<String, Any>()
                data["amount"] = mFundAmountBankEditText.text.toString().toInt()
                data["account_number"] = mAccountNumberTextView.text.toString().substring(0 until 4)
                data["bank"] = chosenBank?.name!!
                data["comments"] = "Funded Wallet Via Bank Account"
                data["channel"] = "Android Application"
                mPresenter.fundWallet(data)
                return@setOnClickListener
            }
        }

        isCard = true
        mBankCheckBox.isChecked = false
        mCardCheckBox.isChecked = true
        mFundBankNestedScrollView.visibility = View.GONE
        mFundCardNestedScrollView.visibility = View.VISIBLE

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

        mCardExpiryTextView.addTextChangedListener(CardExpiryTextWatcher(mCardExpiryTextView))

        mBankNameTextView.setOnClickListener { _ ->
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.selector = 1
            filterBottomSheetFragment.title = "Select Bank"
            filterBottomSheetFragment.items = banks.map { it.name }
            filterBottomSheetFragment.show(childFragmentManager, "filter")
        }

        mSaveCardTextView.setOnClickListener {
            mSaveCardSwitch.toggle()
        }

        mAccountNumberTextView.addTextChangedListener(AccountNumberTextWatcher())

        mPresenter.loadBanks()
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)

        mCardNumberEditCredit = view.findViewById(R.id.card_number)
        mCardNameEditText = view.findViewById(R.id.card_name)
        mCardTextView = view.findViewById(R.id.card_text)
        mBankTextView = view.findViewById(R.id.bank_text)

        mCardCheckBox = view.findViewById(R.id.card_checkBox)
        mBankCheckBox = view.findViewById(R.id.bank_checkBox)

        mBankNameTextView = view.findViewById(R.id.bank)
        mAccountNameTextView = view.findViewById(R.id.name)
        mAccountNumberTextView = view.findViewById(R.id.account_number)
        mFundAmountBankEditText = view.findViewById(R.id.fund_amount_bank)
        mFundAmountCardEditText = view.findViewById(R.id.fund_amount_card)

        mCardCvvTextView = view.findViewById(R.id.card_cvv)
        mCardExpiryTextView = view.findViewById(R.id.card_expiry)

        mSaveCardTextView = view.findViewById(R.id.save_card)
        mSaveCardSwitch = view.findViewById(R.id.save_card_switch)

        mFundBankNestedScrollView = view.findViewById(R.id.fund_bank)
        mFundCardNestedScrollView = view.findViewById(R.id.fund_card)
    }

    override fun onLoadBanksFailed(it: Throwable) {
        show(it.localizedMessage, true)
    }

    override fun onLoadBanksSuccessful(it: ArrayList<Bank>) {
        banks = it
    }

    override fun onFilterItemSelected(dialog: Dialog, selector: Int, s: String) {
        chosenBank = banks.find { it.name == s }!!
        mBankNameTextView.text = chosenBank?.name

        if (mAccountNumberTextView.text.length == 10) {
            mPresenter.resolveAccountNumber(mAccountNumberTextView.text.toString(), chosenBank?.code!!)
        }
        dialog.dismiss()
    }

    override fun onResolveAccountNumberFailed(it: Throwable) {
        show("Could not resolve account name.", true)
        mAccountNameTextView.text = ""
        hideKeyboard()
    }

    override fun onResolveAccountNumberSuccessful(it: Response) {
        val data = gson.fromJson(gson.toJson(it.data), AccountDetails::class.java)
        mAccountNameTextView.text = data.name
        hideKeyboard()
    }

    override fun onResolveCardNumberFailed(it: Throwable) {
        show(gson.toJson(it.localizedMessage), true)
    }

    override fun onResolveCardNumberSuccessful(it: Response) {
        val data = HashMap<String, Any>()
        data["amount"] = mFundAmountCardEditText.text.toString().toInt()
        data["card_number"] = mCardNumberEditCredit.textWithoutSeparator.substring(0 until 4)
        data["card_type"] = when (mCardNumberEditCredit.mCurrentDrawableResId) {
            R.drawable.visa -> "Visa"
            R.drawable.amex -> "American Express"
            R.drawable.mastercard -> "MasterCard"
            else -> {
                "Card Type"
            }
        }
        data["comments"] = "Funded Wallet Via Credit Card"
        data["channel"] = "Android Application"
        mPresenter.fundWallet(data)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    override fun onFundWalletFailed(it: Response) {
        show(it.message!!, false)
    }

    override fun onFundWalletSuccessful(wallet: Wallet) {
        eventBus.post(UpdateWallet())
        baseActivity.onBackPressed()
    }

    private inner class AccountNumberTextWatcher : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s?.length == 10 && chosenBank != null) {
                mPresenter.resolveAccountNumber(mAccountNumberTextView.text.toString(), chosenBank?.code!!)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }

    companion object {

        fun newInstance(): FundFragment {
            val fragment = FundFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}