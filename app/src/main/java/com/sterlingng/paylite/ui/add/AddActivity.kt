package com.sterlingng.paylite.ui.add

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
import com.sterlingng.paylite.ui.add.choose.Card
import com.sterlingng.paylite.ui.add.choose.ChooseBottomSheetFragment
import com.sterlingng.paylite.ui.add.cvv.CvvBottomSheetFragment
import com.sterlingng.paylite.ui.add.expiry.ExpiryBottomSheetFragment
import com.sterlingng.paylite.ui.add.number.NumberBottomSheetFragment
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.fund.FundActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class AddActivity : BaseActivity(), AddMvpView,
        ChooseBottomSheetFragment.OnChooseCardSelected,
        NumberBottomSheetFragment.OnAdd,
        ExpiryBottomSheetFragment.OnAddExpiry,
        CvvBottomSheetFragment.OnAddCvv {

    @Inject
    lateinit var mPresenter: AddMvpContract<AddMvpView>

    var mCardNumber: String = ""
    var mCardExpiry: String = ""
    var mCardValid: Boolean = false
    var mCardImageDrawable: Int = 0

    private lateinit var exit: ImageView
    private lateinit var next: Button
    private lateinit var mCardCVVTextView: TextView
    private lateinit var mCardTypeTextView: TextView
    private lateinit var mCardExpiryTextView: TextView
    private lateinit var mCardNumberTextView: TextView
    private lateinit var mCardLogoImageView: ImageView

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun setUp() {
        exit.setOnClickListener {
            onBackPressed()
        }

        next.setOnClickListener {
            if (mCardCVVTextView.length() == 3 && mCardExpiry.length == 5 && mCardValid) {
                val intent = FundActivity.getStartIntent(this)
                        .putExtra(FundActivity.CARD_TYPE, mCardImageDrawable)
                startActivity(intent)
            }
        }

//        val chooseBottomSheetFragment = ChooseBottomSheetFragment.newInstance()
//        chooseBottomSheetFragment.onChooseCardSelectedListener = this
//        chooseBottomSheetFragment.title = "Choose your card type"
//        chooseBottomSheetFragment.items = listOf(
//                Card("American Express", R.drawable.amex),
//                Card("MasterCard", R.drawable.mastercard),
//                Card("Visa", R.drawable.visa)
//        )
//        chooseBottomSheetFragment.show(supportFragmentManager, "choose")

//        mCardLogoImageView.setOnClickListener {
//            chooseBottomSheetFragment.show(supportFragmentManager, "choose")
//        }

        val numberBottomSheetFragment = NumberBottomSheetFragment.newInstance()
        numberBottomSheetFragment.onAddListener = this
        numberBottomSheetFragment.mCardNumber = mCardNumber
        numberBottomSheetFragment.show(supportFragmentManager, "card number")

        mCardNumberTextView.setOnClickListener {
            numberBottomSheetFragment.mCardNumber = mCardNumber
            numberBottomSheetFragment.show(supportFragmentManager, "card number")
        }

        mCardExpiryTextView.setOnClickListener {
            val expiryBottomSheetFragment = ExpiryBottomSheetFragment.newInstance()
            expiryBottomSheetFragment.onAddExpiryListener = this
            expiryBottomSheetFragment.mCardExpiry = mCardExpiry
            expiryBottomSheetFragment.show(supportFragmentManager, "card expiry")
        }

        mCardCVVTextView.setOnClickListener {
            val cardCvvBottomSheetFragment = CvvBottomSheetFragment.newInstance()
            cardCvvBottomSheetFragment.onAddCvvListener = this
            cardCvvBottomSheetFragment.show(supportFragmentManager, "card cvv")
        }
    }

    override fun onChooseCardSelected(dialog: Dialog, selector: Int, s: Card) {
        mCardLogoImageView.setImageDrawable(ContextCompat.getDrawable(this, s.logo))
        mCardImageDrawable = s.logo
        mCardTypeTextView.text = s.name
        dialog.dismiss()
    }

    override fun onAddNumber(dialog: Dialog, cardNumber: String, logo: Int, valid: Boolean) {
        val cardName = when (logo) {
            R.drawable.visa -> "Visa"
            R.drawable.amex -> "American Express"
            R.drawable.mastercard -> "MasterCard"
            else -> {
                "Card Type"
            }
        }
        this.mCardNumber = cardNumber
        mCardTypeTextView.text = cardName
        mCardLogoImageView.setImageDrawable(ContextCompat.getDrawable(this, logo))
        mCardImageDrawable = logo
        if (valid) mCardNumberTextView.text = cardNumber.subSequence(cardNumber.length - 4, cardNumber.length) else mCardNumberTextView.text = "0000"
        mCardValid = valid
        dialog.dismiss()
    }

    override fun onAddExpiry(dialog: Dialog, expiry: String) {
        if (expiry.length == 5) {
            mCardExpiryTextView.text = expiry
            this.mCardExpiry = expiry
        } else {
            mCardExpiryTextView.text = "MM/YY"
            this.mCardExpiry = "MM/YY"
        }
        dialog.dismiss()
    }

    override fun onAddCvv(dialog: Dialog, cvv: String) {
        mCardCVVTextView.text = if (cvv.length == 3) "***" else "CVV"
        dialog.dismiss()
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        next = findViewById(R.id.next)
        mCardCVVTextView = findViewById(R.id.card_cvv)
        mCardTypeTextView = findViewById(R.id.card_type)
        mCardLogoImageView = findViewById(R.id.card_logo)
        mCardExpiryTextView = findViewById(R.id.card_expiry)
        mCardNumberTextView = findViewById(R.id.card_number)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, AddActivity::class.java)
        }
    }
}
