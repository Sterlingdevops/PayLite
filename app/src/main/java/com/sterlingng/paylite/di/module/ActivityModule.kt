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
import com.sterlingng.paylite.ui.dashboard.DashboardMvpContract
import com.sterlingng.paylite.ui.dashboard.DashboardMvpView
import com.sterlingng.paylite.ui.dashboard.DashboardPresenter
import com.sterlingng.paylite.ui.donate.DonateMvpContract
import com.sterlingng.paylite.ui.donate.DonateMvpView
import com.sterlingng.paylite.ui.donate.DonatePresenter
import com.sterlingng.paylite.ui.give.GiveMvpContract
import com.sterlingng.paylite.ui.give.GiveMvpView
import com.sterlingng.paylite.ui.give.GivePresenter
import com.sterlingng.paylite.ui.give.categories.CategoriesAdapter
import com.sterlingng.paylite.ui.give.categories.CategoriesMvpContract
import com.sterlingng.paylite.ui.give.categories.CategoriesMvpView
import com.sterlingng.paylite.ui.give.categories.CategoriesPresenter
import com.sterlingng.paylite.ui.give.charities.CharitiesAdapter
import com.sterlingng.paylite.ui.give.charities.CharitiesMvpContract
import com.sterlingng.paylite.ui.give.charities.CharitiesMvpView
import com.sterlingng.paylite.ui.give.charities.CharitiesPresenter
import com.sterlingng.paylite.ui.give.filter.FilterMvpContract
import com.sterlingng.paylite.ui.give.filter.FilterMvpView
import com.sterlingng.paylite.ui.give.filter.FilterPresenter
import com.sterlingng.paylite.ui.home.DealsAdapter
import com.sterlingng.paylite.ui.home.HomeMvpContract
import com.sterlingng.paylite.ui.home.HomeMvpView
import com.sterlingng.paylite.ui.home.HomePresenter
import com.sterlingng.paylite.ui.login.LogInMvpContract
import com.sterlingng.paylite.ui.login.LogInMvpView
import com.sterlingng.paylite.ui.login.LogInPresenter
import com.sterlingng.paylite.ui.main.MainMvpContract
import com.sterlingng.paylite.ui.main.MainMvpView
import com.sterlingng.paylite.ui.main.MainPresenter
import com.sterlingng.paylite.ui.payment.PaymentMvpContract
import com.sterlingng.paylite.ui.payment.PaymentMvpView
import com.sterlingng.paylite.ui.payment.PaymentPresenter
import com.sterlingng.paylite.ui.payment.pay.PayMvpContract
import com.sterlingng.paylite.ui.payment.pay.PayMvpView
import com.sterlingng.paylite.ui.payment.pay.PayPresenter
import com.sterlingng.paylite.ui.payment.request.RequestMvpContract
import com.sterlingng.paylite.ui.payment.request.RequestMvpView
import com.sterlingng.paylite.ui.payment.request.RequestPresenter
import com.sterlingng.paylite.ui.payment.scheduled.ScheduledMvpContract
import com.sterlingng.paylite.ui.payment.scheduled.ScheduledMvpView
import com.sterlingng.paylite.ui.payment.scheduled.ScheduledPresenter
import com.sterlingng.paylite.ui.project.ProjectMvpContract
import com.sterlingng.paylite.ui.project.ProjectMvpView
import com.sterlingng.paylite.ui.project.ProjectPresenter
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
import com.sterlingng.paylite.utils.CustomPagerAdapter
import com.sterlingng.paylite.utils.NoScrollingLinearLayoutManager
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
    internal fun provideDealsAdapter(activity: AppCompatActivity): DealsAdapter = DealsAdapter(activity)

    @Provides
    internal fun provideCharitiesAdapter(activity: AppCompatActivity): CharitiesAdapter = CharitiesAdapter(activity)

    @Provides
    internal fun provideCategoriesAdapter(activity: AppCompatActivity): CategoriesAdapter = CategoriesAdapter(activity)

    @Provides
    internal fun provideLinearLayoutManager(activity: AppCompatActivity): LinearLayoutManager = LinearLayoutManager(activity)

    @Provides
    internal fun provideGridLayoutManager(activity: AppCompatActivity): GridLayoutManager = GridLayoutManager(activity, 2)

    @Provides
    internal fun provideCustomPagerAdapter(activity: AppCompatActivity): CustomPagerAdapter = CustomPagerAdapter(activity.supportFragmentManager)

    @Provides
    internal fun provideNoScrollingLinearLayoutManager(activity: AppCompatActivity): NoScrollingLinearLayoutManager = NoScrollingLinearLayoutManager(activity)

    @Provides
    internal fun provideDividerItemDecoration(activity: AppCompatActivity): DividerItemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)

    //Provide Activity Contexts

    @Provides
    @PerActivity
    internal fun provideMainPresenter(presenter: MainPresenter<MainMvpView>): MainMvpContract<MainMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideLogInPresenter(presenter: LogInPresenter<LogInMvpView>): LogInMvpContract<LogInMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideSignUpPresenter(presenter: SignUpPresenter<SignUpMvpView>): SignUpMvpContract<SignUpMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideProjectPresenter(presenter: ProjectPresenter<ProjectMvpView>): ProjectMvpContract<ProjectMvpView> = presenter

    @Provides
    @PerActivity
    internal fun provideDashboardPresenter(presenter: DashboardPresenter<DashboardMvpView>): DashboardMvpContract<DashboardMvpView> = presenter

    // Provide Fragment Contexts

    @Provides
    internal fun providePayPresenter(presenter: PayPresenter<PayMvpView>): PayMvpContract<PayMvpView> = presenter

    @Provides
    internal fun provideOtpPresenter(presenter: OtpPresenter<OtpMvpView>): OtpMvpContract<OtpMvpView> = presenter

    @Provides
    internal fun providePinPresenter(presenter: PinPresenter<PinMvpView>): PinMvpContract<PinMvpView> = presenter

    @Provides
    internal fun provideNamePresenter(presenter: NamePresenter<NameMvpView>): NameMvpContract<NameMvpView> = presenter

    @Provides
    internal fun provideGivePresenter(presenter: GivePresenter<GiveMvpView>): GiveMvpContract<GiveMvpView> = presenter

    @Provides
    internal fun provideHomePresenter(presenter: HomePresenter<HomeMvpView>): HomeMvpContract<HomeMvpView> = presenter

    @Provides
    internal fun provideEmailPresenter(presenter: EmailPresenter<EmailMvpView>): EmailMvpContract<EmailMvpView> = presenter

    @Provides
    internal fun provideDonatePresenter(presenter: DonatePresenter<DonateMvpView>): DonateMvpContract<DonateMvpView> = presenter

    @Provides
    internal fun provideFilterPresenter(presenter: FilterPresenter<FilterMvpView>): FilterMvpContract<FilterMvpView> = presenter

    @Provides
    internal fun providePaymentPresenter(presenter: PaymentPresenter<PaymentMvpView>): PaymentMvpContract<PaymentMvpView> = presenter

    @Provides
    internal fun provideRequestPresenter(presenter: RequestPresenter<RequestMvpView>): RequestMvpContract<RequestMvpView> = presenter

    @Provides
    internal fun provideCompletePresenter(presenter: CompletePresenter<CompleteMvpView>): CompleteMvpContract<CompleteMvpView> = presenter

    @Provides
    internal fun providePasswordPresenter(presenter: PasswordPresenter<PasswordMvpView>): PasswordMvpContract<PasswordMvpView> = presenter

    @Provides
    internal fun provideScheduledPresenter(presenter: ScheduledPresenter<ScheduledMvpView>): ScheduledMvpContract<ScheduledMvpView> = presenter

    @Provides
    internal fun provideCharitiesPresenter(presenter: CharitiesPresenter<CharitiesMvpView>): CharitiesMvpContract<CharitiesMvpView> = presenter

    @Provides
    internal fun provideCategoriesPresenter(presenter: CategoriesPresenter<CategoriesMvpView>): CategoriesMvpContract<CategoriesMvpView> = presenter
}
