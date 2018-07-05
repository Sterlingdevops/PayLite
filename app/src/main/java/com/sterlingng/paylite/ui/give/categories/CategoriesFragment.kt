package com.sterlingng.paylite.ui.give.categories


import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Category
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.give.OnFilterClicked
import com.sterlingng.paylite.ui.give.filter.FilterBottomSheetFragment
import com.sterlingng.paylite.utils.ItemOffsetDecoration
import javax.inject.Inject

class CategoriesFragment : BaseFragment(), CategoriesMvpView, CategoriesAdapter.OnRetryClicked, OnFilterClicked, FilterBottomSheetFragment.OnFilterItemSelected {

    @Inject
    lateinit var mPresenter: CategoriesMvpContract<CategoriesMvpView>

    @Inject
    lateinit var mGridLayoutManager: GridLayoutManager

    @Inject
    lateinit var mCategoriesAdapter: CategoriesAdapter

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var itemOffsetDecoration: ItemOffsetDecoration

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun setUp(view: View) {
        itemOffsetDecoration = ItemOffsetDecoration(baseActivity, R.dimen.item_offset)
        mCategoriesAdapter.mRecyclerViewClickListener = this
        mCategoriesAdapter.onRetryClickedListener = this
        mRecyclerView.adapter = mCategoriesAdapter
        mRecyclerView.layoutManager = mGridLayoutManager
        mRecyclerView.addItemDecoration(itemOffsetDecoration)
        mRecyclerView.scrollToPosition(0)

        mPresenter.loadCategories()
    }

    override fun onRetryClicked() {

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

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    override fun updateDeals(it: ArrayList<Category>) {
        mCategoriesAdapter.addCategories(it)
    }

    companion object {

        fun newInstance(): CategoriesFragment {
            val fragment = CategoriesFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
