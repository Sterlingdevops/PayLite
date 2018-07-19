package com.sterlingng.paylite.ui.signup.name


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.utils.OnChildDidClickNext
import javax.inject.Inject

class NameFragment : BaseFragment(), NameMvpView {

    @Inject
    lateinit var mPresenter: NameMvpContract<NameMvpView>

    lateinit var firstNameTextView: TextView
    lateinit var lastNameTextView: TextView
    lateinit var next: Button

    lateinit var mDidClickNext: OnChildDidClickNext

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_name, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        next = view.findViewById(R.id.next_name)
        lastNameTextView = view.findViewById(R.id.last_name)
        firstNameTextView = view.findViewById(R.id.first_name)
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

        private const val INDEX = "NameFragment.INDEX"

        fun newInstance(index: Int): NameFragment {
            val fragment = NameFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}
