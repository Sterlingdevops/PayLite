package com.sterlingng.paylite.ui.services

import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.VAService
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.paystaff.PayStaffFragment
import com.sterlingng.paylite.ui.services.airtel.AirtelServiceFragment
import com.sterlingng.paylite.ui.services.dstv.DstvServiceFragment
import com.sterlingng.paylite.ui.services.etisalat.EtisalatServiceFragment
import com.sterlingng.paylite.ui.services.glo.GloServiceFragment
import com.sterlingng.paylite.ui.services.mtn.MtnServiceFragment
import com.sterlingng.paylite.utils.ServiceItemClickedListener
import javax.inject.Inject

class ServicesFragment : BaseFragment(), ServicesMvpView, ServiceItemClickedListener {

    @Inject
    lateinit var mPresenter: ServicesMvpContract<ServicesMvpView>

    private lateinit var mNestedScrollView: NestedScrollView
    private lateinit var mRecyclerView: RecyclerView

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var servicesAdapter: ServicesAdapter
    private lateinit var mPayStaffCardView: CardView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_services, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mNestedScrollView = view.findViewById(R.id.nestedScrollView)
        mPayStaffCardView = view.findViewById(R.id.pay_staff)
        mRecyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun setUp(view: View) {
        mPayStaffCardView.setOnClickListener {
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(PayStaffFragment.newInstance())
        }

        servicesAdapter = ServicesAdapter(baseActivity)
        servicesAdapter.serviceItemClickedListener = this
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.adapter = servicesAdapter

        mPresenter.loadServices()
    }

    override fun onServicesLoaded(services: ArrayList<VAService>) {
        servicesAdapter.add(services)
    }

    override fun logout() {
        baseActivity.logout()
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun serviceItemClicked(v: View, sectionIndex: Int, position: Int) {
        when (sectionIndex) {
            0 -> {
                when (position) {
                    0 -> (baseActivity as DashboardActivity).mNavController.pushFragment(MtnServiceFragment.newInstance())
                    1 -> (baseActivity as DashboardActivity).mNavController.pushFragment(AirtelServiceFragment.newInstance())
                    2 -> (baseActivity as DashboardActivity).mNavController.pushFragment(GloServiceFragment.newInstance())
                    3 -> (baseActivity as DashboardActivity).mNavController.pushFragment(EtisalatServiceFragment.newInstance())
                }
            }
            2 -> {
                when (position) {
                    2 -> (baseActivity as DashboardActivity).mNavController.pushFragment(DstvServiceFragment.newInstance())
                }
            }
        }
    }

    companion object {

        fun newInstance(): ServicesFragment {
            val fragment = ServicesFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}