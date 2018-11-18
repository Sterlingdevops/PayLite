package com.sterlingng.paylite.ui.send

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.data.model.SendMoneyRequest
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.banktransfers.BankTransferFragment
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.newpayment.NewPaymentFragment
import com.sterlingng.paylite.ui.newpaymentamount.NewPaymentAmountFragment
import com.sterlingng.paylite.ui.scheduled.ScheduledFragment
import com.sterlingng.paylite.utils.RecyclerViewLongClickListener
import com.sterlingng.views.NoScrollingLinearLayoutManager
import com.sterlingng.views.TitleLabelIconView
import javax.inject.Inject

class SendMoneyFragment : BaseFragment(), SendMoneyMvpView, RecyclerViewLongClickListener {

    @Inject
    lateinit var mPresenter: SendMoneyMvpContract<SendMoneyMvpView>

    private lateinit var exit: ImageView

    private lateinit var mScheduledRefView: TitleLabelIconView
    private lateinit var mBankTransferTextView: CardView
    private lateinit var mNewPaymentTextView: CardView
    private lateinit var mScheduledTextView: CardView

    private lateinit var mContactsAdapter: ContactsAdapter
    private lateinit var mContactsRecyclerView: RecyclerView

    private lateinit var mContactsLinearLayoutManager: NoScrollingLinearLayoutManager

    private lateinit var mBalanceTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_send_money, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun updateContacts(it: ArrayList<PayliteContact>) {
        mContactsAdapter.add(it)
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        // Paylite Contact

        mContactsAdapter = ContactsAdapter(baseActivity, 0)
        mContactsLinearLayoutManager = NoScrollingLinearLayoutManager(baseActivity)
        mContactsLinearLayoutManager.orientation = RecyclerView.HORIZONTAL

        mContactsRecyclerView.adapter = mContactsAdapter
        mContactsAdapter.mRecyclerViewClickListener = this
        mContactsAdapter.mRecyclerViewLongClickListener = this
        mContactsRecyclerView.layoutManager = mContactsLinearLayoutManager
        mContactsRecyclerView.scrollToPosition(0)

        mNewPaymentTextView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(NewPaymentFragment.newInstance())
        }

        mBankTransferTextView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(BankTransferFragment.newInstance())
        }

        mScheduledTextView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(ScheduledFragment.newInstance())
        }

        mPresenter.loadCachedWallet()
        mPresenter.loadContacts()
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mContactsRecyclerView = view.findViewById(R.id.recyclerView)
        mNewPaymentTextView = view.findViewById(R.id.new_payment)
        mScheduledTextView = view.findViewById(R.id.schedule_payment)
        mScheduledRefView = view.findViewById(R.id.schedule_payment_ref)
        mBankTransferTextView = view.findViewById(R.id.to_bank)

        mBalanceTextView = view.findViewById(R.id.balance)
    }

    @SuppressLint("SetTextI18n")
    override fun initView(wallet: Wallet?, count: String) {
        mBalanceTextView.text = String.format("Balance: ₦%,.2f", wallet?.balance?.toFloat())
        mScheduledRefView.label = "$count standing orders"
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {
        with(mContactsAdapter.get(position)) {
            val sendMoneyRequest = SendMoneyRequest()
            sendMoneyRequest.email = email
            sendMoneyRequest.phone = phone
            sendMoneyRequest.recipientName = name
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(NewPaymentAmountFragment.newInstance(sendMoneyRequest))
        }
    }

    override fun recyclerViewItemLongClicked(v: View, position: Int) {
        mPresenter.deleteContact(mContactsAdapter.contacts[position])
    }

    companion object {

        fun newInstance(): SendMoneyFragment {
            val fragment = SendMoneyFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}