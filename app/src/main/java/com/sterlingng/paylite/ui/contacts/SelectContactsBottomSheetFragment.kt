package com.sterlingng.paylite.ui.contacts

import android.content.ContentResolver
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.content.CursorLoader
import android.support.v7.widget.DividerItemDecoration
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
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.send.ContactsAdapter
import com.sterlingng.paylite.utils.AppUtils.gson
import com.sterlingng.paylite.utils.RecyclerViewClickListener
import java.util.HashMap
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.set

class ContactsFragment : BaseFragment(), ContactsMvpView, RecyclerViewClickListener,
        ContactsAdapter.OnRetryClicked, SelectContactAdapter.OnRetryClicked {

    @Inject
    lateinit var mPresenter: ContactsMvpContract<ContactsMvpView>

    @Inject
    lateinit var eventBus: EventBus

    private lateinit var mSearchView: FloatingSearchView
    private lateinit var titleTextView: TextView
    private lateinit var closeImageView: ImageView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mContactsAdapter: SelectContactAdapter
    var selectedContact = -1

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mDividerItemDecoration: DividerItemDecoration

    lateinit var mContacts: List<Contact>
    lateinit var mContactsClone: List<Contact>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_select_contact_bottom_sheet, container, false)
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
        mRecyclerView.addItemDecoration(mDividerItemDecoration)

        titleTextView.text = arguments?.getString(TITLE)
        closeImageView.setOnClickListener {
            baseActivity.onBackPressed()
        }

        initSearchView()

        selectedContact = arguments?.getInt(POSITION, 0)!!

        mContactsClone = setUpLoader()
        mContacts = setUpLoader()
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

    override fun recyclerViewListClicked(v: View, position: Int) {
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

    private fun setUpLoader(): ArrayList<Contact> {
        val contacts = ArrayList<Contact>()
        val contentResolver: ContentResolver = baseActivity.contentResolver
        val projectionFields = arrayOf(
                ContactsContract.Data._ID,
                ContactsContract.Data.LOOKUP_KEY,
                ContactsContract.Data.DISPLAY_NAME
        )
        val cursor: Cursor? = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                projectionFields, // projection fields
                null, // the selection criteria
                null, // the selection args
                null // the sort order
        )

        if (cursor == null || cursor.count <= 0)
            return arrayListOf()

        val contactsMap = HashMap<String, Contact>(cursor.count)

        val idIndex = cursor.getColumnIndex(ContactsContract.Data._ID)
        val nameIndex = cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME)

        while (cursor.moveToNext()) {
            val contactId = cursor.getString(idIndex)
            val contactDisplayName = cursor.getString(nameIndex)
            val contact = Contact(contactId, contactDisplayName)

            contactsMap[contactId] = contact
            contacts += contact
        }

        cursor.close()

        loadContactEmails(contactsMap)
        loadContactNumbers(contactsMap)

        contacts.sortBy { it.name }
        return contacts
    }

    private fun loadContactNumbers(contactsMap: Map<String, Contact>) {
        // Get numbers
        val numberProjection = arrayOf(
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        )

        val phone = CursorLoader(baseActivity,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                numberProjection, null, null, null).loadInBackground()

        if (phone!!.moveToFirst()) {
            val contactNumberColumnIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val contactTypeColumnIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE)
            val contactIdColumnIndex = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)

            while (!phone.isAfterLast) {
                val number = phone.getString(contactNumberColumnIndex)
                val contactId = phone.getString(contactIdColumnIndex)
                val contact = contactsMap[contactId] ?: continue
                val type = phone.getInt(contactTypeColumnIndex)
                val customLabel = "Custom"
                val phoneType = ContactsContract.CommonDataKinds.Phone.getTypeLabel(baseActivity.resources, type, customLabel)
                contact.addNumber(number, phoneType.toString())
                phone.moveToNext()
            }
        }
        phone.close()
    }

    private fun loadContactEmails(contactsMap: Map<String, Contact>) {
        // Get email
        val emailProjection = arrayOf(
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Email.TYPE,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID
        )

        val email = CursorLoader(baseActivity,
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                emailProjection, null, null, null).loadInBackground()

        if (email!!.moveToFirst()) {
            val contactEmailColumnIndex = email.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)
            val contactTypeColumnIndex = email.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE)
            val contactIdColumnsIndex = email.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID)

            while (!email.isAfterLast) {
                val address = email.getString(contactEmailColumnIndex)
                val contactId = email.getString(contactIdColumnsIndex)
                val type = email.getInt(contactTypeColumnIndex)
                val customLabel = "Custom"
                val contact = contactsMap[contactId] ?: continue
                val emailType = ContactsContract.CommonDataKinds.Email.getTypeLabel(baseActivity.resources, type, customLabel)
                contact.addEmail(address, emailType.toString())
                email.moveToNext()
            }
        }
        email.close()
    }


    override fun onRetryClicked() {

    }

    companion object {

        private const val TITLE = "ContactsFragment.TITLE"
        private const val POSITION = "ContactsFragment.POSITION"
        private const val DATA = "ContactsFragment.DATA"

        fun newInstance(title: String, position: Int, data: ArrayList<ContactItem>): ContactsFragment {
            val dialog = ContactsFragment()
            val args = Bundle()
            args.putString(TITLE, title)
            args.putInt(POSITION, position)
            args.putString(DATA, gson.toJson(data))
            dialog.arguments = args
            return dialog
        }
    }
}
