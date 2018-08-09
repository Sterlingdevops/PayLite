package com.sterlingng.paylite.ui.request


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject

class RequestFragment : BaseFragment(), RequestMvpView {

    @Inject
    lateinit var mPresenter: RequestMvpContract<RequestMvpView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_request, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {

    }

    override fun bindViews(view: View) {

    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): RequestFragment {
            val fragment = RequestFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }
}
