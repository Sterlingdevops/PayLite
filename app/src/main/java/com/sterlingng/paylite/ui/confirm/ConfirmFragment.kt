package com.sterlingng.paylite.ui.confirm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.successful.SuccessfulActivity
import javax.inject.Inject

class ConfirmFragment : BaseFragment(), ConfirmMvpView {

    @Inject
    lateinit var mPresenter: ConfirmMvpContract<ConfirmMvpView>

    private lateinit var next: Button
    private lateinit var exit: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_confirm, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }
        next.setOnClickListener {
            startActivity(SuccessfulActivity.getStartIntent(baseActivity))
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): ConfirmFragment {
            val fragment = ConfirmFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
