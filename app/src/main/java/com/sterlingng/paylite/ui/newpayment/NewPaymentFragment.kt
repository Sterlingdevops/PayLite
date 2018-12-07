package com.sterlingng.paylite.ui.newpayment

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
import android.widget.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.PayliteContact
import com.sterlingng.paylite.data.model.SendMoneyRequest
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.newpaymentamount.NewPaymentAmountFragment
import com.sterlingng.paylite.utils.AppConstants.DRAWABLE_RIGHT
import com.sterlingng.paylite.utils.AppConstants.REQUEST_SELECT_CONTACT
import com.sterlingng.paylite.utils.isValidEmail
import java.util.regex.Pattern
import javax.inject.Inject


class NewPaymentFragment : BaseFragment(), NewPaymentMvpView {

    @Inject
    lateinit var mPresenter: NewPaymentMvpContract<NewPaymentMvpView>

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mRecipientNameEditText: EditText
    private lateinit var mSaveRecipientTextView: TextView
    private lateinit var mPhoneEmailEditText: EditText
    private lateinit var mSaveRecipientSwitch: Switch
    private lateinit var mBalanceTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_payment, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)

        mSaveRecipientSwitch = view.findViewById(R.id.save_recipient_switch)
        mSaveRecipientTextView = view.findViewById(R.id.save_recipient)

        mRecipientNameEditText = view.findViewById(R.id.recipient_name)
        mPhoneEmailEditText = view.findViewById(R.id.phone_email)
        mBalanceTextView = view.findViewById(R.id.balance)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setUp(view: View) {
        mPresenter.loadCachedWallet()

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mSaveRecipientTextView.setOnClickListener {
            mSaveRecipientSwitch.toggle()
        }

        next.setOnClickListener {
            if (mPhoneEmailEditText.text.isEmpty() || mRecipientNameEditText.text.isEmpty()) {
                show("The fields are required and cannot be empty", true)
                return@setOnClickListener
            }

            val pattern = Pattern.compile("[0-9]{11}")
            val matcher = pattern.matcher(mPhoneEmailEditText.text.toString())
            if (!mPhoneEmailEditText.text.toString().isValidEmail() && (mPhoneEmailEditText.text.length != 11 || !matcher.matches())) {
                show("Please enter a valid email or phone number", true)
                return@setOnClickListener
            }

            val request = SendMoneyRequest()
            val contact = PayliteContact()
            contact.id = mRecipientNameEditText.text.toString().toLowerCase()
            contact.name = mRecipientNameEditText.text.toString()

            if (mPhoneEmailEditText.text.toString().isValidEmail()) {
                request.email = mPhoneEmailEditText.text.toString()
                contact.email = mPhoneEmailEditText.text.toString()
            } else {
                contact.phone = mPhoneEmailEditText.text.toString()
                request.phone = mPhoneEmailEditText.text.toString()
            }

            request.recipientName = mRecipientNameEditText.text.toString()
            request.paymentReference = System.currentTimeMillis().toString()

            if (mSaveRecipientSwitch.isChecked) {
                (baseActivity as DashboardActivity)
                        .mNavController
                        .pushFragment(NewPaymentAmountFragment.newInstance(request, contact))
            } else {
                (baseActivity as DashboardActivity)
                        .mNavController
                        .pushFragment(NewPaymentAmountFragment.newInstance(request))
            }
        }

        val drawable = ContextCompat.getDrawable(baseActivity, R.drawable.icon_phone_book)
        drawable?.setBounds(0, 0, (drawable.intrinsicWidth * 0.7).toInt(), (drawable.intrinsicHeight * 0.7).toInt())
        mPhoneEmailEditText.setCompoundDrawables(null, null, drawable, null)

        mPhoneEmailEditText.setOnTouchListener { _, event ->

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (mPhoneEmailEditText.right - mPhoneEmailEditText.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
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
                phoneSelection, /*Selection*/
                mSelectionArgs, null/*Sort order*/)/*Uri*//*Selection args*/

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
        mPhoneEmailEditText.setText(phone)
    }

    override fun initView(wallet: Wallet?) {
        mBalanceTextView.text = String.format("Balance: ₦%,.0f", wallet?.balance?.toFloat())
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): NewPaymentFragment {
            val fragment = NewPaymentFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}