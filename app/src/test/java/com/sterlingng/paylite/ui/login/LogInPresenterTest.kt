package com.sterlingng.paylite.ui.login

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.Response
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
        val data = HashMap<String, Any>()
        val response = Response()

        `when`(mDataManager.signIn(data)).thenReturn(Observable.just(response))


        //trigger presenter
        mLogInPresenter.doLogIn(data)
        mTestScheduler.triggerActions()

        //show progress bar, load data
        verify(mMockLogInMvpView).showLoading()
        verify(mDataManager).signIn(data)

        verify(mMockLogInMvpView).hideLoading()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mLogInPresenter.onDetach()
    }
}
