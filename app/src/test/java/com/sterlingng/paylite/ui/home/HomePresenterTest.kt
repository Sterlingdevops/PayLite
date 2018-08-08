package com.sterlingng.paylite.ui.home

import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.rx.TestSchedulerProvider
import com.sterlingng.paylite.util.RxSchedulersOverrideRule
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class HomePresenterTest {

    @Mock
    lateinit var mDataManager: DataManager

    @Mock
    lateinit var mMockHomeMvpView: HomeMvpView

    private lateinit var mTestScheduler: TestScheduler
    private lateinit var mSchedulerProvider: SchedulerProvider
    private lateinit var mCompositeDisposable: CompositeDisposable
    private lateinit var mHomePresenter: HomePresenter<HomeMvpView>

    @get:Rule
    val rxSchedulersOverrideRule = RxSchedulersOverrideRule()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        mTestScheduler = TestScheduler()
        mCompositeDisposable = CompositeDisposable()
        mSchedulerProvider = TestSchedulerProvider(mTestScheduler)
        mHomePresenter = HomePresenter(mDataManager, mSchedulerProvider, mCompositeDisposable)

        mHomePresenter.onAttach(mMockHomeMvpView)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mHomePresenter.onDetach()
    }
}