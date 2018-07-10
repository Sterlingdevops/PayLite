package com.sterlingng.paylite.ui.give


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.give.charities.CharitiesFragment
import com.sterlingng.paylite.ui.give.projects.ProjectsFragment
import com.sterlingng.paylite.utils.widgets.CustomViewPager
import javax.inject.Inject

class GiveFragment : BaseFragment(), GiveMvpView {

    @Inject
    lateinit var mPresenter: GiveMvpContract<GiveMvpView>

    lateinit var mPagerAdapter: GivePagerAdapter

    private lateinit var filter: ImageView
    private lateinit var mViewPager: CustomViewPager
    private lateinit var mSmartTabLayout: SmartTabLayout

    private lateinit var onFilterClickedListener: OnFilterClicked

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_give, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        filter = view.findViewById(R.id.filter)
        mViewPager = view.findViewById(R.id.viewpager)
        mSmartTabLayout = view.findViewById(R.id.viewpagertab)
    }

    override fun setUp(view: View) {
        mPagerAdapter = GivePagerAdapter(childFragmentManager)

        val categoriesFragment = ProjectsFragment.newInstance()
        onFilterClickedListener = categoriesFragment

        mPagerAdapter.addFragment(categoriesFragment, "Categories")
        mPagerAdapter.addFragment(CharitiesFragment.newInstance(), "Charities")

        mViewPager.adapter = mPagerAdapter
        mViewPager.isPagingEnabled = true

        mSmartTabLayout.setViewPager(mViewPager)

        filter.setOnClickListener {
            onFilterClickedListener.onFilterClicked()
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): GiveFragment {
            val fragment = GiveFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
