package com.sterlingng.paylite.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SnapHelper
import android.view.Gravity
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.sterlingng.paylite.di.annotations.ActivityContext
import com.sterlingng.paylite.di.annotations.PerActivity
import com.sterlingng.paylite.rx.AppSchedulerProvider
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.airtime.AirTimeMvpContract
import com.sterlingng.paylite.ui.airtime.AirTimeMvpView
import com.sterlingng.paylite.ui.airtime.AirTimePresenter
import com.sterlingng.paylite.ui.authpin.AuthPinMvpContract
import com.sterlingng.paylite.ui.authpin.AuthPinMvpView
import com.sterlingng.paylite.ui.authpin.AuthPinPresenter
import com.sterlingng.paylite.ui.banktransfers.BankTransferMvpContract
import com.sterlingng.paylite.ui.banktransfers.BankTransferMvpView
import com.sterlingng.paylite.ui.banktransfers.BankTransferPresenter
import com.sterlingng.paylite.ui.banktransfers.banktransferamount.BankTransferAmountMvpContract
import com.sterlingng.paylite.ui.banktransfers.banktransferamount.BankTransferAmountMvpView
import com.sterlingng.paylite.ui.banktransfers.banktransferamount.BankTransferAmountPresenter
import com.sterlingng.paylite.ui.banktransfers.contacts.ContactsMvpContract
import com.sterlingng.paylite.ui.banktransfers.contacts.ContactsMvpView
import com.sterlingng.paylite.ui.banktransfers.contacts.ContactsPresenter
import com.sterlingng.paylite.ui.banktransfers.newbanktransfer.NewBankTransferMvpContract
import com.sterlingng.paylite.ui.banktransfers.newbanktransfer.NewBankTransferMvpView
import com.sterlingng.paylite.ui.banktransfers.newbanktransfer.NewBankTransferPresenter
import com.sterlingng.paylite.ui.bills.BillsMvpContract
import com.sterlingng.paylite.ui.bills.BillsMvpView
import com.sterlingng.paylite.ui.bills.BillsPresenter
import com.sterlingng.paylite.ui.complete.CompleteMvpContract
import com.sterlingng.paylite.ui.complete.CompleteMvpView
import com.sterlingng.paylite.ui.complete.CompletePresenter
import com.sterlingng.paylite.ui.confirm.ConfirmMvpContract
import com.sterlingng.paylite.ui.confirm.ConfirmMvpView
import com.sterlingng.paylite.ui.confirm.ConfirmPresenter
import com.sterlingng.paylite.ui.contacts.SelectContactPresenter
import com.sterlingng.paylite.ui.contacts.SelectContactsMvpContract
import com.sterlingng.paylite.ui.contacts.SelectContactsMvpView
import com.sterlingng.paylite.ui.dashboard.DashboardMvpContract
import com.sterlingng.paylite.ui.dashboard.DashboardMvpView
import com.sterlingng.paylite.ui.dashboard.DashboardPresenter
import com.sterlingng.paylite.ui.editprofile.EditProfileMvpContract
import com.sterlingng.paylite.ui.editprofile.EditProfileMvpView
import com.sterlingng.paylite.ui.editprofile.EditProfilePresenter
import com.sterlingng.paylite.ui.filter.FilterMvpContract
import com.sterlingng.paylite.ui.filter.FilterMvpView
import com.sterlingng.paylite.ui.filter.FilterPresenter
import com.sterlingng.paylite.ui.forgot.ForgotMvpContract
import com.sterlingng.paylite.ui.forgot.ForgotMvpView
import com.sterlingng.paylite.ui.forgot.ForgotPresenter
import com.sterlingng.paylite.ui.forgot.email.ForgotPhoneMvpContract
import com.sterlingng.paylite.ui.forgot.email.ForgotPhoneMvpView
import com.sterlingng.paylite.ui.forgot.email.ForgotPhonePresenter
import com.sterlingng.paylite.ui.forgot.reset.ResetPasswordMvpContract
import com.sterlingng.paylite.ui.forgot.reset.ResetPasswordMvpView
import com.sterlingng.paylite.ui.forgot.reset.ResetPasswordPresenter
import com.sterlingng.paylite.ui.forgot.token.TokenMvpContract
import com.sterlingng.paylite.ui.forgot.token.TokenMvpView
import com.sterlingng.paylite.ui.forgot.token.TokenPresenter
import com.sterlingng.paylite.ui.fund.FundMvpContract
import com.sterlingng.paylite.ui.fund.FundMvpView
import com.sterlingng.paylite.ui.fund.FundPresenter
import com.sterlingng.paylite.ui.getcash.GetCashMvpContract
import com.sterlingng.paylite.ui.getcash.GetCashMvpView
import com.sterlingng.paylite.ui.getcash.GetCashPresenter
import com.sterlingng.paylite.ui.help.HelpMvpContract
import com.sterlingng.paylite.ui.help.HelpMvpView
import com.sterlingng.paylite.ui.help.HelpPresenter
import com.sterlingng.paylite.ui.home.HomeMvpContract
import com.sterlingng.paylite.ui.home.HomeMvpView
import com.sterlingng.paylite.ui.home.HomePresenter
import com.sterlingng.paylite.ui.login.LogInMvpContract
import com.sterlingng.paylite.ui.login.LogInMvpView
import com.sterlingng.paylite.ui.login.LogInPresenter
import com.sterlingng.paylite.ui.main.MainMvpContract
import com.sterlingng.paylite.ui.main.MainMvpView
import com.sterlingng.paylite.ui.main.MainPresenter
import com.sterlingng.paylite.ui.main.onboarding.OnBoardingMvpContract
import com.sterlingng.paylite.ui.main.onboarding.OnBoardingMvpView
import com.sterlingng.paylite.ui.main.onboarding.OnBoardingPresenter
import com.sterlingng.paylite.ui.newpayment.NewPaymentMvpContract
import com.sterlingng.paylite.ui.newpayment.NewPaymentMvpView
import com.sterlingng.paylite.ui.newpayment.NewPaymentPresenter
import com.sterlingng.paylite.ui.newpaymentamount.NewPaymentAmountMvpContract
import com.sterlingng.paylite.ui.newpaymentamount.NewPaymentAmountMvpView
import com.sterlingng.paylite.ui.newpaymentamount.NewPaymentAmountPresenter
import com.sterlingng.paylite.ui.notifications.NotificationMvpContract
import com.sterlingng.paylite.ui.notifications.NotificationMvpView
import com.sterlingng.paylite.ui.notifications.NotificationPresenter
import com.sterlingng.paylite.ui.notifications.NotificationsAdapter
import com.sterlingng.paylite.ui.payment.PaymentMvpContract
import com.sterlingng.paylite.ui.payment.PaymentMvpView
import com.sterlingng.paylite.ui.payment.PaymentPresenter
import com.sterlingng.paylite.ui.paystaff.PayStaffMvpContract
import com.sterlingng.paylite.ui.paystaff.PayStaffMvpView
import com.sterlingng.paylite.ui.paystaff.PayStaffPresenter
import com.sterlingng.paylite.ui.paystaff.addstaff.AddStaffMvpContract
import com.sterlingng.paylite.ui.paystaff.addstaff.AddStaffMvpView
import com.sterlingng.paylite.ui.paystaff.addstaff.AddStaffPresenter
import com.sterlingng.paylite.ui.paystaff.salarydetails.SalaryDetailsMvpContract
import com.sterlingng.paylite.ui.paystaff.salarydetails.SalaryDetailsMvpView
import com.sterlingng.paylite.ui.paystaff.salarydetails.SalaryDetailsPresenter
import com.sterlingng.paylite.ui.request.RequestMvpContract
import com.sterlingng.paylite.ui.request.RequestMvpView
import com.sterlingng.paylite.ui.request.RequestPresenter
import com.sterlingng.paylite.ui.request.custom.CustomRequestMvpContract
import com.sterlingng.paylite.ui.request.custom.CustomRequestMvpView
import com.sterlingng.paylite.ui.request.custom.CustomRequestPresenter
import com.sterlingng.paylite.ui.scheduled.ScheduledMvpContract
import com.sterlingng.paylite.ui.scheduled.ScheduledMvpView
import com.sterlingng.paylite.ui.scheduled.ScheduledPaymentAdapter
import com.sterlingng.paylite.ui.scheduled.ScheduledPresenter
import com.sterlingng.paylite.ui.security.LoginAndSecurityMvpContract
import com.sterlingng.paylite.ui.security.LoginAndSecurityMvpView
import com.sterlingng.paylite.ui.security.LoginAndSecurityPresenter
import com.sterlingng.paylite.ui.securityquestions.SetSecurityQuestionMvpContract
import com.sterlingng.paylite.ui.securityquestions.SetSecurityQuestionMvpPresenter
import com.sterlingng.paylite.ui.securityquestions.SetSecurityQuestionMvpView
import com.sterlingng.paylite.ui.send.SendMoneyMvpContract
import com.sterlingng.paylite.ui.send.SendMoneyMvpView
import com.sterlingng.paylite.ui.send.SendMoneyPresenter
import com.sterlingng.paylite.ui.services.ServicesMvpContract
import com.sterlingng.paylite.ui.services.ServicesMvpView
import com.sterlingng.paylite.ui.services.ServicesPresenter
import com.sterlingng.paylite.ui.services.airtel.AirtelServiceMvpContract
import com.sterlingng.paylite.ui.services.airtel.AirtelServiceMvpView
import com.sterlingng.paylite.ui.services.airtel.AirtelServicePresenter
import com.sterlingng.paylite.ui.services.dstv.DstvServiceMvpContract
import com.sterlingng.paylite.ui.services.dstv.DstvServiceMvpView
import com.sterlingng.paylite.ui.services.dstv.DstvServicePresenter
import com.sterlingng.paylite.ui.services.etisalat.EtisalatServiceMvpContract
import com.sterlingng.paylite.ui.services.etisalat.EtisalatServiceMvpView
import com.sterlingng.paylite.ui.services.etisalat.EtisalatServicePresenter
import com.sterlingng.paylite.ui.services.glo.GloServiceMvpContract
import com.sterlingng.paylite.ui.services.glo.GloServiceMvpView
import com.sterlingng.paylite.ui.services.glo.GloServicePresenter
import com.sterlingng.paylite.ui.services.mtn.MtnServiceMvpContract
import com.sterlingng.paylite.ui.services.mtn.MtnServiceMvpView
import com.sterlingng.paylite.ui.services.mtn.MtnServicePresenter
import com.sterlingng.paylite.ui.settings.SettingsMvpContract
import com.sterlingng.paylite.ui.settings.SettingsMvpView
import com.sterlingng.paylite.ui.settings.SettingsPresenter
import com.sterlingng.paylite.ui.settings.profile.ProfileMvpContract
import com.sterlingng.paylite.ui.settings.profile.ProfileMvpView
import com.sterlingng.paylite.ui.settings.profile.ProfilePresenter
import com.sterlingng.paylite.ui.sheduledtransaction.ScheduledTransactionMvpContract
import com.sterlingng.paylite.ui.sheduledtransaction.ScheduledTransactionMvpView
import com.sterlingng.paylite.ui.sheduledtransaction.ScheduledTransactionPresenter
import com.sterlingng.paylite.ui.signup.SignUpMvpContract
import com.sterlingng.paylite.ui.signup.SignUpMvpView
import com.sterlingng.paylite.ui.signup.SignUpPresenter
import com.sterlingng.paylite.ui.signup.complete.CompleteSignUpMvpContract
import com.sterlingng.paylite.ui.signup.complete.CompleteSignUpMvpView
import com.sterlingng.paylite.ui.signup.complete.CompleteSignUpPresenter
import com.sterlingng.paylite.ui.signup.email.EmailMvpContract
import com.sterlingng.paylite.ui.signup.email.EmailMvpView
import com.sterlingng.paylite.ui.signup.email.EmailPresenter
import com.sterlingng.paylite.ui.signup.name.NameMvpContract
import com.sterlingng.paylite.ui.signup.name.NameMvpView
import com.sterlingng.paylite.ui.signup.name.NamePresenter
import com.sterlingng.paylite.ui.signup.otp.OtpMvpContract
import com.sterlingng.paylite.ui.signup.otp.OtpMvpView
import com.sterlingng.paylite.ui.signup.otp.OtpPresenter
import com.sterlingng.paylite.ui.signup.phone.PhoneMvpContract
import com.sterlingng.paylite.ui.signup.phone.PhoneMvpView
import com.sterlingng.paylite.ui.signup.phone.PhonePresenter
import com.sterlingng.paylite.ui.signup.pin.PinMvpContract
import com.sterlingng.paylite.ui.signup.pin.PinMvpView
import com.sterlingng.paylite.ui.signup.pin.PinPresenter
import com.sterlingng.paylite.ui.splitamount.SplitAmountMvpContract
import com.sterlingng.paylite.ui.splitamount.SplitAmountMvpView
import com.sterlingng.paylite.ui.splitamount.SplitAmountPresenter
import com.sterlingng.paylite.ui.splitcontacts.SplitContactMvpContract
import com.sterlingng.paylite.ui.splitcontacts.SplitContactMvpView
import com.sterlingng.paylite.ui.splitcontacts.SplitContactPresenter
import com.sterlingng.paylite.ui.transactions.TransactionsMvpContract
import com.sterlingng.paylite.ui.transactions.TransactionsMvpView
import com.sterlingng.paylite.ui.transactions.TransactionsPresenter
import com.sterlingng.paylite.ui.transactions.categories.TransactionCategoriesAdapter
import com.sterlingng.paylite.ui.transactions.categories.TransactionCategoriesMvpContract
import com.sterlingng.paylite.ui.transactions.categories.TransactionCategoriesMvpView
import com.sterlingng.paylite.ui.transactions.categories.TransactionCategoriesPresenter
import com.sterlingng.paylite.ui.transactions.detail.TransactionDetailMvpContract
import com.sterlingng.paylite.ui.transactions.detail.TransactionDetailMvpView
import com.sterlingng.paylite.ui.transactions.detail.TransactionDetailPresenter
import com.sterlingng.paylite.ui.transactions.insights.InsightsMvpContract
import com.sterlingng.paylite.ui.transactions.insights.InsightsMvpView
import com.sterlingng.paylite.ui.transactions.insights.InsightsPresenter
import com.sterlingng.paylite.ui.transactions.paymentcategory.PaymentCategoriesAdapter
import com.sterlingng.paylite.ui.transactions.paymentcategory.PaymentCategoriesMvpContract
import com.sterlingng.paylite.ui.transactions.paymentcategory.PaymentCategoriesMvpView
import com.sterlingng.paylite.ui.transactions.paymentcategory.PaymentCategoriesPresenter
import com.sterlingng.paylite.utils.CustomPagerAdapter
import com.sterlingng.views.NoScrollingGridLayoutManager
import com.sterlingng.views.NoScrollingLinearLayoutManager
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by rtukpe on 13/03/2018.
 */

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @ActivityContext
    internal fun provideContext(): Context = activity

    @Provides
    internal fun provideActivity(): AppCompatActivity = activity

    // Provide Utility Contexts

    @Provides
    internal fun provideSnapHelper(): SnapHelper = GravitySnapHelper(Gravity.START)

    @Provides
    internal fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Provides
    internal fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    internal fun provideLinearLayoutManager(activity: AppCompatActivity): LinearLayoutManager = LinearLayoutManager(activity)

    @Provides
    internal fun provideNotificationsAdapter(activity: AppCompatActivity): NotificationsAdapter = NotificationsAdapter(activity)

    @Provides
    internal fun provideGridLayoutManager(activity: AppCompatActivity): GridLayoutManager = GridLayoutManager(activity, 2)

    @Provides
    internal fun provideScheduledPaymentAdapter(activity: AppCompatActivity): ScheduledPaymentAdapter = ScheduledPaymentAdapter(activity)

    @Provides
    internal fun providePaymentCategoriesAdapter(activity: AppCompatActivity): PaymentCategoriesAdapter = PaymentCategoriesAdapter(activity)

    @Provides
    internal fun provideTransactionAdapter(activity: AppCompatActivity): TransactionCategoriesAdapter = TransactionCategoriesAdapter(activity)

    @Provides
    internal fun provideCustomPagerAdapter(activity: AppCompatActivity): CustomPagerAdapter = CustomPagerAdapter(activity.supportFragmentManager)

    @Provides
    internal fun provideNoScrollingLinearLayoutManager(activity: AppCompatActivity): NoScrollingLinearLayoutManager = NoScrollingLinearLayoutManager(activity)

    @Provides
    internal fun provideDividerItemDecoration(activity: AppCompatActivity): DividerItemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)

    @Provides
    internal fun provideNoScrollingGridLayoutManager(activity: AppCompatActivity): NoScrollingGridLayoutManager = NoScrollingGridLayoutManager(activity, 3)

    //Provide Activity Contexts

    @Provides
    @PerActivity
    internal fun provideMainPresenter(presenter: MainPresenter<MainMvpView>): MainMvpContract<MainMvpView> = presenter

    @Provides
    internal fun provideFundPresenter(presenter: FundPresenter<FundMvpView>): FundMvpContract<FundMvpView> = presenter

    @Provides
    internal fun provideBillsPresenter(presenter: BillsPresenter<BillsMvpView>): BillsMvpContract<BillsMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideLogInPresenter(presenter: LogInPresenter<LogInMvpView>): LogInMvpContract<LogInMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideForgotPresenter(presenter: ForgotPresenter<ForgotMvpView>): ForgotMvpContract<ForgotMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideSignUpPresenter(presenter: SignUpPresenter<SignUpMvpView>): SignUpMvpContract<SignUpMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideDashboardPresenter(presenter: DashboardPresenter<DashboardMvpView>): DashboardMvpContract<DashboardMvpView> = presenter

    //Provide Fragment Contexts

    @Provides
    internal fun provideOtpPresenter(presenter: OtpPresenter<OtpMvpView>): OtpMvpContract<OtpMvpView> = presenter

    @Provides
    internal fun providePinPresenter(presenter: PinPresenter<PinMvpView>): PinMvpContract<PinMvpView> = presenter

    @Provides
    internal fun provideHelpPresenter(presenter: HelpPresenter<HelpMvpView>): HelpMvpContract<HelpMvpView> = presenter

    @Provides
    internal fun provideHomePresenter(presenter: HomePresenter<HomeMvpView>): HomeMvpContract<HomeMvpView> = presenter

    @Provides
    internal fun provideNamePresenter(presenter: NamePresenter<NameMvpView>): NameMvpContract<NameMvpView> = presenter

    @Provides
    internal fun provideTokenPresenter(presenter: TokenPresenter<TokenMvpView>): TokenMvpContract<TokenMvpView> = presenter

    @Provides
    internal fun provideEmailPresenter(presenter: EmailPresenter<EmailMvpView>): EmailMvpContract<EmailMvpView> = presenter

    @Provides
    internal fun providePhonePresenter(presenter: PhonePresenter<PhoneMvpView>): PhoneMvpContract<PhoneMvpView> = presenter

    @Provides
    internal fun provideFilterPresenter(presenter: FilterPresenter<FilterMvpView>): FilterMvpContract<FilterMvpView> = presenter

    @Provides
    internal fun provideProfilePresenter(presenter: ProfilePresenter<ProfileMvpView>): ProfileMvpContract<ProfileMvpView> = presenter

    @Provides
    internal fun provideAuthPinPresenter(presenter: AuthPinPresenter<AuthPinMvpView>): AuthPinMvpContract<AuthPinMvpView> = presenter

    @Provides
    internal fun provideConfirmPresenter(presenter: ConfirmPresenter<ConfirmMvpView>): ConfirmMvpContract<ConfirmMvpView> = presenter

    @Provides
    internal fun providePaymentPresenter(presenter: PaymentPresenter<PaymentMvpView>): PaymentMvpContract<PaymentMvpView> = presenter

    @Provides
    internal fun provideAirTimePresenter(presenter: AirTimePresenter<AirTimeMvpView>): AirTimeMvpContract<AirTimeMvpView> = presenter

    @Provides
    internal fun provideRequestPresenter(presenter: RequestPresenter<RequestMvpView>): RequestMvpContract<RequestMvpView> = presenter

    @Provides
    internal fun provideGetCashPresenter(presenter: GetCashPresenter<GetCashMvpView>): GetCashMvpContract<GetCashMvpView> = presenter

    @Provides
    internal fun provideCompletePresenter(presenter: CompletePresenter<CompleteMvpView>): CompleteMvpContract<CompleteMvpView> = presenter

    @Provides
    internal fun provideServicesPresenter(presenter: ServicesPresenter<ServicesMvpView>): ServicesMvpContract<ServicesMvpView> = presenter

    @Provides
    internal fun provideContactsPresenter(presenter: ContactsPresenter<ContactsMvpView>): ContactsMvpContract<ContactsMvpView> = presenter

    @Provides
    internal fun provideInsightsPresenter(presenter: InsightsPresenter<InsightsMvpView>): InsightsMvpContract<InsightsMvpView> = presenter

    @Provides
    internal fun providePayStaffPresenter(presenter: PayStaffPresenter<PayStaffMvpView>): PayStaffMvpContract<PayStaffMvpView> = presenter

    @Provides
    internal fun provideAddStaffPresenter(presenter: AddStaffPresenter<AddStaffMvpView>): AddStaffMvpContract<AddStaffMvpView> = presenter

    @Provides
    internal fun provideSettingsPresenter(presenter: SettingsPresenter<SettingsMvpView>): SettingsMvpContract<SettingsMvpView> = presenter

    @Provides
    internal fun provideScheduledPresenter(presenter: ScheduledPresenter<ScheduledMvpView>): ScheduledMvpContract<ScheduledMvpView> = presenter

    @Provides
    internal fun provideSendMoneyPresenter(presenter: SendMoneyPresenter<SendMoneyMvpView>): SendMoneyMvpContract<SendMoneyMvpView> = presenter

    @Provides
    internal fun provideOnBoardingPresenter(presenter: OnBoardingPresenter<OnBoardingMvpView>): OnBoardingMvpContract<OnBoardingMvpView> = presenter

    @Provides
    internal fun provideMtnServicePresenter(presenter: MtnServicePresenter<MtnServiceMvpView>): MtnServiceMvpContract<MtnServiceMvpView> = presenter

    @Provides
    internal fun provideGloServicePresenter(presenter: GloServicePresenter<GloServiceMvpView>): GloServiceMvpContract<GloServiceMvpView> = presenter

    @Provides
    internal fun provideNewPaymentPresenter(presenter: NewPaymentPresenter<NewPaymentMvpView>): NewPaymentMvpContract<NewPaymentMvpView> = presenter

    @Provides
    internal fun provideSplitAmountPresenter(presenter: SplitAmountPresenter<SplitAmountMvpView>): SplitAmountMvpContract<SplitAmountMvpView> = presenter

    @Provides
    internal fun provideDstvServicePresenter(presenter: DstvServicePresenter<DstvServiceMvpView>): DstvServiceMvpContract<DstvServiceMvpView> = presenter

    @Provides
    internal fun provideEmailForgotPresenter(presenter: ForgotPhonePresenter<ForgotPhoneMvpView>): ForgotPhoneMvpContract<ForgotPhoneMvpView> = presenter

    @Provides
    internal fun provideSplitContactPresenter(presenter: SplitContactPresenter<SplitContactMvpView>): SplitContactMvpContract<SplitContactMvpView> = presenter

    @Provides
    internal fun provideBankTransferPresenter(presenter: BankTransferPresenter<BankTransferMvpView>): BankTransferMvpContract<BankTransferMvpView> = presenter

    @Provides
    internal fun provideNotificationPresenter(presenter: NotificationPresenter<NotificationMvpView>): NotificationMvpContract<NotificationMvpView> = presenter

    @Provides
    internal fun provideTransactionsPresenter(presenter: TransactionsPresenter<TransactionsMvpView>): TransactionsMvpContract<TransactionsMvpView> = presenter

    @Provides
    internal fun provideEditProfilePresenter(presenter: EditProfilePresenter<EditProfileMvpView>): EditProfileMvpContract<EditProfileMvpView> = presenter

    @Provides
    internal fun provideAirtelServicePresenter(presenter: AirtelServicePresenter<AirtelServiceMvpView>): AirtelServiceMvpContract<AirtelServiceMvpView> = presenter

    @Provides
    internal fun provideResetPasswordPresenter(presenter: ResetPasswordPresenter<ResetPasswordMvpView>): ResetPasswordMvpContract<ResetPasswordMvpView> = presenter

    @Provides
    internal fun provideSalaryDetailsPresenter(presenter: SalaryDetailsPresenter<SalaryDetailsMvpView>): SalaryDetailsMvpContract<SalaryDetailsMvpView> = presenter

    @Provides
    internal fun provideCustomRequestPresenter(presenter: CustomRequestPresenter<CustomRequestMvpView>): CustomRequestMvpContract<CustomRequestMvpView> = presenter

    @Provides
    internal fun provideSelectContactPresenter(presenter: SelectContactPresenter<SelectContactsMvpView>): SelectContactsMvpContract<SelectContactsMvpView> = presenter

    @Provides
    internal fun provideCompleteSignUpPresenter(presenter: CompleteSignUpPresenter<CompleteSignUpMvpView>): CompleteSignUpMvpContract<CompleteSignUpMvpView> = presenter

    @Provides
    internal fun provideNewBankTransferPresenter(presenter: NewBankTransferPresenter<NewBankTransferMvpView>): NewBankTransferMvpContract<NewBankTransferMvpView> = presenter

    @Provides
    internal fun provideEtisalatServicePresenter(presenter: EtisalatServicePresenter<EtisalatServiceMvpView>): EtisalatServiceMvpContract<EtisalatServiceMvpView> = presenter

    @Provides
    internal fun provideLoginAndSecurityPresenter(presenter: LoginAndSecurityPresenter<LoginAndSecurityMvpView>): LoginAndSecurityMvpContract<LoginAndSecurityMvpView> = presenter

    @Provides
    internal fun provideNewPaymentAmountPresenter(presenter: NewPaymentAmountPresenter<NewPaymentAmountMvpView>): NewPaymentAmountMvpContract<NewPaymentAmountMvpView> = presenter

    @Provides
    internal fun provideTransactionDetailPresenter(presenter: TransactionDetailPresenter<TransactionDetailMvpView>): TransactionDetailMvpContract<TransactionDetailMvpView> = presenter

    @Provides
    internal fun providePaymentCategoriesPresenter(presenter: PaymentCategoriesPresenter<PaymentCategoriesMvpView>): PaymentCategoriesMvpContract<PaymentCategoriesMvpView> = presenter

    @Provides
    internal fun provideBankTransferAmountPresenter(presenter: BankTransferAmountPresenter<BankTransferAmountMvpView>): BankTransferAmountMvpContract<BankTransferAmountMvpView> = presenter

    @Provides
    internal fun provideSetSecurityQuestionPresenter(presenter: SetSecurityQuestionMvpPresenter<SetSecurityQuestionMvpView>): SetSecurityQuestionMvpContract<SetSecurityQuestionMvpView> = presenter

    @Provides
    internal fun provideScheduledTransactionPresenter(presenter: ScheduledTransactionPresenter<ScheduledTransactionMvpView>): ScheduledTransactionMvpContract<ScheduledTransactionMvpView> = presenter

    @Provides
    internal fun provideTransactionCategoriesPresenter(presenter: TransactionCategoriesPresenter<TransactionCategoriesMvpView>): TransactionCategoriesMvpContract<TransactionCategoriesMvpView> = presenter
}
