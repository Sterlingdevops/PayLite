package com.sterlingng.paylite.ui.fund

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.view.View
import android.widget.*
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.confirm.ConfirmActivity
import com.sterlingng.paylite.utils.CardExpiryTextWatcher
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class FundActivity : BaseActivity(), FundMvpView {

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

        mSaveCardTextView.setOnClickListener {
            mSaveCardSwitch.toggle()
        }
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        next = findViewById(R.id.next)

        mCardTextView = findViewById(R.id.card_text)
        mBankTextView = findViewById(R.id.bank_text)

        mCardCheckBox = findViewById(R.id.card_checkBox)
        mBankCheckBox = findViewById(R.id.bank_checkBox)

        mCardCvvTextView = findViewById(R.id.card_cvv)
        mCardExpiryTextView = findViewById(R.id.card_expiry)

        mSaveCardTextView = findViewById(R.id.save_card)
        mSaveCardSwitch = findViewById(R.id.save_card_switch)

        mFundBankNestedScrollView = findViewById(R.id.fund_bank)
        mFundCardNestedScrollView = findViewById(R.id.fund_card)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        const val CARD_TYPE = "FundActivity.CARD_TYPE"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, FundActivity::class.java)
        }
    }
}
