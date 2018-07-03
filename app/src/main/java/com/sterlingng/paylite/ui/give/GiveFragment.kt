package com.sterlingng.paylite.ui.give


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.give.categories.CategoriesFragment
import com.sterlingng.paylite.ui.give.charities.CharitiesFragment
import com.sterlingng.paylite.utils.widgets.CustomViewPager
import javax.inject.Inject

class GiveFragment : BaseFragment(), GiveMvpView {

    @Inject
    lateinit var mPresenter: GiveMvpContract<GiveMvpView>

    lateinit var mPagerAdapter: GivePagerAdapter

    private lateinit var mViewPager: CustomViewPager
    private lateinit var mSmartTabLayout: SmartTabLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_give, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mViewPager = view.findViewById(R.id.viewpager)
        mSmartTabLayout = view.findViewById(R.id.viewpagertab)
    }

    override fun setUp(view: View) {
        mPagerAdapter = GivePagerAdapter(childFragmentManager)
        mPagerAdapter.addFragment(CategoriesFragment.newInstance(), "Categories")
        mPagerAdapter.addFragment(CharitiesFragment.newInstance(), "Charities")

        mViewPager.adapter = mPagerAdapter
        mViewPager.isPagingEnabled = true

        mSmartTabLayout.setViewPager(mViewPager)
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
