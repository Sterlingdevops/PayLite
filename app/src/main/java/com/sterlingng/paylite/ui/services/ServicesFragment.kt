package com.sterlingng.paylite.ui.services

import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.VAService
import com.sterlingng.paylite.ui.base.BaseFragment
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_services, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mNestedScrollView = view.findViewById(R.id.nestedScrollView)
        mRecyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun setUp(view: View) {
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