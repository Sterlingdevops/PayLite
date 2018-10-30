package com.sterlingng.paylite.ui.sheduledtransaction


import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.ScheduledPayment
import com.sterlingng.paylite.data.model.Transaction
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.views.TitleLabelTextView
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class ScheduledTransactionFragment : BaseFragment(), ScheduledTransactionMvpView {

    @Inject
    lateinit var mPresenter: ScheduledTransactionMvpContract<ScheduledTransactionMvpView>

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var exit: ImageView
    private lateinit var mNameTextView: TextView
    private lateinit var mAmountTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var mCancelImageView: CircleImageView
    private lateinit var mRepeatTitleLabelTextView: TitleLabelTextView
    private lateinit var mPaymentInfoTitleLabelTextView: TitleLabelTextView
    private lateinit var mScheduledTransactionAdapter: ScheduledTransactionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_scheduled_transaction, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        mScheduledTransactionAdapter = ScheduledTransactionAdapter(baseActivity)
        mScheduledTransactionAdapter.mRecyclerViewClickListener = this

        recyclerView.adapter = mScheduledTransactionAdapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.isNestedScrollingEnabled = false
        ViewCompat.setNestedScrollingEnabled(recyclerView, false)

        val scheduledPayment: ScheduledPayment = arguments?.getParcelable(SCHEDULED_PAYMENT)!!
        mPresenter.getTransactions(scheduledPayment.beneficiaryId, scheduledPayment.paymentRef)

        with(scheduledPayment) {
            mAmountTextView.text = String.format("₦%,.2f", amount.toFloat())
            mNameTextView.text = beneficiaryId

            mPaymentInfoTitleLabelTextView.label = numberOfTimesPaidOut.toString()

            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            val dailyDateFormat = SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH)
            val yearlyDateFormat = SimpleDateFormat("MMMM", Locale.ENGLISH)
            val weeklyDateFormat = SimpleDateFormat("EEEE", Locale.ENGLISH)
            val monthlyDateFormat = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)

            val date: Date = formatter.parse(endDate)
            mRepeatTitleLabelTextView.label = when (interval) {
                1 -> "Daily till ${dailyDateFormat.format(date)}"
                2 -> "Weekly on ${weeklyDateFormat.format(date)}s"
                3 -> "Every month till ${monthlyDateFormat.format(date)}"
                4 -> "Every ${yearlyDateFormat.format(date)}"
                else -> "Never"
            }
        }

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mCancelImageView.setOnClickListener {
            mPresenter.cancelStandingOrder(scheduledPayment)
        }
    }

    override fun initView(transactions: ArrayList<Transaction>) {
        mScheduledTransactionAdapter.add(transactions)
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mNameTextView = view.findViewById(R.id.name)
        mAmountTextView = view.findViewById(R.id.amount)
        mCancelImageView = view.findViewById(R.id.cancel)
        recyclerView = view.findViewById(R.id.recyclerView)
        mRepeatTitleLabelTextView = view.findViewById(R.id.repeat)
        mPaymentInfoTitleLabelTextView = view.findViewById(R.id.payment_info)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun onDeleteScheduledPaymentsFailed() {
        show("An error occurred while processing the transaction", true)
    }

    override fun onDeleteScheduledPaymentsSuccessful() {
        show("Standing Order has been cancelled", true)
        (baseActivity as DashboardActivity).mNavController.popFragments(1)
    }

    override fun onGetUserRelativeTransactionsSuccessful() {

    }

    override fun onGetUserRelativeTransactionsFailed() {

    }

    override fun logout() {
        baseActivity.logout()
    }

    companion object {

        private const val SCHEDULED_PAYMENT = "ScheduledTransactionFragment.SCHEDULED_PAYMENT"

        fun newInstance(scheduledPayment: ScheduledPayment): ScheduledTransactionFragment {
            val fragment = ScheduledTransactionFragment()
            val args = Bundle()
            args.putParcelable(SCHEDULED_PAYMENT, scheduledPayment)
            fragment.arguments = args
            return fragment
        }
    }
}
