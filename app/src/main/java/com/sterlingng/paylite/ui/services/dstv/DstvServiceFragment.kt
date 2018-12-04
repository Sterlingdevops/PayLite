package com.sterlingng.paylite.ui.services.dstv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject

class DstvServiceFragment : BaseFragment(), DstvServiceMvpView {

    @Inject
    lateinit var mPresenter: DstvServiceMvpContract<DstvServiceMvpView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dstv_service, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {

    }

    override fun setUp(view: View) {

    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): DstvServiceFragment {
            val fragment = DstvServiceFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
