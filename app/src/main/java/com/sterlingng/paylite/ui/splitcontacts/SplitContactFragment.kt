package com.sterlingng.paylite.ui.splitcontacts

import android.Manifest
import android.app.Dialog
import android.content.ContentResolver
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.content.CursorLoader
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Contact
import com.sterlingng.paylite.data.model.ContactItem
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import com.sterlingng.paylite.ui.contacts.ContactsBottomSheetFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import io.reactivex.disposables.CompositeDisposable
import java.util.HashMap
import javax.inject.Inject
import kotlin.collections.ArrayList

class SplitContactFragment : BaseFragment(), SplitContactMvpView,
        ContactsBottomSheetFragment.OnContactsItemSelected {

    @Inject
    lateinit var mPresenter: SplitContactMvpContract<SplitContactMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    private lateinit var mAddContactTextView: TextView
    private lateinit var mSplitCostTextView: TextView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mBalanceTextView: TextView
    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mSplitContactsAdapter: SplitContactsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_split_contact, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)

        mBalanceTextView = view.findViewById(R.id.balance)
        mSplitCostTextView = view.findViewById(R.id.title)
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mAddContactTextView = view.findViewById(R.id.add_contact)
    }

    override fun setUp(view: View) {
        arguments?.getString(AMOUNT).let {
            mSplitCostTextView.text = baseActivity.resources.getString(R.string.split_ngn, it)
        }

        mSplitContactsAdapter = SplitContactsAdapter(baseActivity)
        mSplitContactsAdapter.mRecyclerViewClickListener = this
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.adapter = mSplitContactsAdapter

        mSplitContactsAdapter.add(ContactItem())
        mSplitContactsAdapter.add(ContactItem())

        mAddContactTextView.setOnClickListener {
            mSplitContactsAdapter.add(ContactItem())
        }

        next.setOnClickListener {

        }

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }
    }

    fun setUpLoader(): ArrayList<Contact> {
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

    override fun recyclerViewListClicked(v: View, position: Int) {
        Dexter.withActivity(baseActivity)
                .withPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }

                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        val selectBottomSheetFragment = ContactsBottomSheetFragment.newInstance()
                        selectBottomSheetFragment.onContactsItemSelectedListener = this@SplitContactFragment
                        selectBottomSheetFragment.selector = position
                        selectBottomSheetFragment.title = "Contacts"
                        selectBottomSheetFragment.contacts = setUpLoader()
                        (baseActivity as DashboardActivity).mNavController.showDialogFragment(selectBottomSheetFragment)
                    }
                }).check()
    }

    override fun onContactsItemSelected(dialog: Dialog, selector: Int, contact: Contact) {
        mSplitContactsAdapter.contacts[selector].contact = contact.name
        mSplitContactsAdapter.notifyItemChanged(selector)
        dialog.dismiss()
    }

    companion object {

        private const val AMOUNT = "SplitContactFragment.AMOUNT"

        fun newInstance(amount: String): SplitContactFragment {
            val fragment = SplitContactFragment()
            val args = Bundle()
            args.putString(AMOUNT, amount)
            fragment.arguments = args
            return fragment
        }
    }
}

interface SplitContactMvpView : MvpView

interface SplitContactMvpContract<V : SplitContactMvpView> : MvpPresenter<V>

class SplitContactPresenter<V : SplitContactMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), SplitContactMvpContract<V>