package com.sterlingng.paylite.ui.cashoutbank

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import com.sterlingng.views.ClickToSelectEditText
import javax.inject.Inject

class CashOutFragment : BaseFragment(), CashOutMvpView, FilterBottomSheetFragment.OnFilterItemSelected {

    @Inject
    lateinit var mPresenter: CashOutMvpContract<CashOutMvpView>

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mCardEditText: ClickToSelectEditText<String>
    private lateinit var mAmountEditText: EditText

    private lateinit var mBalanceTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cash_out, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)

        mCardEditText = view.findViewById(R.id.card)
        mAmountEditText = view.findViewById(R.id.amount)

        mBalanceTextView = view.findViewById(R.id.balance)
    }

    override fun setUp(view: View) {
        mPresenter.loadCachedWallet()

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mCardEditText.setOnClickListener {
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.selector = 1
            filterBottomSheetFragment.title = "Choose an Account"
            filterBottomSheetFragment.items = listOf("Savings Account - 0123456789", "Savings Account - 0247966933", "Savings Account - 0121702158 ", "Other Banks")
            filterBottomSheetFragment.show(childFragmentManager, "filter")
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

    override fun initView(wallet: Wallet?) {
        mBalanceTextView.text = String.format("Balance: ₦%,.0f", wallet?.balance?.toFloat())
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): CashOutFragment {
            val fragment = CashOutFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
