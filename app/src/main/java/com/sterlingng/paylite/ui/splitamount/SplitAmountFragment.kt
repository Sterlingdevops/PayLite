package com.sterlingng.paylite.ui.splitamount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.splitcontacts.SplitContactFragment
import javax.inject.Inject

class SplitAmountFragment : BaseFragment(), SplitAmountMvpView {

    @Inject
    lateinit var mPresenter: SplitAmountMvpContract<SplitAmountMvpView>

    private lateinit var mSplitAmountEditText: EditText
    private lateinit var mBalanceTextView: TextView
    private lateinit var mUnequalTextView: TextView
    private lateinit var mUnequalCheckBox: CheckBox
    private lateinit var mEqualTextView: TextView
    private lateinit var mEqualCheckBox: CheckBox
    private lateinit var mNoteEditText: EditText

    private var isEqual: Boolean = false

    lateinit var exit: ImageView
    lateinit var next: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_split_amount, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)

        mNoteEditText = view.findViewById(R.id.note)
        mBalanceTextView = view.findViewById(R.id.balance)

        mEqualCheckBox = view.findViewById(R.id.equal_checkBox)
        mUnequalCheckBox = view.findViewById(R.id.unequal_checkBox)

        mEqualTextView = view.findViewById(R.id.equal_split)
        mUnequalTextView = view.findViewById(R.id.unequal_split)

        mSplitAmountEditText = view.findViewById(R.id.split_amount)
    }

    override fun setUp(view: View) {
        mPresenter.onViewInitialized()

        next.setOnClickListener {
            if (mSplitAmountEditText.text.isEmpty()) {
                show("SplitAmount number is required", true)
                return@setOnClickListener
            }

            (baseActivity as DashboardActivity).mNavController
                    .pushFragment(SplitContactFragment
                            .newInstance(mSplitAmountEditText.text.toString(),
                                    isEqual, mNoteEditText.text.toString()))
        }

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mEqualTextView.setOnClickListener {
            isEqual = true
            mEqualCheckBox.isChecked = true
            mUnequalCheckBox.isChecked = false
        }

        mUnequalTextView.setOnClickListener {
            isEqual = false
            mEqualCheckBox.isChecked = false
            mUnequalCheckBox.isChecked = true
        }
    }

    override fun initView(wallet: Wallet?) {
        mBalanceTextView.text = String.format("Balance ₦%,.2f", wallet?.balance?.toFloat())
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): SplitAmountFragment {
            val fragment = SplitAmountFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}