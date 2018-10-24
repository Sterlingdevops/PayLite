package com.sterlingng.paylite.ui.request


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.paylite.ui.request.custom.CustomRequestFragment
import javax.inject.Inject

class RequestFragment : BaseFragment(), RequestMvpView {

    @Inject
    lateinit var mPresenter: RequestMvpContract<RequestMvpView>

    private lateinit var mFullNameTextView: TextView
    private lateinit var mLinkTextView: TextView
    private lateinit var mShareButton: Button
    private lateinit var exit: ImageView
    private lateinit var next: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_request, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        mPresenter.onViewInitialized()

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mShareButton.setOnClickListener {
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(CustomRequestFragment.newInstance())
        }

        next.setOnClickListener {
            (baseActivity as DashboardActivity)
                    .mNavController
                    .pushFragment(CustomRequestFragment.newInstance())
        }
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)
        mShareButton = view.findViewById(R.id.share)
        mLinkTextView = view.findViewById(R.id.link)
        mFullNameTextView = view.findViewById(R.id.full_name)
    }

    @SuppressLint("SetTextI18n")
    override fun initView(user: User) {
        mFullNameTextView.text = "${user.firstName} ${user.lastName}"
        mLinkTextView.text = "paylite.com/${user.phoneNumber}"
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

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
