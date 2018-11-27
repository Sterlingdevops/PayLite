package com.sterlingng.paylite.ui.getcash

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
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
import com.sterlingng.paylite.data.model.UpdateWallet
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import javax.inject.Inject

class GetCashFragment : BaseFragment(), GetCashMvpView {

    @Inject
    lateinit var mPresenter: GetCashMvpContract<GetCashMvpView>

    @Inject
    lateinit var eventBus: EventBus

    private lateinit var mBalanceTextView: TextView

    private lateinit var mSelfTextView: TextView
    private lateinit var mSelfCheckBox: CheckBox

    private lateinit var mOthersTextView: TextView
    private lateinit var mOthersCheckBox: CheckBox

    private lateinit var mSelfNestedScrollView: NestedScrollView
    private lateinit var mOthersNestedScrollView: NestedScrollView

    private lateinit var mAmountTextView: TextView
    private lateinit var mPasswordTextView: TextView

    private lateinit var mOtherPhoneTextView: EditText
    private lateinit var mOtherAmountTextView: EditText
    private lateinit var mOtherPasswordTextView: TextView

    private var others: Boolean = false

    lateinit var exit: ImageView
    lateinit var next: Button

    @SuppressLint("ClickableViewAccessibility")
    override fun setUp(view: View) {
        mPresenter.onViewInitialized()

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        next.setOnClickListener {
            val data = HashMap<String, Any>()

            if (others) {
                data["mobile"] = mOtherPhoneTextView.text.toString()
                data["Amount"] = mOtherAmountTextView.text.toString()
                data["OneTimePin"] = mOtherPasswordTextView.text.toString()
                mPresenter.cashOutViaPayCode(data, false)
            } else {
                data["Amount"] = mAmountTextView.text.toString()
                data["OneTimePin"] = mPasswordTextView.text.toString()
                mPresenter.cashOutViaPayCode(data, true)
            }
        }

        others = false
        mSelfCheckBox.isChecked = true
        mOthersCheckBox.isChecked = false
        mOthersNestedScrollView.visibility = View.GONE
        mSelfNestedScrollView.visibility = View.VISIBLE

        mSelfTextView.setOnClickListener {
            others = false
            mSelfCheckBox.isChecked = true
            mOthersCheckBox.isChecked = false
            mOthersNestedScrollView.visibility = View.GONE
            mSelfNestedScrollView.visibility = View.VISIBLE
        }

        mOthersTextView.setOnClickListener {
            others = true
            mSelfCheckBox.isChecked = false
            mOthersCheckBox.isChecked = true
            mOthersNestedScrollView.visibility = View.VISIBLE
            mSelfNestedScrollView.visibility = View.GONE
        }

        val drawable = ContextCompat.getDrawable(baseActivity, R.drawable.icon_phone_book)
        drawable?.setBounds(0, 0, (drawable.intrinsicWidth * 0.7).toInt(), (drawable.intrinsicHeight * 0.7).toInt())
        mOtherPhoneTextView.setCompoundDrawables(null, null, drawable, null)
        mOtherPhoneTextView.setOnTouchListener { _, event ->

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (mOtherPhoneTextView.right - mOtherPhoneTextView.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
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
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                phoneSelection,
                mSelectionArgs,
                null)

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
        mOtherPhoneTextView.setText(phone)
    }

    override fun bindViews(view: View) {
        mBalanceTextView = view.findViewById(R.id.balance)

        mSelfTextView = view.findViewById(R.id.self)
        mSelfCheckBox = view.findViewById(R.id.self_checkBox)

        mOthersTextView = view.findViewById(R.id.others)
        mOthersCheckBox = view.findViewById(R.id.others_checkBox)

        next = view.findViewById(R.id.next)
        exit = view.findViewById(R.id.exit)

        mAmountTextView = view.findViewById(R.id.amount)
        mPasswordTextView = view.findViewById(R.id.password)

        mOtherPhoneTextView = view.findViewById(R.id.phone_other)
        mOtherAmountTextView = view.findViewById(R.id.amount_other)
        mOtherPasswordTextView = view.findViewById(R.id.password_other)

        mSelfNestedScrollView = view.findViewById(R.id.self_scrollview)
        mOthersNestedScrollView = view.findViewById(R.id.others_scrollview)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun onCashOutSuccessful() {
        eventBus.post(UpdateWallet())
        (baseActivity as DashboardActivity)
                .mNavController.clearStack()
    }

    override fun onCashOutFailed() {
        show("An error occurred while processing the transaction", true)
    }

    override fun logout() {
        baseActivity.logout()
    }

    override fun initView(wallet: Wallet) {
        mBalanceTextView.text = String.format("₦%,.0f", wallet.balance.toFloat())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_get_cash, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    companion object {

        const val DRAWABLE_RIGHT = 2
        const val REQUEST_SELECT_CONTACT = 1001

        fun newInstance(): GetCashFragment {
            val fragment = GetCashFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}