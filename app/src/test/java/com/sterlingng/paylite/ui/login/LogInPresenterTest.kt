package com.sterlingng.paylite.ui.login

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.rx.TestSchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class LogInPresenterTest {

    @Mock
    lateinit var mDataManager: DataManager

    @Mock
    lateinit var mMockLogInMvpView: LogInMvpView

    private lateinit var mTestScheduler: TestScheduler
    private lateinit var mSchedulerProvider: SchedulerProvider
    private lateinit var mCompositeDisposable: CompositeDisposable
    private lateinit var mLogInPresenter: LogInPresenter<LogInMvpView>

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mTestScheduler = TestScheduler()
        mCompositeDisposable = CompositeDisposable()
        mSchedulerProvider = TestSchedulerProvider(mTestScheduler)
        mLogInPresenter = LogInPresenter(mDataManager, mSchedulerProvider, mCompositeDisposable)

        mLogInPresenter.onAttach(mMockLogInMvpView)
    }

    @Test
    fun doForgotPassword() {

    }

    @Test
    fun doLogIn() {
        //provide mock data
        val email = "mail@email.com"
        val password = "00000"
        `when`(mDataManager.mockLogin(email, password)).thenReturn(Observable.just(User(email, password, null)))

        //trigger presenter
        mLogInPresenter.doLogIn(email, password)
        mTestScheduler.triggerActions()

        //show progress bar, load data
        verify(mMockLogInMvpView).showLoading()
        verify(mDataManager).mockLogin(email, password)

        verify(mMockLogInMvpView).hideLoading()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mLogInPresenter.onDetach()
    }
}
