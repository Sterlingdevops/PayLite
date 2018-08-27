package com.sterlingng.paylite.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.MenuItem
import android.view.View
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.ncapdevi.fragnav.FragNavController
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.home.HomeFragment
import com.sterlingng.paylite.ui.settings.SettingsFragment
import com.sterlingng.paylite.ui.transactions.TransactionsFragment
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class DashboardActivity : BaseActivity(), DashboardMvpView,
        BottomNavigationView.OnNavigationItemSelectedListener,
        FragNavController.RootFragmentListener {

    private lateinit var mBottomNavigationView: BottomNavigationViewEx

    @Inject
    lateinit var mPresenter: DashboardMvpContract<DashboardMvpView>
    private var mSelectedItem = 0

    lateinit var mNavController: FragNavController
    override val numberOfRootFragments: Int
        get() = 3

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        activityComponent.inject(this)
        mPresenter.onAttach(this)

        mNavController = FragNavController(supportFragmentManager, R.id.container)
        mNavController.apply {
            createEager = true
            fragmentHideStrategy = FragNavController.DETACH_ON_NAVIGATE_HIDE_ON_SWITCH
            rootFragmentListener = this@DashboardActivity
            initialize(savedInstanceState = savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SELECTED_ITEM, mSelectedItem)
        super.onSaveInstanceState(outState)
        mNavController.onSaveInstanceState(outState)
    }

    override fun bindViews() {
        mBottomNavigationView = findViewById(R.id.navigation)
    }

    override fun setUp() {
        mBottomNavigationView.apply {
            setLargeTextSize(14f)
            setSmallTextSize(10f)
            setTextVisibility(false)
            enableAnimation(true)
            enableShiftingMode(false)
            enableItemShiftingMode(false)
            onNavigationItemSelectedListener = this@DashboardActivity
        }
    }

    override fun getRootFragment(index: Int): Fragment {
        return when (index) {
            INDEX_HOME -> HomeFragment.newInstance()
            INDEX_SETTINGS -> SettingsFragment.newInstance()
            INDEX_TRANSACTIONS -> TransactionsFragment.newInstance()
            else -> throw IllegalStateException("Need to send an index that we know")
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> mNavController.switchTab(INDEX_HOME)
            R.id.nav_settings -> mNavController.switchTab(INDEX_SETTINGS)
            R.id.nav_transactions -> mNavController.switchTab(INDEX_TRANSACTIONS)
        }
        return true
    }

    override fun onBackPressed() {
        when {
            mNavController.isRootFragment.not() -> mNavController.popFragment()
            else -> super.onBackPressed()
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {
        private const val INDEX_HOME = FragNavController.TAB1
        private const val INDEX_SETTINGS = FragNavController.TAB3
        private const val INDEX_TRANSACTIONS = FragNavController.TAB2
        const val SELECTED_ITEM = "arg_selected_item"

        fun getStartIntent(context: Context): Intent {
            val intent = Intent(context, DashboardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            return intent
        }
    }
}
