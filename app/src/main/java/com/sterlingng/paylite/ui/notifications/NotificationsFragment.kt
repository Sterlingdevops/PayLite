package com.sterlingng.paylite.ui.notifications

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Notification
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject

class NotificationsFragment : BaseFragment(), NotificationMvpView {

    @Inject
    lateinit var mPresenter: NotificationMvpContract<NotificationMvpView>

    @Inject
    lateinit var mNotificationsAdapter: NotificationsAdapter

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSearchView: FloatingSearchView

    var mNotifications = ArrayList<Notification>()
    var mNotificationsClone = ArrayList<Notification>()

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        mNotificationsAdapter.mRecyclerViewClickListener = this
        mRecyclerView.adapter = mNotificationsAdapter
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.scrollToPosition(0)

        mPresenter.loadNotifications()
        initSearchView()
    }

    override fun updateNotifications(it: ArrayList<Notification>) {
        mNotificationsAdapter.add(it)
        mRecyclerView.scrollToPosition(0)
        mNotifications.addAll(mNotificationsAdapter.notifications)
        mNotificationsClone.addAll(mNotificationsAdapter.notifications)
    }

    override fun bindViews(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mSearchView = view.findViewById(R.id.floating_search_view)
    }

    private fun initSearchView() {
        mSearchView.setShowSearchKey(true)
        mSearchView.setOnQueryChangeListener { oldQuery, newQuery ->
            if (oldQuery != "" && newQuery == "") {
                mSearchView.clearSuggestions()
                mNotificationsAdapter.clear()
                mNotificationsAdapter.add(mNotificationsClone)
            } else {
                val clone = ArrayList<Notification>()
                for (i in mNotificationsClone.indices) {
                    if (mNotificationsClone[i].text.toLowerCase().contains(newQuery.toLowerCase())) {
                        clone.add(mNotificationsClone[i])
                    }
                }
                mNotifications = clone
                mSearchView.swapSuggestions(mNotifications)
                mNotificationsAdapter.clear()
                mNotificationsAdapter.add(mNotifications)
            }
        }

        mSearchView.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSearchAction(currentQuery: String) {
                val clone = ArrayList<Notification>()
                for (i in mNotificationsClone.indices) {
                    if (mNotificationsClone[i].text.toLowerCase().contains(currentQuery.toLowerCase())) {
                        clone.add(mNotificationsClone[i])
                    }
                }
                mNotifications = clone
                mSearchView.swapSuggestions(mNotifications)
                mNotificationsAdapter.clear()
                mNotificationsAdapter.add(mNotifications)
            }

            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion) {
                val clone = ArrayList<Notification>()
                for (i in mNotificationsClone.indices) {
                    if (mNotificationsClone[i].text.toLowerCase().contains(searchSuggestion.body.toLowerCase())) {
                        clone.add(mNotificationsClone[i])
                    }
                }
                mNotifications = clone
                mSearchView.swapSuggestions(mNotifications)
                mNotificationsAdapter.clear()
                mNotificationsAdapter.add(mNotifications)
                hideKeyboard()
            }
        })

        mSearchView.setOnHomeActionClickListener {
            baseActivity.onBackPressed()
        }

        mSearchView.setOnClearSearchActionListener {
            mSearchView.clearSuggestions()
            mNotificationsAdapter.clear()
            mNotificationsAdapter.add(mNotificationsClone)
        }

        //listen for when suggestion list expands/shrinks in order to move down/up the
        //search results list
        mSearchView.setOnSuggestionsListHeightChanged { newHeight -> mRecyclerView.translationY = newHeight }
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): NotificationsFragment {
            val fragment = NotificationsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
