package com.sterlingng.paylite.ui.newpayment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.SendMoneyRequest
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import io.reactivex.disposables.CompositeDisposable
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

interface NewPaymentMvpContract<V : NewPaymentMvpView> : MvpPresenter<V> {
    fun loadCachedWallet()
}

interface NewPaymentMvpView : MvpView {
    fun initView(wallet: Wallet?)
}

class NewPaymentPresenter<V : NewPaymentMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), NewPaymentMvpContract<V> {

    override fun loadCachedWallet() {
        mvpView.initView(dataManager.getWallet())
    }
}

class NewPaymentActivity : BaseActivity(), NewPaymentMvpView {

    @Inject
    lateinit var mPresenter: NewPaymentMvpContract<NewPaymentMvpView>

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mRecipientNameEditText: EditText
    private lateinit var mPhoneEmailEditText: EditText
    private lateinit var mBalanceTextView: TextView

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_payment)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        next = findViewById(R.id.next)

        mRecipientNameEditText = findViewById(R.id.recipient_name)
        mPhoneEmailEditText = findViewById(R.id.phone_email)
        mBalanceTextView = findViewById(R.id.balance)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setUp() {
        mPresenter.loadCachedWallet()

        exit.setOnClickListener {
            onBackPressed()
        }

        next.setOnClickListener {
            if (mPhoneEmailEditText.text.isEmpty() || mRecipientNameEditText.text.isEmpty()) {
                show("The fields are required and cannot be empty", true)
                return@setOnClickListener
            }

            val intent = NewPaymentAmountActivity.getStartIntent(this)

            val request = SendMoneyRequest()
            request.rcpt = mPhoneEmailEditText.text.toString()
            request.channel = "Android Application"

            intent.putExtra(REQUEST, request)
            startActivity(intent)
        }

        mPhoneEmailEditText.setOnTouchListener { _, event ->

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (mPhoneEmailEditText.right - mPhoneEmailEditText.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                    Dexter.withActivity(this@NewPaymentActivity)
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
        mPhoneEmailEditText.setText(phone)
    }

    override fun initView(wallet: Wallet?) {
        mBalanceTextView.text = String.format("Balance ₦%,.2f", wallet?.balance?.toFloat())
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        const val DRAWABLE_RIGHT = 2
        const val REQUEST = "NewPaymentActivity.REQUEST"
        const val REQUEST_SELECT_CONTACT = 1001

        fun getStartIntent(context: Context): Intent {
            return Intent(context, NewPaymentActivity::class.java)
        }
    }
}