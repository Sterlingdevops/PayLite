package com.sterlingng.paylite.ui.signup

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.SignUpRequest
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.signup.complete.CompleteSignUpFragment
import com.sterlingng.paylite.ui.signup.email.EmailFragment
import com.sterlingng.paylite.ui.signup.name.NameFragment
import com.sterlingng.paylite.ui.signup.otp.OtpFragment
import com.sterlingng.paylite.ui.signup.phone.PhoneFragment
import com.sterlingng.paylite.ui.signup.pin.PinFragment
import com.sterlingng.paylite.utils.CustomPagerAdapter
import com.sterlingng.paylite.utils.OnChildDidClickNext
import com.sterlingng.paylite.utils.asString
import com.sterlingng.views.CustomViewPager
import javax.inject.Inject

class SignUpActivity : BaseActivity(), SignUpMvpView, OnChildDidClickNext {

    @Inject
    lateinit var mPresenter: SignUpMvpContract<SignUpMvpView>

    @Inject
    lateinit var mPagerAdapter: CustomPagerAdapter

    private lateinit var mViewPager: CustomViewPager

    val signUpRequest = SignUpRequest()
    var latitude: String = "0"
    var longitude: String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun bindViews() {
        mViewPager = findViewById(R.id.view_pager)
    }

    override fun setUp() {
        getLocation()

        val phoneFragment = PhoneFragment.newInstance(1)
        phoneFragment.mDidClickNext = this

        val otpFragment = OtpFragment.newInstance(2)
        otpFragment.mDidClickNext = this

        val emailFragment = EmailFragment.newInstance(3)
        emailFragment.mDidClickNext = this

        val nameFragment = NameFragment.newInstance(4)
        nameFragment.mDidClickNext = this

        val pinFragment = PinFragment.newInstance(5)
        pinFragment.mDidClickNext = this

        val confirmPinFragment = PinFragment.newInstance(6)
        confirmPinFragment.mDidClickNext = this

        val completeFragment = CompleteSignUpFragment.newInstance()

        mPagerAdapter.run {
            addFragment(phoneFragment, getString(R.string.phone))
            addFragment(otpFragment, getString(R.string.otp_ph))
            addFragment(emailFragment, getString(R.string.email))
            addFragment(nameFragment, getString(R.string.personal_details))
            addFragment(pinFragment, getString(R.string.set_pin))
            addFragment(confirmPinFragment, getString(R.string.confirm_password))
            addFragment(completeFragment, getString(R.string.account_setup_completed))
        }

        mViewPager.adapter = mPagerAdapter
        mViewPager.isPagingEnabled = false
    }

    override fun onBackPressed() {
        if (mViewPager.currentItem == 0)
            super.onBackPressed()
        else
            --mViewPager.currentItem
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun onPreviousClick(index: Int) {
        mViewPager.currentItem = index % mPagerAdapter.count
    }

    override fun onNextClick(index: Int, data: Any) {
        run case@{
            when (index) {
                1 -> {
                    signUpRequest.phoneNumber = "0${data as String}"
                }
                3 -> {
                    signUpRequest.email = data as String
                }
                4 -> {
                    with(data as List<String>) {
                        signUpRequest.firstName = get(0)
                        signUpRequest.lastName = get(1)
                        signUpRequest.dob  = get(2)
                        signUpRequest.gender = get(3)
                    }
                }
                5 -> {
                    signUpRequest.password = data as String
                }
            }
            mViewPager.currentItem = index % mPagerAdapter.count
        }
    }

    private fun getLocation() {
        Dexter.withActivity(this)
                .withPermissions(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : MultiplePermissionsListener {
                    @SuppressLint("MissingPermission")
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if (report?.isAnyPermissionPermanentlyDenied!!) {
                            val permissions = report.deniedPermissionResponses
                                    .asSequence()
                                    .filter { it.isPermanentlyDenied }
                                    .map { it.permissionName }

                            show("Please allow ${permissions.toList().asString()} " + "it has been permanently denied", true)
                        }

                        if (report.areAllPermissionsGranted()) {
                                val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, object : LocationListener {

                                    override fun onLocationChanged(location: Location?) {
                                        longitude = location?.longitude.toString()
                                        latitude = location?.latitude.toString()
                                    }

                                    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

                                    }

                                    override fun onProviderEnabled(provider: String) {

                                    }

                                    override fun onProviderDisabled(provider: String) {

                                    }
                                })

                        } else {
                            val permissions = report.deniedPermissionResponses.map { it.permissionName }
                            show("These permissions: ${permissions.asString()}" +
                                    " have been denied. Please enabled them to continue", true)
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }
                }).check()
    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }
}
