package com.sterlingng.paylite.ui.payment.pay


import android.os.Bundle
import android.support.constraint.Group
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.donate.DonateActivity
import javax.inject.Inject

class PayFragment : BaseFragment(), PayMvpView {

    @Inject
    lateinit var mPresenter: PayMvpContract<PayMvpView>

    private lateinit var mSendMoneyReferenceTextView: TextView
    private lateinit var mPayCodeReferenceTextView: TextView
    private lateinit var mSendMoneyTextView: TextView
    private lateinit var mPayCodeTextView: TextView
    private lateinit var mSendMoneyGroup: Group

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pay, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        mSendMoneyGroup.setOnClickListener {
            val intent = DonateActivity.getStartIntent(baseActivity)
                    .putExtra(DonateActivity.donateTitle, "Send Money")
                    .putExtra(DonateActivity.donateType, 1)
            startActivity(intent)
        }

        mSendMoneyReferenceTextView.setOnClickListener {
            val intent = DonateActivity.getStartIntent(baseActivity)
                    .putExtra(DonateActivity.donateTitle, "Send Money")
                    .putExtra(DonateActivity.donateType, 1)
            startActivity(intent)
        }

        mPayCodeTextView.setOnClickListener {
            val intent = DonateActivity.getStartIntent(baseActivity)
                    .putExtra(DonateActivity.donateTitle, "Send Money")
                    .putExtra(DonateActivity.donateType, 2)
            startActivity(intent)
        }

        mPayCodeReferenceTextView.setOnClickListener {
            val intent = DonateActivity.getStartIntent(baseActivity)
                    .putExtra(DonateActivity.donateTitle, "Send Money")
                    .putExtra(DonateActivity.donateType, 2)
            startActivity(intent)
        }
    }

    override fun bindViews(view: View) {
        mSendMoneyGroup = view.findViewById(R.id.group)
        mPayCodeTextView = view.findViewById(R.id.pay_code)
        mSendMoneyTextView = view.findViewById(R.id.send_money)
        mPayCodeReferenceTextView = view.findViewById(R.id.ref2)
        mSendMoneyReferenceTextView = view.findViewById(R.id.ref1)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): PayFragment {
            val fragment = PayFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }
}
