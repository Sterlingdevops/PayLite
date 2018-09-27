package com.sterlingng.paylite.ui.scheduled

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject

class ScheduledFragment : BaseFragment(), ScheduledMvpView {

    @Inject
    lateinit var mPresenter: ScheduledMvpContract<ScheduledMvpView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_scheduled, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {

    }

    override fun bindViews(view: View) {

    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): ScheduledFragment {
            val fragment = ScheduledFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }
}
