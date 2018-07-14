package com.sterlingng.paylite.ui.fund

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.confirm.ConfirmActivity
import com.sterlingng.paylite.ui.fund.amount.AmountBottomSheetFragment
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class FundActivity : BaseActivity(), FundMvpView, AmountBottomSheetFragment.OnAddAmount {

    @Inject
    lateinit var mPresenter: FundMvpContract<FundMvpView>

    var mCardAmount: String = ""
    private lateinit var exit: ImageView
    private lateinit var next: Button
    private lateinit var mAmountTextView: TextView
    private lateinit var mCardLogoImageView: ImageView

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
        mCardLogoImageView.setImageDrawable(ContextCompat.getDrawable(this, intent.getIntExtra(CARD_TYPE, R.drawable.creditcard)))

        exit.setOnClickListener {
            onBackPressed()
        }

        val amountBottomSheetFragment = AmountBottomSheetFragment.newInstance()
        amountBottomSheetFragment.mCardAmount = mCardAmount
        amountBottomSheetFragment.onAddAmountListener = this
        amountBottomSheetFragment.show(supportFragmentManager, "amount")
        mAmountTextView.setOnClickListener {
            amountBottomSheetFragment.mCardAmount = mCardAmount
            amountBottomSheetFragment.show(supportFragmentManager, "amount")
        }
        next.setOnClickListener {
            startActivity(ConfirmActivity.getStartIntent(this))
        }
    }

    override fun onAddAmount(dialog: Dialog, amount: String) {
        mAmountTextView.text = amount
        mCardAmount = amount
        dialog.dismiss()
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        next = findViewById(R.id.next)
        mAmountTextView = findViewById(R.id.amount)
        mCardLogoImageView = findViewById(R.id.card_logo)
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
