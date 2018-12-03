package com.sterlingng.paylite.ui.signup.email


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.signup.SignUpActivity
import com.sterlingng.paylite.utils.AppConstants
import com.sterlingng.paylite.utils.AppUtils.createEvent
import com.sterlingng.paylite.utils.AppUtils.createId
import com.sterlingng.paylite.utils.OnChildDidClickNext
import com.sterlingng.paylite.utils.isValidEmail
import com.sterlingng.views.LargeLabelEditText
import javax.inject.Inject

class EmailFragment : BaseFragment(), EmailMvpView {

    @Inject
    lateinit var mPresenter: EmailMvpContract<EmailMvpView>

    lateinit var mDidClickNext: OnChildDidClickNext

    private lateinit var mEmailEditText: LargeLabelEditText
    private lateinit var exit: ImageView
    private lateinit var next: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_email, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        next = view.findViewById(R.id.next_email)
        exit = view.findViewById(R.id.exit)

        mEmailEditText = view.findViewById(R.id.email)
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        next.setOnClickListener {
            if (mEmailEditText.text.isEmpty() || !mEmailEditText.text.isValidEmail()) {
                show("A valid email address is required", true)
                return@setOnClickListener
            }

            createEvent(
                    baseActivity,
                    AppConstants.ON_BOARDING,
                    AppConstants.EVENT_THREE,
                    AppConstants.EVENT_SEVEN,
                    createId(),
                    (baseActivity as SignUpActivity).latitude,
                    (baseActivity as SignUpActivity).longitude,
                    javaClass.simpleName,
                    ""
            )
            mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, mEmailEditText.text)
        }
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

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
