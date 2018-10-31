package com.sterlingng.paylite.ui.transactions.detail

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.sterlingng.paylite.utils.then
import com.sterlingng.views.TitleLabelTextView
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TransactionDetailFragment : BaseFragment(), TransactionDetailMvpView {

    @Inject
    lateinit var mPresenter: TransactionDetailMvpContract<TransactionDetailMvpView>

    @Inject
    lateinit var eventBus: EventBus

    private lateinit var mReceivedTitleLabelTextView: TitleLabelTextView
    private lateinit var mCountTitleLabelTextView: TitleLabelTextView
    private lateinit var mSentTitleLabelTextView: TitleLabelTextView

    private lateinit var mHistoryTextView: TextView
    private lateinit var mAmountTextView: TextView
    private lateinit var mNameTextView: TextView
    private lateinit var mDateTextView: TextView

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

        exit = view.findViewById(R.id.exit)
    }

    override fun setUp(view: View) {
        mPresenter.getTransactions()

        exit.setOnClickListener {
            eventBus.post(UpdateTransaction(arguments?.getInt(POSITION)!!))
            baseActivity.onBackPressed()
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
                it.recipientName == (credit == "00") then recipientName ?: senderName || it.senderName == (credit == "11") then senderName ?: recipientName
            } as ArrayList<Transaction>).size.toString()

            val countTransactions = transactions.filter {
                it.recipientName == (credit == "00") then recipientName ?: senderName || it.senderName == (credit == "11") then senderName ?: recipientName
            } as ArrayList<Transaction>
            val count = countTransactions.isEmpty() then Transaction()
                    ?: countTransactions.reduce { acc, transaction ->
                        acc.amountInt += transaction.amount.toFloat().toInt()
                        return@reduce acc
                    }
            mReceivedTitleLabelTextView.label = String.format("₦%,.2f", count.amountInt.toFloat())

            val sentTransactions = transactions.filter {
                it.recipientName == (credit == "00") then recipientName ?: senderName || it.senderName == (credit == "11") then senderName ?: recipientName
            } as ArrayList<Transaction>
            val label = sentTransactions.isEmpty() then Transaction()
                    ?: sentTransactions.reduce { acc, transaction ->
                        acc.amountInt += transaction.amount.toFloat().toInt()
                        return@reduce acc
                    }
            mSentTitleLabelTextView.label = String.format("₦%,.2f", label.amountInt.toFloat())
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