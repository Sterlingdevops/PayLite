package com.sterlingng.paylite.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.airtime.AirTimeActivity
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.donate.DonateActivity
import com.sterlingng.paylite.ui.profile.ProfileActivity
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeMvpView {

    @Inject
    lateinit var mPresenter: HomeMvpContract<HomeMvpView>

    private lateinit var mNotificationsImageView: ImageView
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
//        mTopUpButton = view.findViewById(R.id.top_up)
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
    }

    override fun setUp(view: View) {
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

        mNotificationsImageView.setOnClickListener {
            startActivity(ProfileActivity.getStartIntent(baseActivity))
        }
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
