package com.sterlingng.paylite.di.component

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.di.module.ActivityModule
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.login.LogInActivity
import com.sterlingng.paylite.ui.main.MainActivity
import com.sterlingng.paylite.ui.signup.SignUpActivity
import com.sterlingng.paylite.ui.signup.complete.CompleteFragment
import com.sterlingng.paylite.ui.signup.email.EmailFragment
import com.sterlingng.paylite.ui.signup.name.NameFragment
import com.sterlingng.paylite.ui.signup.otp.OtpFragment
import com.sterlingng.paylite.ui.signup.password.PasswordFragment
import com.sterlingng.paylite.ui.signup.pin.PinFragment
import dagger.Component


/**
 * Created by rtukpe on 13/03/2018.
 */

@PerActivity
@Component(dependencies = [(ApplicationComponent::class)], modules = [(ActivityModule::class)])
interface ActivityComponent {

    // Activities

    fun inject(mainActivity: MainActivity)

    fun inject(logInActivity: LogInActivity)

    fun inject(onBoardingActivity: SignUpActivity)

    fun inject(dashboardActivity: DashboardActivity)

    // Fragments

    fun inject(otpFragment: OtpFragment)

    fun inject(pinFragment: PinFragment)

    fun inject(nameFragment: NameFragment)

    fun inject(emailFragment: EmailFragment)

    fun inject(countryFragment: PasswordFragment)

    fun inject(completeFragment: CompleteFragment)

    // Dialogs

}