package com.sterlingng.paylite.ui.signup.email


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.utils.OnChildDidClickNext
import com.sterlingng.paylite.utils.isValidEmail
import com.sterlingng.views.LargeLabelEditText
import javax.inject.Inject

class EmailFragment : BaseFragment(), EmailMvpView {

    @Inject
    lateinit var mPresenter: EmailMvpContract<EmailMvpView>

    lateinit var mDidClickNext: OnChildDidClickNext

    private lateinit var mPhoneEditText: LargeLabelEditText
    private lateinit var mEmailEditText: LargeLabelEditText
    private lateinit var mBvnEditText: LargeLabelEditText
    lateinit var next: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_email, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        next = view.findViewById(R.id.next_email)

        mPhoneEditText = view.findViewById(R.id.phone)
        mEmailEditText = view.findViewById(R.id.email)
        mBvnEditText = view.findViewById(R.id.bvn)
    }

    override fun setUp(view: View) {
        next.setOnClickListener {
            if (mPhoneEditText.text().isEmpty() || mEmailEditText.text().isEmpty() || mBvnEditText.text().isEmpty()) {
                show("ALl the fields are required", true)
                return@setOnClickListener
            }

            if (mEmailEditText.text().isEmpty() || !mEmailEditText.text().isValidEmail()) {
                show("A valid email address is required", false)
                return@setOnClickListener
            }

            if (mBvnEditText.text().length != 11) {
                show("BVN should be 11 characters long", true)
                return@setOnClickListener
            }

            mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!,
                    listOf(mPhoneEditText.mTextEditText.text.toString(),
                            mEmailEditText.mTextEditText.text.toString(),
                            mBvnEditText.mTextEditText.text.toString()))
            hideKeyboard()
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {
        private const val INDEX: String = "EmailFragment.INDEX"

        fun newInstance(index: Int): EmailFragment {
            val fragment = EmailFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}
