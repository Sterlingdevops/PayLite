package com.sterlingng.paylite.ui.airtime

import android.Manifest
import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Wallet
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.confirm.ConfirmFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import com.sterlingng.views.LargeLabelClickToSelectEditText
import com.sterlingng.views.LargeLabelEditText
import javax.inject.Inject

class AirTimeFragment : BaseFragment(), AirTimeMvpView, FilterBottomSheetFragment.OnFilterItemSelected {

    @Inject
    lateinit var mPresenter: AirTimeMvpContract<AirTimeMvpView>

    private lateinit var next: Button
    private lateinit var exit: ImageView
    private lateinit var amount: EditText
    private lateinit var bundle: EditText
    private lateinit var provider: EditText
    private lateinit var phone: LargeLabelEditText
    private lateinit var mBalanceTextView: TextView
    private lateinit var category: LargeLabelClickToSelectEditText<String>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_air_time, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun initView(wallet: Wallet?) {
        mBalanceTextView.text = String.format("Balance ₦%,.2f", wallet?.balance?.toFloat())
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        next.setOnClickListener {
            (baseActivity as DashboardActivity).mNavController.pushFragment(ConfirmFragment.newInstance())
        }

        bundle.isClickable = true
        bundle.setOnClickListener {
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.selector = 1
            filterBottomSheetFragment.title = "Select a bundle"
            filterBottomSheetFragment.items = listOf("30MB - 24hrs", "300MB - 48hrs", "3000MB - 72hrs", "Unlimted - 1 month")
            filterBottomSheetFragment.show(childFragmentManager, "filter")
        }

        category.mTextEditText.isClickable = true
        category.mTextEditText.setOnClickListener {
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.selector = 2
            filterBottomSheetFragment.title = "Project"
            filterBottomSheetFragment.items = listOf("Data bundle", "Mobile Top-up")
            filterBottomSheetFragment.show(childFragmentManager, "filter")
        }

        provider.isClickable = true
        provider.setOnClickListener {
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.selector = 0
            filterBottomSheetFragment.title = "Network provider"
            filterBottomSheetFragment.items = listOf("Airtel", "Glo", "MTN", "9 Mobile", "nTel")
            filterBottomSheetFragment.show(childFragmentManager, "filter")
        }

        phone.mTextEditText.setOnTouchListener { _, event ->

            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (phone.mTextEditText.right - phone.mTextEditText.compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
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
        val phoneCursor = baseActivity.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, /*Projection*/
                phoneSelection, /*Selection*/
                mSelectionArgs, null/*Sort order*/)/*Uri*//*Selection args*/

        // If the cursor returned is valid, get the phone number
        var phone = ""
        if (phoneCursor != null && phoneCursor.moveToFirst()) {
            val numberIndex = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NORMALIZED_NUMBER)
            phone = phoneCursor.getString(numberIndex).replace("+234", "")
        }
        phoneCursor?.close()

        if (TextUtils.isEmpty(phone)) {
            show("Invalid contact", true)
            return
        }
        this.phone.mTextEditText.setText(phone)
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)
        phone = view.findViewById(R.id.phone)
        amount = view.findViewById(R.id.amount)
        bundle = view.findViewById(R.id.bundles)
        category = view.findViewById(R.id.category)
        provider = view.findViewById(R.id.provider)
        mBalanceTextView = view.findViewById(R.id.balance)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        const val DRAWABLE_RIGHT = 2
        const val REQUEST_SELECT_CONTACT = 1001

        fun newInstance(): AirTimeFragment {
            val fragment = AirTimeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
