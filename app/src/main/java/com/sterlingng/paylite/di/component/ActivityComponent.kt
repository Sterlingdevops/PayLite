package com.sterlingng.paylite.di.component

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.di.module.ActivityModule
import com.sterlingng.paylite.ui.confirm.ConfirmActivity
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.donate.DonateActivity
import com.sterlingng.paylite.ui.donate.repeat.RepeatBottomSheetFragment
import com.sterlingng.paylite.ui.give.GiveFragment
import com.sterlingng.paylite.ui.give.charities.CharitiesFragment
import com.sterlingng.paylite.ui.give.filter.FilterBottomSheetFragment
import com.sterlingng.paylite.ui.give.projects.ProjectsFragment
import com.sterlingng.paylite.ui.home.HomeFragment
import com.sterlingng.paylite.ui.login.LogInActivity
import com.sterlingng.paylite.ui.main.MainActivity
import com.sterlingng.paylite.ui.main.onboarding.OnBoardingFragment
import com.sterlingng.paylite.ui.payment.PaymentFragment
import com.sterlingng.paylite.ui.payment.pay.PayFragment
import com.sterlingng.paylite.ui.payment.request.RequestFragment
import com.sterlingng.paylite.ui.payment.scheduled.ScheduledFragment
import com.sterlingng.paylite.ui.project.ProjectActivity
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

    fun inject(donateActivity: DonateActivity)

    fun inject(confirmActivity: ConfirmActivity)

    fun inject(projectActivity: ProjectActivity)

    fun inject(onBoardingActivity: SignUpActivity)

    fun inject(dashboardActivity: DashboardActivity)

    // Fragments

    fun inject(otpFragment: OtpFragment)

    fun inject(payFragment: PayFragment)

    fun inject(pinFragment: PinFragment)

    fun inject(giveFragment: GiveFragment)

    fun inject(nameFragment: NameFragment)

    fun inject(homeFragment: HomeFragment)

    fun inject(emailFragment: EmailFragment)

    fun inject(requestFragment: RequestFragment)

    fun inject(paymentFragment: PaymentFragment)

    fun inject(countryFragment: PasswordFragment)

    fun inject(completeFragment: CompleteFragment)

    fun inject(charitiesFragment: CharitiesFragment)

    fun inject(scheduledFragment: ScheduledFragment)

    fun inject(categoriesFragment: ProjectsFragment)

    fun inject(onBoardingFragment: OnBoardingFragment)

    // Dialogs

    fun inject(filterBottomSheetFragment: FilterBottomSheetFragment)

    fun inject(repeatBottomSheetFragment: RepeatBottomSheetFragment)
}