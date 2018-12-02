package com.sterlingng.paylite.ui.paystaff.salarydetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import mr.robot.scheduleview.ScheduleView
import javax.inject.Inject

class SalaryDetailsFragment : BaseFragment(), SalaryDetailsMvpView {

    @Inject
    lateinit var mPresenter: SalaryDetailsMvpContract<SalaryDetailsMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    private lateinit var exit: ImageView
    private lateinit var mScheduleView: ScheduleView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_salary_details, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }


    }

    override fun logout() {
        baseActivity.logout()
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mScheduleView = view.findViewById(R.id.scheduleView)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {
        fun newInstance(): SalaryDetailsFragment {
            val fragment = SalaryDetailsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
