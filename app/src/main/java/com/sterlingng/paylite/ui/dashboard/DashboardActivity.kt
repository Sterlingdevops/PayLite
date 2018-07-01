package com.sterlingng.paylite.ui.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.MenuItem
import android.view.View
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.utils.Log
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

class DashboardActivity : BaseActivity(), DashboardMvpView, BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var mBottomNavigationView: BottomNavigationViewEx

    @Inject
    lateinit var mPresenter: DashboardMvpContract<DashboardMvpView>
    private var mFragmentManager: FragmentManager = supportFragmentManager
    private lateinit var mCurrentFragment: Fragment
    private lateinit var settingsFragment: Fragment
    private lateinit var marketFragment: Fragment
    private lateinit var walletFragment: Fragment
    private lateinit var bidsFragment: Fragment
    private var mSelectedItem = 0

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
        Log.d("onCreate() Called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SELECTED_ITEM, mSelectedItem)
        super.onSaveInstanceState(outState)
        Log.d("onSaveInstanceState() Called")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("onRestoreInstanceState() Called")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        Log.d("onPostCreate() Called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("onResume() Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("onPause() Called")
    }

    override fun bindViews() {
        mBottomNavigationView = findViewById(R.id.navigation)
    }

    override fun setUp() {
//        settingsFragment = SettingsFragment.newInstance()
//        walletFragment = WalletFragment.newInstance()
//        marketFragment = MarketFragment.newInstance()
//        bidsFragment = BidsFragment.newInstance()

        createFragments()

        mBottomNavigationView.onNavigationItemSelectedListener = this
        mBottomNavigationView.enableAnimation(true)
        mBottomNavigationView.enableShiftingMode(false)
        mBottomNavigationView.enableItemShiftingMode(false)

        mSelectedItem = intent.getIntExtra(SELECTED_ITEM, 0)

        val homeItem = mBottomNavigationView.menu.getItem(mSelectedItem)
        Log.d(homeItem.itemId.toString())
        mBottomNavigationView.selectedItemId = homeItem.itemId
        selectFragment(homeItem)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return selectFragment(item)
    }

    override fun onBackPressed() {
        val homeItem = mBottomNavigationView.menu.getItem(0)
        if (mSelectedItem != homeItem.itemId) {
            mBottomNavigationView.selectedItemId = homeItem.itemId
            selectFragment(homeItem)
        } else {
            super.onBackPressed()
        }
    }

    //Method to add and hide all of the fragments you need to. In my case I hide 4 fragments, while 1 is visible, that is the first one.
    private fun addHideFragment(fragment: Fragment, tag: String) {
        mFragmentManager.beginTransaction().add(R.id.container, fragment, tag).hide(fragment).commit()
    }

    //Method to hide and show the fragment you need. It is called in the BottomBar click listener.
    private fun hideShowFragment(hide: Fragment, show: Fragment): Fragment {
        mFragmentManager.beginTransaction().hide(hide).show(show).commit()
        return show
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    //Add all the fragments that need to be added and hidden. Also, add the one that is supposed to be the starting one, that one is not hidden.
    private fun createFragments() {
//        mFragmentManager
//                .beginTransaction()
//                .remove(bidsFragment)
//                .remove(walletFragment)
//                .remove(settingsFragment)
//                .remove(marketFragment).commit()
//
//        addHideFragment(settingsFragment, settings)
//        addHideFragment(walletFragment, wallet)
//        addHideFragment(bidsFragment, bids)
//
//        mFragmentManager.beginTransaction().add(R.id.container, marketFragment, market).commit()
//        mCurrentFragment = marketFragment
    }

    private fun selectFragment(item: MenuItem): Boolean {
        if (mSelectedItem == item.itemId) {
            return false
        }

//        when (item.itemId) {
//            R.id.nav_market -> {
//                mCurrentFragment = hideShowFragment(mCurrentFragment, marketFragment)
//            }
//            R.id.nav_bid -> {
//                mCurrentFragment = hideShowFragment(mCurrentFragment, bidsFragment)
//            }
//            R.id.nav_wallet -> {
//                mCurrentFragment = hideShowFragment(mCurrentFragment, walletFragment)
//            }
//            R.id.nav_settings -> {
//                mCurrentFragment = hideShowFragment(mCurrentFragment, settingsFragment)
//            }
//        }

        mSelectedItem = item.itemId
        return true
    }

    companion object {
        private const val bids = "BidsFragment"
        private const val wallet = "WalletFragment"
        private const val market = "MarketFragment"
        const val SELECTED_ITEM = "arg_selected_item"
        private const val settings = "SettingsFragment"

        fun getStartIntent(context: Context): Intent {
            val intent = Intent(context, DashboardActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            return intent
        }
    }
}
