package com.sterlingng.paylite.ui.cashoutcode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.confirm.ConfirmFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import javax.inject.Inject

class CashOutCodeFragment : BaseFragment(), DonateMvpView {

    @Inject
    lateinit var mPresenter: DonateMvpContract<DonateMvpView>

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mTitleTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cash_out_code, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        next.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(ConfirmFragment.newInstance())
        }
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)
        mTitleTextView = view.findViewById(R.id.title)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): CashOutCodeFragment {
            val fragment = CashOutCodeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
