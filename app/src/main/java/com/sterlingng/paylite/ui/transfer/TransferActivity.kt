package com.sterlingng.paylite.ui.transfer

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import com.sterlingng.views.ClickToSelectEditText
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class TransferActivity : BaseActivity(), TransferMvpView, FilterBottomSheetFragment.OnFilterItemSelected {

    @Inject
    lateinit var mPresenter: TransferMvpContract<TransferMvpView>

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mCardEditText: ClickToSelectEditText<String>
    private lateinit var mAmountEditText: EditText

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        next = findViewById(R.id.next)

        mCardEditText = findViewById(R.id.card)
        mAmountEditText = findViewById(R.id.amount)
    }

    override fun setUp() {
        exit.setOnClickListener {
            onBackPressed()
        }

        mCardEditText.setOnClickListener {
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.selector = 1
            filterBottomSheetFragment.title = "Choose an Account"
            filterBottomSheetFragment.items = listOf("Savings Account - 0123456789", "Savings Account - 0247966933", "Savings Account - 0121702158 ", "Other Banks")
            filterBottomSheetFragment.show(supportFragmentManager, "filter")
        }
    }

    override fun onFilterItemSelected(dialog: Dialog, selector: Int, s: String) {
        when (selector) {
            1 -> {
                mCardEditText.setText(s)
            }
        }
        dialog.dismiss()
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, TransferActivity::class.java)
        }
    }
}
