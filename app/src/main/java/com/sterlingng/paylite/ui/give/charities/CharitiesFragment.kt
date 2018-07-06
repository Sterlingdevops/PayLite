package com.sterlingng.paylite.ui.give.charities

import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Charity
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.give.OnFilterClicked
import com.sterlingng.paylite.ui.give.filter.FilterBottomSheetFragment
import javax.inject.Inject

class CharitiesFragment : BaseFragment(), CharitiesMvpView, CharitiesAdapter.OnRetryClicked, OnFilterClicked, FilterBottomSheetFragment.OnFilterItemSelected {

    @Inject
    lateinit var mPresenter: CharitiesMvpContract<CharitiesMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mCharitiesAdapter: CharitiesAdapter

    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_charities, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun updateCharities(it: ArrayList<Charity>) {
        mCharitiesAdapter.addCharities(it)
    }

    override fun bindViews(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun setUp(view: View) {
        mCharitiesAdapter.mRecyclerViewClickListener = this
        mCharitiesAdapter.onRetryClickedListener = this
        mRecyclerView.adapter = mCharitiesAdapter
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.scrollToPosition(0)

        mPresenter.loadCharities()
    }

    override fun onRetryClicked() {

    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    override fun onFilterClicked() {
        val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
        filterBottomSheetFragment.onFilterItemSelectedListener = this
        filterBottomSheetFragment.show(baseActivity.supportFragmentManager, "filter")
    }

    override fun onFilterItemSelected(dialog: Dialog, s: String) {
        show(s, true)
        dialog.dismiss()
    }

    companion object {

        fun newInstance(): CharitiesFragment {
            val fragment = CharitiesFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
