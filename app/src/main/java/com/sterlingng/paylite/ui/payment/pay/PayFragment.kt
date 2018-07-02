package com.sterlingng.paylite.ui.payment.pay


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject

class PayFragment : BaseFragment(), PayMvpView {

    @Inject
    lateinit var mPresenter: PayMvpContract<PayMvpView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pay, container, false)
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

        fun newInstance(): PayFragment {
            val fragment = PayFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }
}
