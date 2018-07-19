package com.sterlingng.paylite.ui.signup.complete


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import javax.inject.Inject

class CompleteFragment : BaseFragment(), CompleteMvpView {

    @Inject
    lateinit var mPresenter: CompleteMvpContract<CompleteMvpView>

    lateinit var next: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_complete, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        next = view.findViewById(R.id.next_complete)
    }

    override fun setUp(view: View) {
        next.setOnClickListener {
            val intent = DashboardActivity.getStartIntent(baseActivity)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .putExtra(DashboardActivity.SELECTED_ITEM, 0)
            startActivity(intent)
            baseActivity.finish()
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        private const val INDEX = "CompleteFragment.INDEX"

        fun newInstance(): CompleteFragment {
            val fragment = CompleteFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
