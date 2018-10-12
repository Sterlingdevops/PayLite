package com.sterlingng.paylite.ui.getcash

import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.UpdateWallet
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import javax.inject.Inject

class GetCashFragment : BaseFragment(), GetCashMvpView {

    @Inject
    lateinit var mPresenter: GetCashMvpContract<GetCashMvpView>

    @Inject
    lateinit var eventBus: EventBus

    private lateinit var mBalanceTextView: TextView

    private lateinit var mSelfTextView: TextView
    private lateinit var mSelfCheckBox: CheckBox

    private lateinit var mOthersTextView: TextView
    private lateinit var mOthersCheckBox: CheckBox

    private lateinit var mSelfNestedScrollView: NestedScrollView
    private lateinit var mOthersNestedScrollView: NestedScrollView

    private lateinit var mAmountTextView: TextView
    private lateinit var mPasswordTextView: TextView

    private lateinit var mOtherPhoneTextView: TextView
    private lateinit var mOtherAmountTextView: TextView
    private lateinit var mOtherPasswordTextView: TextView

    private var others: Boolean = false

    lateinit var exit: ImageView
    lateinit var next: Button

    override fun setUp(view: View) {
        mPresenter.onViewInitialized()

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        next.setOnClickListener {
            val data = HashMap<String, Any>()

            if (others) {
                data["mobile"] = mOtherPhoneTextView.text.toString()
                data["Amount"] = mOtherAmountTextView.text.toString()
                data["OneTimePin"] = mOtherPasswordTextView.text.toString()
                mPresenter.cashOutViaPayCode(data, false)
            } else {
                data["Amount"] = mAmountTextView.text.toString()
                data["OneTimePin"] = mPasswordTextView.text.toString()
                mPresenter.cashOutViaPayCode(data, true)
            }
        }

        others = false
        mSelfCheckBox.isChecked = true
        mOthersCheckBox.isChecked = false
        mOthersNestedScrollView.visibility = View.GONE
        mSelfNestedScrollView.visibility = View.VISIBLE

        mSelfTextView.setOnClickListener {
            others = false
            mSelfCheckBox.isChecked = true
            mOthersCheckBox.isChecked = false
            mOthersNestedScrollView.visibility = View.GONE
            mSelfNestedScrollView.visibility = View.VISIBLE
        }

        mOthersTextView.setOnClickListener {
            others = true
            mSelfCheckBox.isChecked = false
            mOthersCheckBox.isChecked = true
            mOthersNestedScrollView.visibility = View.VISIBLE
            mSelfNestedScrollView.visibility = View.GONE
        }
    }

    override fun bindViews(view: View) {
        mBalanceTextView = view.findViewById(R.id.balance)

        mSelfTextView = view.findViewById(R.id.self)
        mSelfCheckBox = view.findViewById(R.id.self_checkBox)

        mOthersTextView = view.findViewById(R.id.others)
        mOthersCheckBox = view.findViewById(R.id.others_checkBox)

        next = view.findViewById(R.id.next)
        exit = view.findViewById(R.id.exit)

        mAmountTextView = view.findViewById(R.id.amount)
        mPasswordTextView = view.findViewById(R.id.password)

        mOtherPhoneTextView = view.findViewById(R.id.phone_other)
        mOtherAmountTextView = view.findViewById(R.id.amount_other)
        mOtherPasswordTextView = view.findViewById(R.id.password_other)

        mSelfNestedScrollView = view.findViewById(R.id.self_scrollview)
        mOthersNestedScrollView = view.findViewById(R.id.others_scrollview)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun onCashOutSuccessful() {
        eventBus.post(UpdateWallet())
        (baseActivity as DashboardActivity)
                .mNavController.clearStack()
    }

    override fun onCashOutFailed() {
        show("An error occurred while processing the transaction", true)
    }

    override fun logout() {
        baseActivity.logout()
    }

    override fun initView(wallet: Wallet) {
        mBalanceTextView.text = String.format("₦%,.2f", wallet.balance.toFloat())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_get_cash, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    companion object {

        fun newInstance(): GetCashFragment {
            val fragment = GetCashFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}