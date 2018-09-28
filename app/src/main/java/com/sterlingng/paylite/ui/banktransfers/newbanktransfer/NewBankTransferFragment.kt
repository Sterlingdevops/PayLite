package com.sterlingng.paylite.ui.banktransfers.newbanktransfer

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Bank
import com.sterlingng.paylite.data.model.BankNameEnquiry
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import javax.inject.Inject

class NewBankTransferFragment : BaseFragment(), NewBankTransferMvpView,
        FilterBottomSheetFragment.OnFilterItemSelected {

    @Inject
    lateinit var mPresenter: NewBankTransferMvpContract<NewBankTransferMvpView>

    private lateinit var mAccountNumberEditText: EditText
    private lateinit var mSaveRecipientTextView: TextView
    private lateinit var mAccountNameTextView: TextView
    private lateinit var mChooseBankTextView: TextView
    private lateinit var mSaveRecipientSwitch: Switch

    private var bankCode: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_bank_transfer, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        mSaveRecipientTextView.setOnClickListener {
            mSaveRecipientSwitch.toggle()
        }

        mAccountNumberEditText.addTextChangedListener(AccountNumberTextWatcher())

        mChooseBankTextView.setOnClickListener { _ ->
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.selector = 1
            filterBottomSheetFragment.title = "Banks"
            filterBottomSheetFragment.items = (baseActivity as DashboardActivity).banks.map { bank: Bank -> bank.bankname }
            filterBottomSheetFragment.show(childFragmentManager, "filter")
        }
    }

    override fun bindViews(view: View) {
        mChooseBankTextView = view.findViewById(R.id.choose_bank)
        mAccountNameTextView = view.findViewById(R.id.account_name)
        mAccountNumberEditText = view.findViewById(R.id.account_number)
        mSaveRecipientTextView = view.findViewById(R.id.save_recipient)
        mSaveRecipientSwitch = view.findViewById(R.id.save_recipient_switch)
    }

    override fun onFilterItemSelected(dialog: Dialog, selector: Int, s: String) {
        mChooseBankTextView.text = s
        bankCode = (baseActivity as DashboardActivity).banks.filter { it.bankname == s }[0].bankcode
        if (mAccountNumberEditText.length() == 10) {
            mPresenter.resolveBankAccount(mAccountNumberEditText.text.toString(), bankCode)
        }
        dialog.dismiss()
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }


    override fun onResolveBankAccountFailed() {
        show("Unable to validate account number for the selected bank", true)
    }

    override fun onResolveBankAccountSuccessful(bankNameEnquiry: BankNameEnquiry) {
        mAccountNameTextView.text = bankNameEnquiry.beneficiaryName
    }

    override fun logout() {
        baseActivity.logout()
    }

    private inner class AccountNumberTextWatcher : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s?.length == 10 && bankCode.isNotEmpty()) {
                mPresenter.resolveBankAccount(mAccountNumberEditText.text.toString(), bankCode)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }

    companion object {

        fun newInstance(): NewBankTransferFragment {
            val fragment = NewBankTransferFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }
}
