package com.sterlingng.paylite.di.component

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.di.module.ActivityModule
import com.sterlingng.paylite.ui.airtime.AirTimeFragment
import com.sterlingng.paylite.ui.bills.BillsActivity
import com.sterlingng.paylite.ui.cashoutbank.CashOutFragment
import com.sterlingng.paylite.ui.cashoutcode.CashOutCodeFragment
import com.sterlingng.paylite.ui.confirm.ConfirmFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import com.sterlingng.paylite.ui.fund.FundFragment
import com.sterlingng.paylite.ui.home.HomeFragment
import com.sterlingng.paylite.ui.login.LogInActivity
import com.sterlingng.paylite.ui.main.MainActivity
import com.sterlingng.paylite.ui.main.onboarding.OnBoardingFragment
import com.sterlingng.paylite.ui.newpayment.NewPaymentAmountFragment
import com.sterlingng.paylite.ui.newpayment.NewPaymentFragment
import com.sterlingng.paylite.ui.payment.PaymentFragment
import com.sterlingng.paylite.ui.profile.ProfileActivity
import com.sterlingng.paylite.ui.profile.edit.EditActivity
import com.sterlingng.paylite.ui.profile.notifications.NotificationActivity
import com.sterlingng.paylite.ui.request.RequestFragment
import com.sterlingng.paylite.ui.scheduled.ScheduledFragment
import com.sterlingng.paylite.ui.send.SendMoneyFragment
import com.sterlingng.paylite.ui.settings.SettingsFragment
import com.sterlingng.paylite.ui.signup.SignUpActivity
import com.sterlingng.paylite.ui.signup.complete.CompleteFragment
import com.sterlingng.paylite.ui.signup.email.EmailFragment
import com.sterlingng.paylite.ui.signup.name.NameFragment
import com.sterlingng.paylite.ui.signup.otp.OtpFragment
import com.sterlingng.paylite.ui.signup.password.PasswordFragment
import com.sterlingng.paylite.ui.signup.pin.PinFragment
import com.sterlingng.paylite.ui.successful.SuccessfulActivity
import com.sterlingng.paylite.ui.transactions.TransactionsFragment
import com.sterlingng.paylite.ui.transactions.categories.CategoriesFragment
import dagger.Component

/**
 * Created by rtukpe on 13/03/2018.
 */

@PerActivity
@Component(dependencies = [(ApplicationComponent::class)], modules = [(ActivityModule::class)])
interface ActivityComponent {

    // Activities

    fun inject(editActivity: EditActivity)

    fun inject(fundFragment: FundFragment)

    fun inject(mainActivity: MainActivity)

    fun inject(logInActivity: LogInActivity)

    fun inject(billsActivity: BillsActivity)

    fun inject(cashOutCodeFragment: CashOutCodeFragment)

    fun inject(confirmFragment: ConfirmFragment)

    fun inject(airTimeFragment: AirTimeFragment)

    fun inject(profileActivity: ProfileActivity)

    fun inject(onBoardingActivity: SignUpActivity)

    fun inject(dashboardActivity: DashboardActivity)

    fun inject(successfulActivity: SuccessfulActivity)

    fun inject(transactionsFragment: TransactionsFragment)

    fun inject(notificationActivity: NotificationActivity)

    // Fragments

    fun inject(otpFragment: OtpFragment)

    fun inject(pinFragment: PinFragment)

    fun inject(nameFragment: NameFragment)

    fun inject(homeFragment: HomeFragment)

    fun inject(emailFragment: EmailFragment)

    fun inject(requestFragment: RequestFragment)

    fun inject(paymentFragment: PaymentFragment)

    fun inject(countryFragment: PasswordFragment)

    fun inject(completeFragment: CompleteFragment)

    fun inject(scheduledFragment: ScheduledFragment)

    fun inject(categoriesFragment: CategoriesFragment)

    fun inject(onBoardingFragment: OnBoardingFragment)

    // Dialogs

    fun inject(filterBottomSheetFragment: FilterBottomSheetFragment)

    fun inject(settingsFragment: SettingsFragment)

    fun inject(cashOutFragment: CashOutFragment)

    fun inject(sendMoneyFragment: SendMoneyFragment)

    fun inject(newPaymentFragment: NewPaymentFragment)

    fun inject(newPaymentAmountFragment: NewPaymentAmountFragment)
}
