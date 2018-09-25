package com.sterlingng.paylite.ui.contacts

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.arlib.floatingsearchview.FloatingSearchView
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion
import com.google.gson.reflect.TypeToken
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.ChosenContact
import com.sterlingng.paylite.data.model.Contact
import com.sterlingng.paylite.data.model.ContactItem
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import javax.inject.Inject

class SelectContactsFragment : BaseFragment(), ContactsMvpView, RecyclerViewClickListener,
        SelectContactAdapter.OnRetryClicked, BaseActivity.OnBackClicked {
    @Inject
    lateinit var mPresenter: ContactsMvpContract<ContactsMvpView>

    @Inject
    lateinit var eventBus: EventBus

    private lateinit var mSearchView: FloatingSearchView

    private lateinit var titleTextView: TextView
    private lateinit var closeImageView: ImageView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mContactsAdapter: SelectContactAdapter
    private var selectedContact = -1
    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    lateinit var mContacts: List<Contact>

    lateinit var mContactsClone: List<Contact>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_select_contact, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        titleTextView = view.findViewById(R.id.title)
        closeImageView = view.findViewById(R.id.close)
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mSearchView = view.findViewById(R.id.floating_search_view)
    }

    override fun setUp(view: View) {
        mContactsAdapter = SelectContactAdapter(baseActivity)
        mContactsAdapter.mRecyclerViewClickListener = this
        mContactsAdapter.onRetryClickedListener = this
        mRecyclerView.adapter = mContactsAdapter
        mRecyclerView.layoutManager = mLinearLayoutManager

        titleTextView.text = arguments?.getString(TITLE)

        closeImageView.setOnClickListener {
            baseActivity.onBackPressed()
        }

        initSearchView()

        baseActivity.onBackClickedListener = this
        selectedContact = arguments?.getInt(POSITION, 0)!!

        mContactsClone = (baseActivity as DashboardActivity).contacts
        mContacts = (baseActivity as DashboardActivity).contacts
        mContactsAdapter.add(mContacts)
    }

    private fun initSearchView() {
        mSearchView.setShowSearchKey(true)
        mSearchView.setOnQueryChangeListener { oldQuery, newQuery ->
            if (oldQuery != "" && newQuery == "") {
                mSearchView.clearSuggestions()
                mContactsAdapter.clear()
                mContactsAdapter.add(mContactsClone)
            } else {
                val clone = ArrayList<Contact>()
                for (i in mContactsClone.indices) {
                    if (mContactsClone[i].name.toLowerCase().contains(newQuery.toLowerCase())) {
                        clone.add(mContactsClone[i])
                    }
                }
                mContacts = clone
                mSearchView.swapSuggestions(mContacts)
                mContactsAdapter.clear()
                mContactsAdapter.add(mContacts)
            }
        }

        mSearchView.setOnSearchListener(object : FloatingSearchView.OnSearchListener {
            override fun onSearchAction(currentQuery: String) {
                val clone = ArrayList<Contact>()
                for (i in mContactsClone.indices) {
                    if (mContactsClone[i].name.toLowerCase().contains(currentQuery.toLowerCase())) {
                        clone.add(mContactsClone[i])
                    }
                }
                mContacts = clone
                mSearchView.swapSuggestions(mContacts)
                mContactsAdapter.clear()
                mContactsAdapter.add(mContacts)
            }

            override fun onSuggestionClicked(searchSuggestion: SearchSuggestion) {
                val clone = ArrayList<Contact>()
                for (i in mContactsClone.indices) {
                    if (mContactsClone[i].name.toLowerCase().contains(searchSuggestion.body.toLowerCase())) {
                        clone.add(mContactsClone[i])
                    }
                }
                mContacts = clone
                mSearchView.swapSuggestions(mContacts)
                mContactsAdapter.clear()
                mContactsAdapter.add(mContacts)
                hideKeyboard()
            }
        })

        mSearchView.setOnClearSearchActionListener {
            mSearchView.clearSuggestions()
            mContactsAdapter.clear()
            mContactsAdapter.add(mContactsClone)
        }

        //listen for when suggestion list expands/shrinks in order to move down/up the
        //search results list
        mSearchView.setOnSuggestionsListHeightChanged { newHeight -> mRecyclerView.translationY = newHeight }
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {
        when (v.id) {
            R.id.contact_email -> {
                val email = mContacts[position].emails[0].address
                val type = object : TypeToken<ArrayList<ContactItem>>() {}.type
                val contactList = gson.fromJson<ArrayList<ContactItem>>(arguments?.getString(DATA), type)
                email?.let { contactList[selectedContact].contact = it }
                eventBus.post(ChosenContact(contactList))

                (baseActivity as DashboardActivity).mNavController.popFragment()
            }
            R.id.contact_phone -> {
                var number = mContactsAdapter.get(position).numbers[0].number
                number = number?.replace(" ", "")
                        ?.replace("+234", "0")
                val type = object : TypeToken<ArrayList<ContactItem>>() {}.type
                val contactList = gson.fromJson<ArrayList<ContactItem>>(arguments?.getString(DATA), type)
                number?.let { contactList[selectedContact].contact = it }
                eventBus.post(ChosenContact(contactList))

                (baseActivity as DashboardActivity).mNavController.popFragment()
            }
        }
    }

    override fun onRetryClicked() {

    }

    override fun onBackClicked() {
        val type = object : TypeToken<ArrayList<ContactItem>>() {}.type
        val contactList = gson.fromJson<ArrayList<ContactItem>>(arguments?.getString(DATA), type)
        eventBus.post(ChosenContact(contactList))
    }

    companion object {

        private const val DATA = "SelectContactsFragment.DATA"
        private const val TITLE = "SelectContactsFragment.TITLE"
        private const val POSITION = "SelectContactsFragment.POSITION"

        fun newInstance(title: String, position: Int, data: ArrayList<ContactItem>): SelectContactsFragment {
            val dialog = SelectContactsFragment()
            val args = Bundle()
            args.putString(TITLE, title)
            args.putInt(POSITION, position)
            args.putString(DATA, gson.toJson(data))
            dialog.arguments = args
            return dialog
        }
    }
}
