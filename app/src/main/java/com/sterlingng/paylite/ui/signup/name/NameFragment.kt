package com.sterlingng.paylite.ui.signup.name


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.utils.OnChildDidClickNext
import javax.inject.Inject

class NameFragment : BaseFragment(), NameMvpView {

    @Inject
    lateinit var mPresenter: NameMvpContract<NameMvpView>

    private lateinit var mFirstNameEditText: EditText
    private lateinit var mLastNameEditText: EditText
    private lateinit var mUsernameEditText: EditText
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
        mUsernameEditText = view.findViewById(R.id.username)
        mLastNameEditText = view.findViewById(R.id.last_name)
        mFirstNameEditText = view.findViewById(R.id.first_name)
    }

    override fun setUp(view: View) {
        next.setOnClickListener {
            if (mFirstNameEditText.text.isEmpty() || mLastNameEditText.text.isEmpty() || mUsernameEditText.text.isEmpty()) {
                show("ALl the fields are required", true)
                return@setOnClickListener
            }

            if (mUsernameEditText.text.length < 6) {
                show("Username should be at least 6 characters long", true)
                return@setOnClickListener
            }

            mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!,
                    listOf(mFirstNameEditText.text.toString(),
                            mLastNameEditText.text.toString(),
                            mUsernameEditText.text.toString()))
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
