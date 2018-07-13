package com.sterlingng.paylite.ui.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SnapHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Deal
import com.sterlingng.paylite.ui.airtime.AirTimeActivity
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.donate.DonateActivity
import com.sterlingng.paylite.ui.fund.FundActivity
import com.sterlingng.paylite.ui.profile.ProfileActivity
import com.sterlingng.paylite.ui.transactions.TransactionsActivity
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeMvpView, DealsAdapter.OnRetryClicked {

    @Inject
    lateinit var mPresenter: HomeMvpContract<HomeMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mDealsAdapter: DealsAdapter

    @Inject
    lateinit var mSnapHelper: SnapHelper

    private lateinit var mProfileImageView: CircleImageView

    private lateinit var mMovieTicketsImageView: ImageView
    private lateinit var mAirTimeDataImageView: ImageView
    private lateinit var mQrPaymentImageView: ImageView
    private lateinit var mSendMoneyImageView: ImageView
    private lateinit var mPayBillsImageView: ImageView
    private lateinit var mFlightsImageView: ImageView

    private lateinit var mMovieTicketsTextView: TextView
    private lateinit var mAirTimeDataTextView: TextView
    private lateinit var mQrPaymentTextView: TextView
    private lateinit var mSendMoneyTextView: TextView
    private lateinit var mPayBillsTextView: TextView
    private lateinit var mFlightsTextView: TextView

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mTopUpButton: Button
    private lateinit var mHistoryButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mTopUpButton = view.findViewById(R.id.top_up)
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mHistoryButton = view.findViewById(R.id.view_history)
        mProfileImageView = view.findViewById(R.id.profile_icon)

        mAirTimeDataImageView = view.findViewById(R.id.airtime_data)
        mAirTimeDataTextView = view.findViewById(R.id.airtime_and_data_text)

        mPayBillsImageView = view.findViewById(R.id.pay_bills)
        mPayBillsTextView = view.findViewById(R.id.pay_bills_text)

        mFlightsImageView = view.findViewById(R.id.flights)
        mFlightsTextView = view.findViewById(R.id.flights_text)

        mMovieTicketsImageView = view.findViewById(R.id.movie_tickets)
        mMovieTicketsTextView = view.findViewById(R.id.movie_tickets_text)

        mQrPaymentImageView = view.findViewById(R.id.qr_payment)
        mQrPaymentTextView = view.findViewById(R.id.qr_payment_text)

        mSendMoneyImageView = view.findViewById(R.id.send_money)
        mSendMoneyTextView = view.findViewById(R.id.send_money_text)
    }

    override fun setUp(view: View) {
        mLinearLayoutManager.orientation = RecyclerView.HORIZONTAL
        mDealsAdapter.mRecyclerViewClickListener = this
        mDealsAdapter.onRetryClickedListener = this
        mRecyclerView.adapter = mDealsAdapter
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.scrollToPosition(0)
        mSnapHelper.attachToRecyclerView(mRecyclerView)

        mPresenter.loadDeals()

        mAirTimeDataImageView.setOnClickListener {
            startActivity(AirTimeActivity.getStartIntent(baseActivity))
        }

        mAirTimeDataTextView.setOnClickListener {
            startActivity(AirTimeActivity.getStartIntent(baseActivity))
        }

        mSendMoneyImageView.setOnClickListener {
            val intent = DonateActivity.getStartIntent(baseActivity)
                    .putExtra(DonateActivity.donateTitle, "Send Money")
                    .putExtra(DonateActivity.donateType, 1)
            startActivity(intent)
        }

        mSendMoneyTextView.setOnClickListener {
            val intent = DonateActivity.getStartIntent(baseActivity)
                    .putExtra(DonateActivity.donateTitle, "Send Money")
                    .putExtra(DonateActivity.donateType, 1)
            startActivity(intent)
        }

        mProfileImageView.setOnClickListener {
            startActivity(ProfileActivity.getStartIntent(baseActivity))
        }

        mTopUpButton.setOnClickListener {
            startActivity(FundActivity.getStartIntent(baseActivity))
        }

        mHistoryButton.setOnClickListener {
            startActivity(TransactionsActivity.getStartIntent(baseActivity))
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    override fun onRetryClicked() {

    }

    override fun updateDeals(it: ArrayList<Deal>) {
        mDealsAdapter.addDeals(it)
        mRecyclerView.scrollToPosition(0)
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
