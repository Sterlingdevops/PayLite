package com.sterlingng.paylite.ui.give.charities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject

class CharitiesFragment : BaseFragment(), CharitiesMvpView {

    @Inject
    lateinit var mPresenter: CharitiesMvpContract<CharitiesMvpView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_charities, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {

    }

    override fun setUp(view: View) {

    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): CharitiesFragment {
            val fragment = CharitiesFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
