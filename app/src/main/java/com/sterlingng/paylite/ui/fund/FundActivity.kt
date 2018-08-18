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
import com.sterlingng.paylite.data.model.AccountDetails
import com.sterlingng.paylite.data.model.Bank
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.confirm.ConfirmActivity
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.CardExpiryTextWatcher
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class FundActivity : BaseActivity(), FundMvpView, FilterBottomSheetFragment.OnFilterItemSelected {

    @Inject
    lateinit var mPresenter: FundMvpContract<FundMvpView>

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mCardExpiryTextView: EditText
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

    private lateinit var mBankNameTextView: TextView
    private lateinit var mAccountNameTextView: TextView
    private lateinit var mAccountNumberTextView: TextView

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
            startActivity(ConfirmActivity.getStartIntent(this))
        }

        mFundBankNestedScrollView.visibility = View.GONE
        mFundCardNestedScrollView.visibility = View.GONE

        mCardTextView.setOnClickListener {
            mBankCheckBox.isChecked = false
            mCardCheckBox.isChecked = true
            mFundBankNestedScrollView.visibility = View.GONE
            mFundCardNestedScrollView.visibility = View.VISIBLE
        }

        mBankTextView.setOnClickListener {
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

        mCardTextView = findViewById(R.id.card_text)
        mBankTextView = findViewById(R.id.bank_text)

        mCardCheckBox = findViewById(R.id.card_checkBox)
        mBankCheckBox = findViewById(R.id.bank_checkBox)

        mBankNameTextView = findViewById(R.id.bank)
        mAccountNameTextView = findViewById(R.id.name)
        mAccountNumberTextView = findViewById(R.id.account_number)

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
        val type = object : TypeToken<java.util.ArrayList<Bank>>() {}.type
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
    }

    override fun onResolveAccountNumberSuccessful(it: Response) {
        val data = gson.fromJson(gson.toJson(it.data), AccountDetails::class.java)
        mAccountNameTextView.text = data.name
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    inner class AccountNumberTextWatcher : TextWatcher {
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

        const val CARD_TYPE = "FundActivity.CARD_TYPE"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, FundActivity::class.java)
        }
    }
}
