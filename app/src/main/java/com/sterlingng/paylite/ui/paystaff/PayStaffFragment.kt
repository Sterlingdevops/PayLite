package com.sterlingng.paylite.ui.paystaff

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.paystaff.addstaff.AddStaffFragment
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import javax.inject.Inject

class PayStaffFragment : BaseFragment(), PayStaffMvpView {

    @Inject
    lateinit var mPresenter: PayStaffMvpContract<PayStaffMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    private lateinit var exit: ImageView
    private lateinit var mAddNewButton: Button
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mStaffAdapter: StaffAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pay_staff, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mAddNewButton.setOnClickListener {
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(AddStaffFragment.newInstance())
        }

        mStaffAdapter = StaffAdapter(baseActivity)
        mStaffAdapter.mRecyclerViewClickListener = this

        mRecyclerView.adapter = mStaffAdapter
        mRecyclerView.layoutManager = mLinearLayoutManager
    }

    override fun logout() {
        baseActivity.logout()
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mAddNewButton = view.findViewById(R.id.add_new)
        mRecyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {
        fun newInstance(): PayStaffFragment {
            val fragment = PayStaffFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
