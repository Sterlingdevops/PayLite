package com.sterlingng.paylite.ui.home

import com.sterlingng.paylite.data.manager.DataManager
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


class HomePresenterTest {

    @Mock
    lateinit var mDataManager: DataManager

    @Mock
    lateinit var mMockHomeMvpView: HomeMvpView

    private lateinit var mTestScheduler: TestScheduler
    private lateinit var mSchedulerProvider: SchedulerProvider
    private lateinit var mCompositeDisposable: CompositeDisposable
    private lateinit var mHomePresenter: HomePresenter<HomeMvpView>

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

    @Test
    fun loadDealsTest() {
        // Provide mock data
        `when`(mDataManager.mockDeals()).thenReturn(Observable.just(ArrayList()))

        //trigger presenter
        mHomePresenter.loadDeals()
        mTestScheduler.triggerActions()

        //show progress bar, load data
        verify(mMockHomeMvpView).showLoading()
        verify(mDataManager).mockDeals()

        //update view, remove loading progress bar
        verify(mMockHomeMvpView).updateDeals(ArrayList())
        verify(mMockHomeMvpView).hideLoading()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mHomePresenter.onDetach()
    }
}