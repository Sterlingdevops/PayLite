package com.sterlingng.paylite.ui.give.charities

import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Charity
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.charity.CharityActivity
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import com.sterlingng.paylite.ui.give.OnFilterClicked
import javax.inject.Inject

class CharitiesFragment : BaseFragment(), CharitiesMvpView, CharitiesAdapter.OnRetryClicked, OnFilterClicked, FilterBottomSheetFragment.OnFilterItemSelected {

    @Inject
    lateinit var mPresenter: CharitiesMvpContract<CharitiesMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mCharitiesAdapter: CharitiesAdapter

    private lateinit var mSearchView: FloatingSearchView

    private lateinit var mRecyclerView: RecyclerView

    private var charities = ArrayList<Charity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_charities, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun updateCharities(it: ArrayList<Charity>) {
        charities = it
        mCharitiesAdapter.add(it)
    }

    override fun bindViews(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mSearchView = view.findViewById(R.id.floating_search_view)
    }

    override fun setUp(view: View) {
        mCharitiesAdapter.mRecyclerViewClickListener = this
        mCharitiesAdapter.onRetryClickedListener = this
        mRecyclerView.adapter = mCharitiesAdapter
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.scrollToPosition(0)

        mPresenter.loadCharities()
        initSearchView()
    }

    override fun onRetryClicked() {

    }

    override fun recyclerViewListClicked(v: View, position: Int) {
        val intent = CharityActivity.getStartIntent(baseActivity)
        startActivity(intent)
    }

    override fun onFilterClicked() {
        val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
        filterBottomSheetFragment.onFilterItemSelectedListener = this
        filterBottomSheetFragment.title = "Project"
        filterBottomSheetFragment.items = listOf("Health", "Education", "Agriculture", "Transportation")
        filterBottomSheetFragment.show(baseActivity.supportFragmentManager, "filter")
    }

    private fun initSearchView() {
        mSearchView.setShowSearchKey(true)
        mSearchView.setOnQueryChangeListener { oldQuery, newQuery ->
            if (oldQuery != "" && newQuery == "") {
                mSearchView.clearSuggestions()
                mCharitiesAdapter.clear()
                mCharitiesAdapter.add(charities)
            } else {
                val clone = ArrayList<Charity>()
                for (i in charities.indices) {
                    if (charities[i].name.toLowerCase().contains(newQuery.toLowerCase())) {
                        clone.add(charities[i])
                    }
                    if (charities[i].category.toLowerCase().contains(newQuery.toLowerCase())) {
                        clone.add(charities[i])
                    }
                }
                mSearchView.swapSuggestions(clone)
                mCharitiesAdapter.clear()
                mCharitiesAdapter.add(clone)
            }
        }

        mSearchView.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSearchAction(currentQuery: String) {
                val clone = ArrayList<Charity>()
                for (i in charities.indices) {
                    if (charities[i].name.toLowerCase().contains(currentQuery.toLowerCase())) {
                        clone.add(charities[i])
                    }
                    if (charities[i].category.toLowerCase().contains(currentQuery.toLowerCase())) {
                        clone.add(charities[i])
                    }
                }
                mSearchView.swapSuggestions(clone)
                mCharitiesAdapter.clear()
                mCharitiesAdapter.add(clone)
            }

            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion) {
                hideKeyboard()
                val intent = CharityActivity.getStartIntent(baseActivity)
                startActivity(intent)
            }
        })

        mSearchView.setOnClearSearchActionListener {
            mSearchView.clearSuggestions()
            mCharitiesAdapter.clear()
            mCharitiesAdapter.add(charities)
        }

        //listen for when suggestion list expands/shrinks in order to move down/up the
        //search results list
        mSearchView.setOnSuggestionsListHeightChanged { newHeight -> mRecyclerView.translationY = newHeight }
    }

    override fun onFilterItemSelected(dialog: Dialog, selector: Int, s: String) {
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
