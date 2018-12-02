package com.sterlingng.paylite.di.component

import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.di.module.ActivityModule
import com.sterlingng.paylite.ui.airtime.AirTimeFragment
import com.sterlingng.paylite.ui.authpin.AuthPinFragment
import com.sterlingng.paylite.ui.banktransfers.BankTransferFragment
import com.sterlingng.paylite.ui.banktransfers.banktransferamount.BankTransferAmountFragment
import com.sterlingng.paylite.ui.banktransfers.contacts.ContactsFragment
import com.sterlingng.paylite.ui.banktransfers.newbanktransfer.NewBankTransferFragment
import com.sterlingng.paylite.ui.bills.BillsActivity
import com.sterlingng.paylite.ui.complete.CompleteFragment
import com.sterlingng.paylite.ui.confirm.ConfirmFragment
import com.sterlingng.paylite.ui.contacts.SelectContactsFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.editprofile.EditProfileFragment
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import com.sterlingng.paylite.ui.forgot.ForgotActivity
import com.sterlingng.paylite.ui.forgot.email.EmailForgotFragment
import com.sterlingng.paylite.ui.forgot.reset.ResetFragment
import com.sterlingng.paylite.ui.forgot.token.TokenFragment
import com.sterlingng.paylite.ui.fund.FundFragment
import com.sterlingng.paylite.ui.getcash.GetCashFragment
import com.sterlingng.paylite.ui.help.HelpFragment
import com.sterlingng.paylite.ui.home.HomeFragment
import com.sterlingng.paylite.ui.login.LogInActivity
import com.sterlingng.paylite.ui.main.MainActivity
import com.sterlingng.paylite.ui.main.onboarding.OnBoardingFragment
import com.sterlingng.paylite.ui.newpayment.NewPaymentFragment
import com.sterlingng.paylite.ui.newpaymentamount.NewPaymentAmountFragment
import com.sterlingng.paylite.ui.notifications.NotificationsFragment
import com.sterlingng.paylite.ui.payment.PaymentFragment
import com.sterlingng.paylite.ui.paystaff.PayStaffFragment
import com.sterlingng.paylite.ui.request.RequestFragment
import com.sterlingng.paylite.ui.request.custom.CustomRequestFragment
import com.sterlingng.paylite.ui.scheduled.ScheduledFragment
import com.sterlingng.paylite.ui.security.LoginAndSecurityFragment
import com.sterlingng.paylite.ui.securityquestions.SetSecurityQuestionFragment
import com.sterlingng.paylite.ui.send.SendMoneyFragment
import com.sterlingng.paylite.ui.services.ServicesFragment
import com.sterlingng.paylite.ui.settings.SettingsFragment
import com.sterlingng.paylite.ui.settings.profile.ProfileFragment
import com.sterlingng.paylite.ui.sheduledtransaction.ScheduledTransactionFragment
import com.sterlingng.paylite.ui.signup.SignUpActivity
import com.sterlingng.paylite.ui.signup.complete.CompleteSignUpFragment
import com.sterlingng.paylite.ui.signup.email.EmailFragment
import com.sterlingng.paylite.ui.signup.name.NameFragment
import com.sterlingng.paylite.ui.signup.otp.OtpFragment
import com.sterlingng.paylite.ui.signup.password.PasswordFragment
import com.sterlingng.paylite.ui.signup.phone.PhoneFragment
import com.sterlingng.paylite.ui.signup.pin.PinFragment
import com.sterlingng.paylite.ui.splitamount.SplitAmountFragment
import com.sterlingng.paylite.ui.splitcontacts.SplitContactFragment
import com.sterlingng.paylite.ui.transactions.TransactionsFragment
import com.sterlingng.paylite.ui.transactions.categories.TransactionCategoriesFragment
import com.sterlingng.paylite.ui.transactions.detail.TransactionDetailFragment
import com.sterlingng.paylite.ui.transactions.paymentcategory.PaymentCategoriesFragment
import dagger.Component

/**
 * Created by rtukpe on 13/03/2018.
 */

@PerActivity
@Component(dependencies = [(ApplicationComponent::class)], modules = [(ActivityModule::class)])
interface ActivityComponent {

    // Activities

    fun inject(editProfileFragment: EditProfileFragment)

    fun inject(fundFragment: FundFragment)

    fun inject(mainActivity: MainActivity)

    fun inject(logInActivity: LogInActivity)

    fun inject(billsActivity: BillsActivity)

    fun inject(confirmFragment: ConfirmFragment)

    fun inject(airTimeFragment: AirTimeFragment)

    fun inject(onBoardingActivity: SignUpActivity)

    fun inject(dashboardActivity: DashboardActivity)

    fun inject(transactionsFragment: TransactionsFragment)

    fun inject(notificationsFragment: NotificationsFragment)

    // Fragments

    fun inject(otpFragment: OtpFragment)

    fun inject(pinFragment: PinFragment)

    fun inject(nameFragment: NameFragment)

    fun inject(homeFragment: HomeFragment)

    fun inject(emailFragment: EmailFragment)

    fun inject(requestFragment: RequestFragment)

    fun inject(paymentFragment: PaymentFragment)

    fun inject(countryFragment: PasswordFragment)

    fun inject(completeSignUpFragment: CompleteSignUpFragment)

    fun inject(scheduledFragment: ScheduledFragment)

    fun inject(transactionCategoriesFragment: TransactionCategoriesFragment)

    fun inject(onBoardingFragment: OnBoardingFragment)

    // Dialogs

    fun inject(tokenFragment: TokenFragment)

    fun inject(resetFragment: ResetFragment)

    fun inject(phoneFragment: PhoneFragment)

    fun inject(forgotActivity: ForgotActivity)

    fun inject(getCashFragment: GetCashFragment)

    fun inject(settingsFragment: SettingsFragment)

    fun inject(sendMoneyFragment: SendMoneyFragment)

    fun inject(newPaymentFragment: NewPaymentFragment)

    fun inject(emailForgotFragment: EmailForgotFragment)

    fun inject(splitAmountFragment: SplitAmountFragment)

    fun inject(splitContactFragment: SplitContactFragment)

    fun inject(customRequestFragment: CustomRequestFragment)

    fun inject(newPaymentAmountFragment: NewPaymentAmountFragment)

    fun inject(filterBottomSheetFragment: FilterBottomSheetFragment)

    fun inject(selectContactsFragment: SelectContactsFragment)

    fun inject(bankTransferFragment: BankTransferFragment)

    fun inject(authPinFragment: AuthPinFragment)

    fun inject(loginAndSecurityFragment: LoginAndSecurityFragment)

    fun inject(newBankTransferFragment: NewBankTransferFragment)

    fun inject(contactsFragment: ContactsFragment)

    fun inject(helpFragment: HelpFragment)

    fun inject(bankTransferAmountFragment: BankTransferAmountFragment)

    fun inject(scheduledTransactionFragment: ScheduledTransactionFragment)

    fun inject(transactionDetailFragment: TransactionDetailFragment)

    fun inject(paymentCategoriesFragment: PaymentCategoriesFragment)

    fun inject(servicesFragment: ServicesFragment)

    fun inject(completeFragment: CompleteFragment)

    fun inject(setSecurityQuestionFragment: SetSecurityQuestionFragment)

    fun inject(profileFragment: ProfileFragment)

    fun inject(payStaffFragment: PayStaffFragment)

}
