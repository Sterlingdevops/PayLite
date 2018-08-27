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
import com.sterlingng.paylite.ui.bills.BillsMvpContract
import com.sterlingng.paylite.ui.bills.BillsMvpView
import com.sterlingng.paylite.ui.bills.BillsPresenter
import com.sterlingng.paylite.ui.cashoutbank.CashOutBankMvpContract
import com.sterlingng.paylite.ui.cashoutbank.CashOutBankMvpView
import com.sterlingng.paylite.ui.cashoutbank.CashOutBankPresenter
import com.sterlingng.paylite.ui.cashoutcode.DonateMvpContract
import com.sterlingng.paylite.ui.cashoutcode.DonateMvpView
import com.sterlingng.paylite.ui.cashoutcode.DonatePresenter
import com.sterlingng.paylite.ui.confirm.ConfirmMvpContract
import com.sterlingng.paylite.ui.confirm.ConfirmMvpView
import com.sterlingng.paylite.ui.confirm.ConfirmPresenter
import com.sterlingng.paylite.ui.dashboard.DashboardMvpContract
import com.sterlingng.paylite.ui.dashboard.DashboardMvpView
import com.sterlingng.paylite.ui.dashboard.DashboardPresenter
import com.sterlingng.paylite.ui.filter.FilterMvpContract
import com.sterlingng.paylite.ui.filter.FilterMvpView
import com.sterlingng.paylite.ui.filter.FilterPresenter
import com.sterlingng.paylite.ui.fund.FundMvpContract
import com.sterlingng.paylite.ui.fund.FundMvpView
import com.sterlingng.paylite.ui.fund.FundPresenter
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
import com.sterlingng.paylite.ui.newpayment.*
import com.sterlingng.paylite.ui.payment.PaymentMvpContract
import com.sterlingng.paylite.ui.payment.PaymentMvpView
import com.sterlingng.paylite.ui.payment.PaymentPresenter
import com.sterlingng.paylite.ui.profile.ProfileMvpContract
import com.sterlingng.paylite.ui.profile.ProfileMvpView
import com.sterlingng.paylite.ui.profile.ProfilePresenter
import com.sterlingng.paylite.ui.profile.edit.EditMvpContract
import com.sterlingng.paylite.ui.profile.edit.EditMvpView
import com.sterlingng.paylite.ui.profile.edit.EditPresenter
import com.sterlingng.paylite.ui.profile.notifications.NotificationMvpContract
import com.sterlingng.paylite.ui.profile.notifications.NotificationMvpView
import com.sterlingng.paylite.ui.profile.notifications.NotificationPresenter
import com.sterlingng.paylite.ui.profile.notifications.NotificationsAdapter
import com.sterlingng.paylite.ui.request.RequestMvpContract
import com.sterlingng.paylite.ui.request.RequestMvpView
import com.sterlingng.paylite.ui.request.RequestPresenter
import com.sterlingng.paylite.ui.scheduled.ScheduledMvpContract
import com.sterlingng.paylite.ui.scheduled.ScheduledMvpView
import com.sterlingng.paylite.ui.scheduled.ScheduledPresenter
import com.sterlingng.paylite.ui.send.SendMoneyMvpContract
import com.sterlingng.paylite.ui.send.SendMoneyMvpView
import com.sterlingng.paylite.ui.send.SendMoneyPresenter
import com.sterlingng.paylite.ui.settings.SettingsMvpContract
import com.sterlingng.paylite.ui.settings.SettingsMvpView
import com.sterlingng.paylite.ui.settings.SettingsPresenter
import com.sterlingng.paylite.ui.signup.SignUpMvpContract
import com.sterlingng.paylite.ui.signup.SignUpMvpView
import com.sterlingng.paylite.ui.signup.SignUpPresenter
import com.sterlingng.paylite.ui.signup.complete.CompleteMvpContract
import com.sterlingng.paylite.ui.signup.complete.CompleteMvpView
import com.sterlingng.paylite.ui.signup.complete.CompletePresenter
import com.sterlingng.paylite.ui.signup.email.EmailMvpContract
import com.sterlingng.paylite.ui.signup.email.EmailMvpView
import com.sterlingng.paylite.ui.signup.email.EmailPresenter
import com.sterlingng.paylite.ui.signup.name.NameMvpContract
import com.sterlingng.paylite.ui.signup.name.NameMvpView
import com.sterlingng.paylite.ui.signup.name.NamePresenter
import com.sterlingng.paylite.ui.signup.otp.OtpMvpContract
import com.sterlingng.paylite.ui.signup.otp.OtpMvpView
import com.sterlingng.paylite.ui.signup.otp.OtpPresenter
import com.sterlingng.paylite.ui.signup.password.PasswordMvpContract
import com.sterlingng.paylite.ui.signup.password.PasswordMvpView
import com.sterlingng.paylite.ui.signup.password.PasswordPresenter
import com.sterlingng.paylite.ui.signup.pin.PinMvpContract
import com.sterlingng.paylite.ui.signup.pin.PinMvpView
import com.sterlingng.paylite.ui.signup.pin.PinPresenter
import com.sterlingng.paylite.ui.successful.CategoriesAdapter
import com.sterlingng.paylite.ui.successful.SuccessfulMvpContract
import com.sterlingng.paylite.ui.successful.SuccessfulMvpView
import com.sterlingng.paylite.ui.successful.SuccessfulPresenter
import com.sterlingng.paylite.ui.transactions.TransactionsMvpContract
import com.sterlingng.paylite.ui.transactions.TransactionsMvpView
import com.sterlingng.paylite.ui.transactions.TransactionsPresenter
import com.sterlingng.paylite.ui.transactions.categories.CategoriesMvpContract
import com.sterlingng.paylite.ui.transactions.categories.CategoriesMvpView
import com.sterlingng.paylite.ui.transactions.categories.CategoriesPresenter
import com.sterlingng.paylite.ui.transactions.categories.TransactionAdapter
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
    internal fun provideCategoriesAdapter(activity: AppCompatActivity): CategoriesAdapter = CategoriesAdapter(activity)

    @Provides
    internal fun provideTransactionAdapter(activity: AppCompatActivity): TransactionAdapter = TransactionAdapter(activity)

    @Provides
    internal fun provideLinearLayoutManager(activity: AppCompatActivity): LinearLayoutManager = LinearLayoutManager(activity)

    @Provides
    internal fun provideNotificationsAdapter(activity: AppCompatActivity): NotificationsAdapter = NotificationsAdapter(activity)

    @Provides
    internal fun provideGridLayoutManager(activity: AppCompatActivity): GridLayoutManager = GridLayoutManager(activity, 2)

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
    internal fun provideEditPresenter(presenter: EditPresenter<EditMvpView>): EditMvpContract<EditMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideMainPresenter(presenter: MainPresenter<MainMvpView>): MainMvpContract<MainMvpView> = presenter

    @Provides
    internal fun provideFundPresenter(presenter: FundPresenter<FundMvpView>): FundMvpContract<FundMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideBillsPresenter(presenter: BillsPresenter<BillsMvpView>): BillsMvpContract<BillsMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideLogInPresenter(presenter: LogInPresenter<LogInMvpView>): LogInMvpContract<LogInMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideDonatePresenter(presenter: DonatePresenter<DonateMvpView>): DonateMvpContract<DonateMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideSignUpPresenter(presenter: SignUpPresenter<SignUpMvpView>): SignUpMvpContract<SignUpMvpView> = presenter

    @Provides
    internal fun provideConfirmPresenter(presenter: ConfirmPresenter<ConfirmMvpView>): ConfirmMvpContract<ConfirmMvpView> = presenter

    @Provides
    internal fun providePaymentPresenter(presenter: PaymentPresenter<PaymentMvpView>): PaymentMvpContract<PaymentMvpView> = presenter

    @Provides
    internal fun provideAirTimePresenter(presenter: AirTimePresenter<AirTimeMvpView>): AirTimeMvpContract<AirTimeMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideProfilePresenter(presenter: ProfilePresenter<ProfileMvpView>): ProfileMvpContract<ProfileMvpView> = presenter

    @Provides
    internal fun provideRequestPresenter(presenter: RequestPresenter<RequestMvpView>): RequestMvpContract<RequestMvpView> = presenter

    @Provides
    internal fun provideTransferPresenter(presenter: CashOutBankPresenter<CashOutBankMvpView>): CashOutBankMvpContract<CashOutBankMvpView> = presenter

    @Provides
    internal fun provideSendMoneyPresenter(presenter: SendMoneyPresenter<SendMoneyMvpView>): SendMoneyMvpContract<SendMoneyMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideDashboardPresenter(presenter: DashboardPresenter<DashboardMvpView>): DashboardMvpContract<DashboardMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideNewPaymentPresenter(presenter: NewPaymentPresenter<NewPaymentMvpView>): NewPaymentMvpContract<NewPaymentMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideSuccessfulPresenter(presenter: SuccessfulPresenter<SuccessfulMvpView>): SuccessfulMvpContract<SuccessfulMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideNotificationPresenter(presenter: NotificationPresenter<NotificationMvpView>): NotificationMvpContract<NotificationMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideTransactionsPresenter(presenter: TransactionsPresenter<TransactionsMvpView>): TransactionsMvpContract<TransactionsMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideNewPaymentAmountPresenter(presenter: NewPaymentAmountPresenter<NewPaymentAmountMvpView>): NewPaymentAmountMvpContract<NewPaymentAmountMvpView> = presenter

    // Provide Fragment Contexts

    @Provides
    internal fun provideOtpPresenter(presenter: OtpPresenter<OtpMvpView>): OtpMvpContract<OtpMvpView> = presenter

    @Provides
    internal fun providePinPresenter(presenter: PinPresenter<PinMvpView>): PinMvpContract<PinMvpView> = presenter

    @Provides
    internal fun provideNamePresenter(presenter: NamePresenter<NameMvpView>): NameMvpContract<NameMvpView> = presenter

    @Provides
    internal fun provideHomePresenter(presenter: HomePresenter<HomeMvpView>): HomeMvpContract<HomeMvpView> = presenter

    @Provides
    internal fun provideEmailPresenter(presenter: EmailPresenter<EmailMvpView>): EmailMvpContract<EmailMvpView> = presenter

    @Provides
    internal fun provideFilterPresenter(presenter: FilterPresenter<FilterMvpView>): FilterMvpContract<FilterMvpView> = presenter

    @Provides
    internal fun provideCompletePresenter(presenter: CompletePresenter<CompleteMvpView>): CompleteMvpContract<CompleteMvpView> = presenter

    @Provides
    internal fun provideSettingsPresenter(presenter: SettingsPresenter<SettingsMvpView>): SettingsMvpContract<SettingsMvpView> = presenter

    @Provides
    internal fun providePasswordPresenter(presenter: PasswordPresenter<PasswordMvpView>): PasswordMvpContract<PasswordMvpView> = presenter

    @Provides
    internal fun provideScheduledPresenter(presenter: ScheduledPresenter<ScheduledMvpView>): ScheduledMvpContract<ScheduledMvpView> = presenter

    @Provides
    internal fun provideOnBoardingPresenter(presenter: OnBoardingPresenter<OnBoardingMvpView>): OnBoardingMvpContract<OnBoardingMvpView> = presenter

    @Provides
    internal fun provideCategoriesPresenter(presenter: CategoriesPresenter<CategoriesMvpView>): CategoriesMvpContract<CategoriesMvpView> = presenter
}
