package com.sterlingng.paylite.ui.send

import android.os.Bundle
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
import com.sterlingng.paylite.utils.RecyclerViewLongClickListener
import com.sterlingng.paylite.utils.then
import com.sterlingng.views.NoScrollingLinearLayoutManager
import javax.inject.Inject

class SendMoneyFragment : BaseFragment(), SendMoneyMvpView, RecyclerViewLongClickListener {

    @Inject
    lateinit var mPresenter: SendMoneyMvpContract<SendMoneyMvpView>

    private lateinit var exit: ImageView

    private lateinit var mNewPaymentTextView: TextView
    private lateinit var mNewPaymentRefTextView: TextView

    private lateinit var mScheduledTextView: TextView
    private lateinit var mScheduledRefTextView: TextView

    private lateinit var mSeeAllTextView: TextView
    private lateinit var mSeeAllImageView: ImageView

    private lateinit var mBankTransferTextView: TextView
    private lateinit var mBankTransferRefTextView: TextView

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

        mNewPaymentRefTextView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(NewPaymentFragment.newInstance())
        }

        mBankTransferTextView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(BankTransferFragment.newInstance())
        }

        mBankTransferRefTextView.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(BankTransferFragment.newInstance())
        }

        mScheduledRefTextView.setOnClickListener {

        }

        mScheduledTextView.setOnClickListener {

        }

        mSeeAllTextView.visibility = (mContactsAdapter.contacts.size == 0) then View.GONE ?: View.VISIBLE
        mSeeAllImageView.visibility = (mContactsAdapter.contacts.size == 0) then View.GONE ?: View.VISIBLE

        mPresenter.loadCachedWallet()
        mPresenter.loadContacts()
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mContactsRecyclerView = view.findViewById(R.id.recyclerView)

        mSeeAllTextView = view.findViewById(R.id.see_all)
        mSeeAllImageView = view.findViewById(R.id.see_all_image)

        mNewPaymentTextView = view.findViewById(R.id.new_payment)
        mNewPaymentRefTextView = view.findViewById(R.id.new_payment_ref)

        mScheduledTextView = view.findViewById(R.id.scheduled_payments)
        mScheduledRefTextView = view.findViewById(R.id.scheduled_payments_ref)

        mBankTransferTextView = view.findViewById(R.id.to_bank)
        mBankTransferRefTextView = view.findViewById(R.id.to_bank_ref)

        mBalanceTextView = view.findViewById(R.id.balance)
    }

    override fun initView(wallet: Wallet?) {
        mBalanceTextView.text = String.format("Balance ₦%,.2f", wallet?.balance?.toFloat())
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