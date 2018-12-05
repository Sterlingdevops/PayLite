package com.sterlingng.paylite.ui.services.mtn

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.newpayment.NewPaymentFragment
import com.sterlingng.views.LargeLabelEditText
import javax.inject.Inject

class MtnServiceFragment : BaseFragment(), MtnServiceMvpView {

    @Inject
    lateinit var mPresenter: MtnServiceMvpContract<MtnServiceMvpView>

    private lateinit var exit: ImageView
    private lateinit var mPhoneEmailEditText: LargeLabelEditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mtn_service, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mPhoneEmailEditText = view.findViewById(R.id.phone_email)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        val drawable = ContextCompat.getDrawable(baseActivity, R.drawable.icon_phone_book)
        drawable?.setBounds(0, 0, (drawable.intrinsicWidth * 0.7).toInt(), (drawable.intrinsicHeight * 0.7).toInt())
        mPhoneEmailEditText.mTextEditText.setCompoundDrawables(null, null, drawable, null)
        mPhoneEmailEditText.mTextEditText.setOnTouchListener { _, event ->

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (mPhoneEmailEditText.right - mPhoneEmailEditText.mTextEditText.compoundDrawables[NewPaymentFragment.DRAWABLE_RIGHT].bounds.width())) {
                    Dexter.withActivity(baseActivity)
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
                                    if (intent.resolveActivity(baseActivity.packageManager) != null) {
                                        startActivityForResult(intent, NewPaymentFragment.REQUEST_SELECT_CONTACT)
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
            NewPaymentFragment.REQUEST_SELECT_CONTACT -> {
                data?.data?.let { handleContactPickerResult(it) }
            }
        }
    }

    private fun handleContactPickerResult(contactUri: Uri) {
        val lookUpKey = contactUri.lastPathSegment
        val mSelectionArgs = arrayOf(lookUpKey)

        val phoneSelection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?"
        val phoneCursor = baseActivity.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, /*Projection*/
                phoneSelection,
                mSelectionArgs, null)

        // If the cursor returned is valid, get the phone number
        var phone: String? = ""
        if (phoneCursor != null && phoneCursor.moveToFirst()) {
            val numberIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER)
            if (phoneCursor.getString(numberIndex) != null)
                phone = phoneCursor.getString(numberIndex).replace("+234", "0")
        }
        phoneCursor?.close()

        if (TextUtils.isEmpty(phone)) {
            show("Invalid contact", true)
            return
        }
        mPhoneEmailEditText.mTextEditText.setText(phone)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): MtnServiceFragment {
            val fragment = MtnServiceFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
