package com.sterlingng.paylite.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Response
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.airtime.AirTimeActivity
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.donate.DonateActivity
import com.sterlingng.paylite.ui.fund.FundActivity
import com.sterlingng.paylite.ui.profile.ProfileActivity
import com.sterlingng.paylite.ui.request.RequestActivity
import com.sterlingng.paylite.ui.send.SendMoneyActivity
import com.sterlingng.paylite.ui.transfer.TransferActivity
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.Log
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeMvpView {

    @Inject
    lateinit var mPresenter: HomeMvpContract<HomeMvpView>

    private lateinit var mNotificationsImageView: ImageView
    private lateinit var mRequestMoneyImageView: ImageView
    private lateinit var mMovieTicketsImageView: ImageView
    private lateinit var mAirTimeDataImageView: ImageView
    private lateinit var mSendMoneyImageView: ImageView
    private lateinit var mPayBillsImageView: ImageView
    private lateinit var mFlightsImageView: ImageView
    private lateinit var mPayCodeImageView: ImageView
    private lateinit var mCashOutImageView: ImageView

    private lateinit var mMovieTicketsTextView: TextView
    private lateinit var mRequestMoneyTextView: TextView
    private lateinit var mAirTimeDataTextView: TextView
    private lateinit var mSendMoneyTextView: TextView
    private lateinit var mPayBillsTextView: TextView
    private lateinit var mCashOutTextView: TextView
    private lateinit var mPayCodeTextView: TextView
    private lateinit var mFlightsTextView: TextView

    private lateinit var mMainAmountTextView: TextView
    private lateinit var mFundButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mNotificationsImageView = view.findViewById(R.id.notifications_icon)

        mAirTimeDataImageView = view.findViewById(R.id.airtime_data)
        mAirTimeDataTextView = view.findViewById(R.id.airtime_data_text)

        mPayBillsImageView = view.findViewById(R.id.pay_bills)
        mPayBillsTextView = view.findViewById(R.id.pay_bills_text)

        mFlightsImageView = view.findViewById(R.id.travels_and_hotels)
        mFlightsTextView = view.findViewById(R.id.travels_and_hotels_text)

        mMovieTicketsImageView = view.findViewById(R.id.movie_tickets)
        mMovieTicketsTextView = view.findViewById(R.id.movie_tickets_text)

        mSendMoneyImageView = view.findViewById(R.id.send_money)
        mSendMoneyTextView = view.findViewById(R.id.send_money_text)

        mRequestMoneyImageView = view.findViewById(R.id.request_money)
        mRequestMoneyTextView = view.findViewById(R.id.request_money_text)

        mPayCodeImageView = view.findViewById(R.id.cash_out_code)
        mPayCodeTextView = view.findViewById(R.id.cash_out_code_text)

        mCashOutImageView = view.findViewById(R.id.cash_out_to_bank)
        mCashOutTextView = view.findViewById(R.id.cash_out_to_bank_text)

        mMainAmountTextView = view.findViewById(R.id.main_amount)
        mFundButton = view.findViewById(R.id.fund)
    }

    override fun setUp(view: View) {
        mPresenter.loadWallet()

        mAirTimeDataImageView.setOnClickListener {
            startActivity(AirTimeActivity.getStartIntent(baseActivity))
        }

        mAirTimeDataTextView.setOnClickListener {
            startActivity(AirTimeActivity.getStartIntent(baseActivity))
        }

        mRequestMoneyImageView.setOnClickListener {
            startActivity(RequestActivity.getStartIntent(baseActivity))
        }

        mRequestMoneyTextView.setOnClickListener {
            startActivity(RequestActivity.getStartIntent(baseActivity))
        }

        mSendMoneyImageView.setOnClickListener {
            val intent = SendMoneyActivity.getStartIntent(baseActivity)
            startActivity(intent)
        }

        mSendMoneyTextView.setOnClickListener {
            val intent = SendMoneyActivity.getStartIntent(baseActivity)
            startActivity(intent)
        }

        mNotificationsImageView.setOnClickListener {
            startActivity(ProfileActivity.getStartIntent(baseActivity))
        }

        mFundButton.setOnClickListener {
            startActivity(FundActivity.getStartIntent(baseActivity))
        }

        mPayCodeTextView.setOnClickListener {
            val intent = DonateActivity.getStartIntent(baseActivity)
                    .putExtra(DonateActivity.donateTitle, "Send Money")
                    .putExtra(DonateActivity.donateType, 2)
            startActivity(intent)
        }

        mPayCodeImageView.setOnClickListener {
            val intent = DonateActivity.getStartIntent(baseActivity)
                    .putExtra(DonateActivity.donateTitle, "Send Money")
                    .putExtra(DonateActivity.donateType, 2)
            startActivity(intent)
        }

        mCashOutImageView.setOnClickListener {
            startActivity(TransferActivity.getStartIntent(baseActivity))
        }

        mCashOutTextView.setOnClickListener {
            startActivity(TransferActivity.getStartIntent(baseActivity))
        }
    }

    override fun onGetWalletFailed(it: Throwable) {
        show(it.localizedMessage, true)
        Log.e(it, "HomeFragment->onGetWalletFailed")
    }

    override fun onGetWalletSuccessful(it: Response) {
        val wallet = gson.fromJson(gson.toJson(it.data), Wallet::class.java)
        mMainAmountTextView.text = String.format("₦%,.2f", wallet.balance.toFloat())
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
