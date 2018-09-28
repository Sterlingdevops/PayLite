package com.sterlingng.paylite.ui.banktransfers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.banktransfers.contacts.ContactsFragment
import com.sterlingng.paylite.ui.banktransfers.newbanktransfer.NewBankTransferFragment
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.utils.CustomPagerAdapter
import com.sterlingng.views.CustomViewPager
import javax.inject.Inject

class BankTransferFragment : BaseFragment(), BankTransferMvpView {

    @Inject
    lateinit var mPresenter: BankTransferMvpContract<BankTransferMvpView>

    private lateinit var mPagerAdapter: CustomPagerAdapter

    private lateinit var exit: ImageView
    private lateinit var mBalanceTextView: TextView
    private lateinit var mViewPager: CustomViewPager
    private lateinit var mSmartTabLayout: SmartTabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bank_transfers, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        mPresenter.loadWallet()

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mPagerAdapter = CustomPagerAdapter(childFragmentManager)

        mPagerAdapter.addFragment(NewBankTransferFragment.newInstance(), "NEW")
        mPagerAdapter.addFragment(ContactsFragment.newInstance(), "CONTACTS")

        mViewPager.isPagingEnabled = true
        mViewPager.adapter = mPagerAdapter
        mViewPager.offscreenPageLimit = 2

        mSmartTabLayout.setViewPager(mViewPager)
    }

    override fun initView(wallet: Wallet) {
        mBalanceTextView.text = String.format("₦%,.2f", wallet.balance.toFloat())
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mViewPager = view.findViewById(R.id.viewpager)
        mBalanceTextView = view.findViewById(R.id.balance)
        mSmartTabLayout = view.findViewById(R.id.viewpagertab)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): BankTransferFragment {
            val fragment = BankTransferFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
