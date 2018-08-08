package com.sterlingng.paylite.ui.settings


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.main.MainActivity
import javax.inject.Inject

class SettingsFragment : BaseFragment(), SettingsMvpView {

    @Inject
    lateinit var mPresenter: SettingsMvpContract<SettingsMvpView>

    private lateinit var mLogOutTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mLogOutTextView = view.findViewById(R.id.log_out)
    }

    override fun setUp(view: View) {
        mLogOutTextView.setOnClickListener {
            mPresenter.logOut()
        }
    }

    override fun onLogOutComplete() {
        startActivity(MainActivity.getStartIntent(baseActivity))
        baseActivity.finish()
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): SettingsFragment {
            val fragment = SettingsFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
