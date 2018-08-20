package com.sterlingng.paylite.ui.signup.pin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.goodiebag.pinview.PinView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.utils.OnChildDidClickNext
import kotlinx.android.synthetic.main.fragment_token.*
import javax.inject.Inject

class PinFragment : BaseFragment(), PinMvpView {

    @Inject
    lateinit var mPresenter: PinMvpContract<PinMvpView>

    lateinit var mDidClickNext: OnChildDidClickNext

    lateinit var next: Button
    lateinit var pinView: PinView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_token, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        pin_view.setPinViewEventListener { _, _ ->
            mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, "")
            hideKeyboard()
        }
        next.setOnClickListener {
            mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, "")
            hideKeyboard()
        }
    }

    override fun bindViews(view: View) {
        next = view.findViewById(R.id.next_pin)
        pinView = view.findViewById(R.id.pin_view)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        private const val INDEX = "PinFragment.INDEX"

        fun newInstance(index: Int): PinFragment {
            val fragment = PinFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}
