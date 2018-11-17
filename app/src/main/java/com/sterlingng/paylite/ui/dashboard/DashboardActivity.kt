package com.sterlingng.paylite.ui.dashboard

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.CursorLoader
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.ncapdevi.fragnav.FragNavController
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Bank
import com.sterlingng.paylite.data.model.Contact
import com.sterlingng.paylite.data.model.UpdateTransaction
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.home.HomeFragment
import com.sterlingng.paylite.ui.main.MainActivity
import com.sterlingng.paylite.ui.services.ServicesFragment
import com.sterlingng.paylite.ui.settings.SettingsFragment
import com.sterlingng.paylite.ui.transactions.TransactionsFragment
import com.sterlingng.paylite.utils.asString
import java.util.HashMap
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.set


class DashboardActivity : BaseActivity(), DashboardMvpView,
        BottomNavigationView.OnNavigationItemSelectedListener,
        FragNavController.RootFragmentListener {

    private lateinit var mBottomNavigationView: BottomNavigationViewEx

    @Inject
    lateinit var mPresenter: DashboardMvpContract<DashboardMvpView>
    private var mSelectedItem = 0

    lateinit var mNavController: FragNavController
    override val numberOfRootFragments: Int
        get() = 4

    var contacts = ArrayList<Contact>()
    var banks = ArrayList<Bank>()

    @Inject
    lateinit var eventBus: EventBus

    override fun onResume() {
        super.onResume()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        activityComponent.inject(this)
        mPresenter.onAttach(this)

        Dexter.withActivity(this)
                .withPermissions(android.Manifest.permission.READ_CONTACTS,
                        android.Manifest.permission.WRITE_CONTACTS)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }

                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if (report?.isAnyPermissionPermanentlyDenied!!) {
                            val permissions = report.deniedPermissionResponses
                                    .asSequence()
                                    .filter { it.isPermanentlyDenied }
                                    .map { it.permissionName }

                            show("Please allow ${permissions.toList().asString()} " +
                                    "it has been permanently denied", true)
                        }

                        if (report.areAllPermissionsGranted()) {
                            contacts = setUpLoader()
                        } else {
                            val permissions = report.deniedPermissionResponses.map { it.permissionName }
                            show("These permissions: ${permissions.asString()}" +
                                    " have been denied. Please enabled them to continue", true)
                        }
                    }
                }).check()

        mNavController = FragNavController(supportFragmentManager, R.id.container)
        mNavController.apply {
            createEager = true
            fragmentHideStrategy = FragNavController.DETACH_ON_NAVIGATE_HIDE_ON_SWITCH
            rootFragmentListener = this@DashboardActivity
            initialize(savedInstanceState = savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SELECTED_ITEM, mSelectedItem)
        super.onSaveInstanceState(outState)
        mNavController.onSaveInstanceState(outState)
    }

    override fun bindViews() {
        mBottomNavigationView = findViewById(R.id.navigation)
    }

    override fun setUp() {
        mPresenter.onViewInitialized()
        mPresenter.loadBanks()

        mBottomNavigationView.apply {
            labelVisibilityMode = 1
            itemIconTintList = null
            setSmallTextSize(10f)
            setLargeTextSize(12f)
            enableAnimation(true)
            isItemHorizontalTranslationEnabled = false
            onNavigationItemSelectedListener = this@DashboardActivity
        }
        for (i in 0 until mBottomNavigationView.itemCount) {
            val view = mBottomNavigationView.getLargeLabelAt(i)
            view.setPadding(0, 0, 0, 0)
        }
    }

    override fun getRootFragment(index: Int): Fragment {
        return when (index) {
            INDEX_HOME -> HomeFragment.newInstance()
            INDEX_PROFILE -> SettingsFragment.newInstance()
            INDEX_SERVICES -> ServicesFragment.newInstance()
            INDEX_TRANSACTIONS -> TransactionsFragment.newInstance()
            else -> throw IllegalStateException("Need to send an index that we know")
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> mNavController.switchTab(INDEX_HOME)
            R.id.nav_profile -> mNavController.switchTab(INDEX_PROFILE)
            R.id.nav_services -> mNavController.switchTab(INDEX_SERVICES)
            R.id.nav_transactions -> {
                eventBus.post(UpdateTransaction())
                mNavController.switchTab(INDEX_TRANSACTIONS)
            }
        }
        return true
    }

    override fun onBackPressed() {
        when {
            mNavController.isRootFragment.not() -> {
                if (onBackClickedListener != null) {
                    onBackClickedListener?.onBackClicked()
                }
                mNavController.popFragment()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    private fun setUpLoader(): ArrayList<Contact> {
        val contacts = ArrayList<Contact>()
        val contentResolver: ContentResolver = contentResolver
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
            val contactDisplayName: String? = cursor.getString(nameIndex)
            val contact = contactDisplayName?.let { Contact(contactId, it) }

            contact?.let { contactsMap[contactId] = it }
            contact?.let { contacts.add(it) }
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

        val phone = CursorLoader(this,
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
                val phoneType = ContactsContract.CommonDataKinds.Phone.getTypeLabel(resources, type, customLabel)
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

        val email = CursorLoader(this,
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
                val emailType = ContactsContract.CommonDataKinds.Email.getTypeLabel(resources, type, customLabel)
                contact.addEmail(address, emailType.toString())
                email.moveToNext()
            }
        }
        email.close()
    }

    override fun onGetBanksSuccessful(banks: ArrayList<Bank>) {
        this.banks.addAll(banks)
    }

    override fun onGetBanksFailed() {

    }

    override fun logout() {
        show("Session has timed out", true)
        startActivity(MainActivity.getStartIntent(this))
        finish()
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {
        private const val INDEX_HOME = FragNavController.TAB1
        private const val INDEX_PROFILE = FragNavController.TAB4
        private const val INDEX_SERVICES = FragNavController.TAB3
        private const val INDEX_TRANSACTIONS = FragNavController.TAB2
        const val SELECTED_ITEM = "arg_selected_item"

        fun getStartIntent(context: Context): Intent {
            val intent = Intent(context, DashboardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            return intent
        }
    }
}
