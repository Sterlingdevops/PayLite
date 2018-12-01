package com.sterlingng.paylite.ui.settings.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject

class ProfileFragment : BaseFragment(), ProfileMvpView {

    @Inject
    lateinit var mPresenter: ProfileMvpContract<ProfileMvpView>

    private lateinit var exit: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        mPresenter.onViewInitialized()

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }
    }

    override fun logout() {
        baseActivity.logout()
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
    }

    override fun initView(user: User) {

    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {
        fun newInstance(): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
