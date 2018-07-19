package com.sterlingng.paylite.ui.signup.password


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.utils.OnChildDidClickNext
import javax.inject.Inject

class PasswordFragment : BaseFragment(), PasswordMvpView {

    @Inject
    lateinit var mPresenter: PasswordMvpContract<PasswordMvpView>

    lateinit var mDidClickNext: OnChildDidClickNext
    lateinit var next: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_password, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        next = view.findViewById(R.id.next_password)
    }

    override fun setUp(view: View) {
        next.setOnClickListener {
            mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!)
            hideKeyboard()
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        private const val INDEX = "PasswordFragment.INDEX"

        fun newInstance(index: Int): PasswordFragment {
            val fragment = PasswordFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}
