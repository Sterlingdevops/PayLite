package com.sterlingng.paylite.ui.airtime

import android.Manifest
import android.animation.Animator
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.confirm.ConfirmActivity
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import com.sterlingng.paylite.utils.ClickToSelectEditText
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class AirTimeActivity : BaseActivity(), AirTimeMvpView, FilterBottomSheetFragment.OnFilterItemSelected {

    @Inject
    lateinit var mPresenter: AirTimeMvpContract<AirTimeMvpView>

    private lateinit var next: Button
    private lateinit var exit: ImageView
    private lateinit var contactPicker: ImageView
    private lateinit var bundleHolder: TextInputLayout
    private lateinit var phoneEditText: TextInputEditText
    private lateinit var bundle: ClickToSelectEditText<String>
    private lateinit var category: ClickToSelectEditText<String>
    private lateinit var provider: ClickToSelectEditText<String>

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_air_time)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun setUp() {
        exit.setOnClickListener {
            onBackPressed()
        }

        next.setOnClickListener {
            startActivity(ConfirmActivity.getStartIntent(this))
        }

        bundle.isClickable = true
        bundle.setOnClickListener {
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.selector = 1
            filterBottomSheetFragment.title = "Select a bundle"
            filterBottomSheetFragment.items = listOf("30MB - 24hrs", "300MB - 48hrs", "3000MB - 72hrs", "Unlimted - 1 month")
            filterBottomSheetFragment.show(supportFragmentManager, "filter")
        }

        category.isClickable = true
        category.setOnClickListener {
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.selector = 2
            filterBottomSheetFragment.title = "Category"
            filterBottomSheetFragment.items = listOf("Data bundle", "Mobile Top-up")
            filterBottomSheetFragment.show(supportFragmentManager, "filter")
        }

        provider.isClickable = true
        provider.setOnClickListener {
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.selector = 0
            filterBottomSheetFragment.title = "Network provider"
            filterBottomSheetFragment.items = listOf("Airtel", "Glo", "MTN", "9 Mobile", "nTel")
            filterBottomSheetFragment.show(supportFragmentManager, "filter")
        }

        contactPicker.setOnClickListener {
            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.READ_CONTACTS)
                    .withListener(object : PermissionListener {
                        override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                            token?.continuePermissionRequest()
                        }

                        override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                            show("Permission Denied", true)
                        }

                        override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                            val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
                            if (intent.resolveActivity(packageManager) != null) {
                                startActivityForResult(intent, REQUEST_SELECT_CONTACT)
                            }
                        }

                    })
                    .check()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_SELECT_CONTACT -> {
                if (data != null)
                    handleContactPickerResult(data.data)
            }
        }
    }

    override fun onFilterItemSelected(dialog: Dialog, selector: Int, s: String) {
        when (selector) {
            1 -> bundle.setText(s)
            0 -> provider.setText(s)
            2 -> {
                when (s) {
                    "Mobile Top-up" -> {
                        bundleHolder.animate()
                                .scaleX(0f)
                                .scaleY(0f)
                                .setListener(object : Animator.AnimatorListener {
                                    override fun onAnimationRepeat(animation: Animator?) {

                                    }

                                    override fun onAnimationCancel(animation: Animator?) {

                                    }

                                    override fun onAnimationStart(animation: Animator?) {

                                    }

                                    override fun onAnimationEnd(animation: Animator?) {
                                        bundleHolder.visibility = View.GONE
                                    }
                                }).start()
                    }
                    "Data bundle" -> {
                        bundleHolder.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setListener(object : Animator.AnimatorListener {
                                    override fun onAnimationRepeat(animation: Animator?) {

                                    }

                                    override fun onAnimationCancel(animation: Animator?) {

                                    }

                                    override fun onAnimationStart(animation: Animator?) {

                                    }

                                    override fun onAnimationEnd(animation: Animator?) {
                                        bundleHolder.visibility = View.VISIBLE
                                    }
                                }).start()
                    }
                }
                category.setText(s)
            }
        }
        dialog.dismiss()
    }

    private fun handleContactPickerResult(contactUri: Uri) {
        val lookUpKey = contactUri.lastPathSegment
        val mSelectionArgs = arrayOf(lookUpKey)

        val phoneSelection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?"
        val phoneCursor = this.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, /*Projection*/
                phoneSelection, /*Selection*/
                mSelectionArgs, null/*Sort order*/)/*Uri*//*Selection args*/

        // If the cursor returned is valid, get the phone number
        var phone = ""
        if (phoneCursor != null && phoneCursor.moveToFirst()) {
            val numberIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER)
            phone = phoneCursor.getString(numberIndex)
        }
        phoneCursor?.close()

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Invalid contact", Toast.LENGTH_LONG).show()
            return
        }
        phoneEditText.setText(phone)
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        next = findViewById(R.id.next)
        bundle = findViewById(R.id.bundles)
        category = findViewById(R.id.category)
        provider = findViewById(R.id.provider)
        phoneEditText = findViewById(R.id.phone)
        contactPicker = findViewById(R.id.contacts)
        bundleHolder = findViewById(R.id.textInputLayout5)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        const val REQUEST_SELECT_CONTACT = 1001

        fun getStartIntent(context: Context): Intent {
            return Intent(context, AirTimeActivity::class.java)
        }
    }
}
