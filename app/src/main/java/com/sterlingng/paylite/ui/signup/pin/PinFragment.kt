package com.sterlingng.paylite.ui.signup.pin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.goodiebag.pinview.PinView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.utils.OnChildDidClickNext
import javax.inject.Inject

class PinFragment : BaseFragment(), PinMvpView {

    @Inject
    lateinit var mPresenter: PinMvpContract<PinMvpView>

    lateinit var mDidClickNext: OnChildDidClickNext
    private lateinit var pinView: PinView
    private lateinit var exit: ImageView
    private lateinit var next: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_token, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        pinView.setPinViewEventListener { _, _ ->
            mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, pinView.value)
            hideKeyboard()
        }
        next.setOnClickListener {
            mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, pinView.value)
            hideKeyboard()
        }
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
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
