package com.sterlingng.paylite.ui.newpayment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.SendMoneyRequest
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.newpaymentamount.NewPaymentAmountFragment
import java.util.regex.Pattern
import javax.inject.Inject

class NewPaymentFragment : BaseFragment(), NewPaymentMvpView {

    @Inject
    lateinit var mPresenter: NewPaymentMvpContract<NewPaymentMvpView>

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mRecipientNameEditText: EditText
    private lateinit var mPhoneEmailEditText: EditText
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

        next.setOnClickListener {
            if (mPhoneEmailEditText.text.isEmpty() || mRecipientNameEditText.text.isEmpty()) {
                show("The fields are required and cannot be empty", true)
                return@setOnClickListener
            }

            val pattern = Pattern.compile("^[A-z0-9_]{1,15}$")
            val matcher = pattern.matcher(mPhoneEmailEditText.text.toString())
            if (!matcher.matches()) {
                show("Invalid username, please choose another username", true)
                return@setOnClickListener
            }

            val request = SendMoneyRequest()
            request.recipientHandle = mPhoneEmailEditText.text.toString()
            request.recipientName = mRecipientNameEditText.text.toString()
            request.paymentReference = System.currentTimeMillis().toString()

            (baseActivity as DashboardActivity).mNavController.pushFragment(NewPaymentAmountFragment.newInstance(request))
        }

//        mPhoneEmailEditText.setOnTouchListener { _, event ->
//
//            if (event.action == MotionEvent.ACTION_UP) {
//                if (event.rawX >= (mPhoneEmailEditText.right - mPhoneEmailEditText.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
//                    Dexter.withActivity(baseActivity)
//                            .withPermission(Manifest.permission.READ_CONTACTS)
//                            .withListener(object : PermissionListener {
//                                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
//                                    token?.continuePermissionRequest()
//                                }
//
//                                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
//                                    show("Permission Denied", true)
//                                }
//
//                                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
//                                    val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
//                                    if (intent.resolveActivity(baseActivity.packageManager) != null) {
//                                        startActivityForResult(intent, REQUEST_SELECT_CONTACT)
//                                    }
//                                }
//
//                            }).check()
//                    return@setOnTouchListener true
//                }
//            }
//            return@setOnTouchListener false
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_SELECT_CONTACT -> {
                if (data != null) handleContactPickerResult(data.data)
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
        var phone = ""
        if (phoneCursor != null && phoneCursor.moveToFirst()) {
            val numberIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER)
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
        mBalanceTextView.text = String.format("Balance ₦%,.2f", wallet?.balance?.toFloat())
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        const val DRAWABLE_RIGHT = 2
        const val REQUEST_SELECT_CONTACT = 1001

        fun newInstance(): NewPaymentFragment {
            val fragment = NewPaymentFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}