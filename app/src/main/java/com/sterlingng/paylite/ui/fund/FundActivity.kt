package com.sterlingng.paylite.ui.fund

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.google.gson.reflect.TypeToken
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.*
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.CardExpiryTextWatcher
import mostafa.ma.saleh.gmail.com.editcredit.EditCredit
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class FundActivity : BaseActivity(), FundMvpView, FilterBottomSheetFragment.OnFilterItemSelected {

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

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fund)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun setUp() {
        exit.setOnClickListener {
            onBackPressed()
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
            filterBottomSheetFragment.show(supportFragmentManager, "filter")
        }

        mSaveCardTextView.setOnClickListener {
            mSaveCardSwitch.toggle()
        }

        mAccountNumberTextView.addTextChangedListener(AccountNumberTextWatcher())

        mPresenter.loadBanks()
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        next = findViewById(R.id.next)

        mCardNumberEditCredit = findViewById(R.id.card_number)
        mCardNameEditText = findViewById(R.id.card_name)
        mCardTextView = findViewById(R.id.card_text)
        mBankTextView = findViewById(R.id.bank_text)

        mCardCheckBox = findViewById(R.id.card_checkBox)
        mBankCheckBox = findViewById(R.id.bank_checkBox)

        mBankNameTextView = findViewById(R.id.bank)
        mAccountNameTextView = findViewById(R.id.name)
        mAccountNumberTextView = findViewById(R.id.account_number)
        mFundAmountBankEditText = findViewById(R.id.fund_amount_bank)
        mFundAmountCardEditText = findViewById(R.id.fund_amount_card)

        mCardCvvTextView = findViewById(R.id.card_cvv)
        mCardExpiryTextView = findViewById(R.id.card_expiry)

        mSaveCardTextView = findViewById(R.id.save_card)
        mSaveCardSwitch = findViewById(R.id.save_card_switch)

        mFundBankNestedScrollView = findViewById(R.id.fund_bank)
        mFundCardNestedScrollView = findViewById(R.id.fund_card)
    }

    override fun onLoadBanksFailed(it: Throwable) {
        show(it.localizedMessage, true)
    }

    override fun onLoadBanksSuccessful(it: Response) {
        val type = object : TypeToken<ArrayList<Bank>>() {}.type
        banks = gson.fromJson(gson.toJson(it.data), type)
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

    override fun onFundWalletFailed(it: Throwable) {
        show("An error occurred while processing the transaction", false)
    }

    override fun onFundWalletSuccessful(wallet: Wallet) {
        eventBus.post(UpdateWallet())
        onBackPressed()
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

        //startActivity(ConfirmActivity.getStartIntent(this))

        const val CARD_TYPE = "FundActivity.CARD_TYPE"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, FundActivity::class.java)
        }
    }
}
