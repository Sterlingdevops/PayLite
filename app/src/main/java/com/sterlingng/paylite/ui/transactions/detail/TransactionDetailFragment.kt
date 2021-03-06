package com.sterlingng.paylite.ui.transactions.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.data.model.UpdateTransaction
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.transactions.paymentcategory.PaymentCategoriesFragment
import com.sterlingng.paylite.utils.Log
import com.sterlingng.paylite.utils.then
import com.sterlingng.views.TitleLabelTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TransactionDetailFragment : BaseFragment(), TransactionDetailMvpView {

    @Inject
    lateinit var mPresenter: TransactionDetailMvpContract<TransactionDetailMvpView>

    @Inject
    lateinit var eventBus: EventBus

    private lateinit var mReceivedTitleLabelTextView: TitleLabelTextView
    private lateinit var mCountTitleLabelTextView: TitleLabelTextView
    private lateinit var mSentTitleLabelTextView: TitleLabelTextView

    private lateinit var mCategoryTextView: TextView
    private lateinit var mHistoryTextView: TextView
    private lateinit var mAmountTextView: TextView
    private lateinit var mNameTextView: TextView
    private lateinit var mDateTextView: TextView

    private lateinit var mEditCategoryImageView: ImageView
    private lateinit var mSendMoneyImageView: ImageView
    private lateinit var mCategoryImageView: ImageView
    private lateinit var exit: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_transaction_detail, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mReceivedTitleLabelTextView = view.findViewById(R.id.received)
        mCountTitleLabelTextView = view.findViewById(R.id.count)
        mSentTitleLabelTextView = view.findViewById(R.id.sent)

        mHistoryTextView = view.findViewById(R.id.history_title)
        mAmountTextView = view.findViewById(R.id.amount)
        mDateTextView = view.findViewById(R.id.date)
        mNameTextView = view.findViewById(R.id.name)

        mCategoryTextView = view.findViewById(R.id.transaction_category_text)
        mCategoryImageView = view.findViewById(R.id.transaction_category)

        mSendMoneyImageView = view.findViewById(R.id.send_money)
        mEditCategoryImageView = view.findViewById(R.id.edit)
        exit = view.findViewById(R.id.exit)
    }

    @SuppressLint("CheckResult")
    override fun setUp(view: View) {
        val transaction: Transaction = arguments?.getParcelable(TRANSACTION)!!
        mPresenter.getTransactions()

        Log.d(transaction.toString())

        eventBus.observe(UpdateTransaction::class.java)
                .delay(1L, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                }

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mCategoryTextView.text = transaction.category.isNotEmpty() then transaction.category ?: "Transfers"
        mCategoryImageView.setImageDrawable(ContextCompat.getDrawable(baseActivity, when (mCategoryTextView.text) {
            "Bills" -> R.drawable.icon_bills2
            "Income" -> R.drawable.icon_income
            "Travel" -> R.drawable.icon_travel
            "Health Care" -> R.drawable.icon_give
            "Eating Out" -> R.drawable.icon_eating
            "Shopping" -> R.drawable.icon_shopping
            "Charity" -> R.drawable.icon_education
            "Payroll" -> R.drawable.icon_investment
            "Transfers" -> R.drawable.icon_transfers
            "Groceries" -> R.drawable.icon_groceries
            "Education" -> R.drawable.icon_education
            "Investments" -> R.drawable.icon_investment
            "Entertainment" -> R.drawable.icon_entertainment
            "Transportation" -> R.drawable.icon_transportation
            else -> R.drawable.icon_transfers
        }))
        mSendMoneyImageView.setOnClickListener {

        }

        mEditCategoryImageView.setOnClickListener {
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(PaymentCategoriesFragment.newInstance(transaction))
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initView(transactions: ArrayList<Transaction>) {
        val transaction: Transaction = arguments?.getParcelable(TRANSACTION)!!

        with(transaction) {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            val dateFormat = SimpleDateFormat("hh:mm a, dd MMMM", Locale.ENGLISH)

            mDateTextView.text = dateFormat.format(formatter.parse(this.date))

            mAmountTextView.text = String.format("₦%,.2f", amount.toFloat())
            mNameTextView.text = (credit == "00") then recipientName ?: senderName
            mHistoryTextView.text = "History with ${(credit == "00") then recipientName
                    ?: senderName}"

            mCountTitleLabelTextView.label = (transactions.filter {
                it.recipientPhoneNumber == (credit == "00") then recipientPhoneNumber ?: senderNumber || it.senderNumber == (credit == "11") then senderNumber ?: recipientPhoneNumber
            } as ArrayList<Transaction>).size.toString()

            val receivedTransactions = transactions.filter {
                it.credit == "11" && it.senderNumber == senderNumber
            } as ArrayList<Transaction>

            val received = if (receivedTransactions.isEmpty()) Transaction()
            else receivedTransactions.reduce { acc, t ->
                acc.amountInt += t.amount.toFloat().toInt()
                return@reduce acc
            }
            mReceivedTitleLabelTextView.label = String.format("₦%,.2f", received.amountInt.toFloat())

            val sentTransactions = transactions.filter {
                it.credit == "00" && it.recipientPhoneNumber == senderNumber
            } as ArrayList<Transaction>

            val sent = if (sentTransactions.isEmpty()) Transaction()
            else sentTransactions.reduce { acc, t ->
                acc.amountInt += t.amount.toFloat().toInt()
                return@reduce acc
            }
            mSentTitleLabelTextView.label = String.format("₦%,.2f", sent.amountInt.toFloat())
        }
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        private const val TRANSACTION = "TransactionDetailFragment.Transaction"
        private const val POSITION = "TransactionDetailFragment.POSITION"

        fun newInstance(transaction: Transaction, position: Int): TransactionDetailFragment {
            val fragment = TransactionDetailFragment()
            val args = Bundle()
            args.putParcelable(TRANSACTION, transaction)
            args.putInt(POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }
}