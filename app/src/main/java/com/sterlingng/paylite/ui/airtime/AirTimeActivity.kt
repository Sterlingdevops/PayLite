package com.sterlingng.paylite.ui.airtime

import android.Manifest
import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
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
import com.sterlingng.paylite.utils.Log
import com.sterlingng.views.LargeLabelClickToSelectEditText
import com.sterlingng.views.LargeLabelEditText
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class AirTimeActivity : BaseActivity(), AirTimeMvpView, FilterBottomSheetFragment.OnFilterItemSelected {

    @Inject
    lateinit var mPresenter: AirTimeMvpContract<AirTimeMvpView>

    private lateinit var next: Button
    private lateinit var exit: ImageView
    private lateinit var phone: LargeLabelEditText
    private lateinit var amount: EditText
    private lateinit var bundle: EditText
    private lateinit var provider: EditText
    private lateinit var category: LargeLabelClickToSelectEditText<String>

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_air_time)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    @SuppressLint("ClickableViewAccessibility")
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

        category.mTextEditText.isClickable = true
        category.mTextEditText.setOnClickListener {
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.selector = 2
            filterBottomSheetFragment.title = "Project"
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

        phone.mTextEditText.setOnTouchListener { _, event ->
            val DRAWABLE_RIGHT = 2

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (phone.mTextEditText.right - phone.mTextEditText.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                    Log.d("phone.mTextEditText.setDrawableClickListener")
                    Dexter.withActivity(this@AirTimeActivity)
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

                            }).check()
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
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
                        bundle.animate()
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
                                        bundle.visibility = View.GONE
                                    }
                                }).start()
                    }
                    "Data bundle" -> {
                        bundle.animate()
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
                                        bundle.visibility = View.VISIBLE
                                    }
                                }).start()
                    }
                }
                category.mTextEditText.setText(s)
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
        this.phone.mTextEditText.setText(phone)
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        next = findViewById(R.id.next)
        phone = findViewById(R.id.phone)
        amount = findViewById(R.id.amount)
        bundle = findViewById(R.id.bundles)
        category = findViewById(R.id.category)
        provider = findViewById(R.id.provider)
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
