package com.sterlingng.paylite.ui.sheduledtransaction


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject

class ScheduledTransactionFragment : BaseFragment(), ScheduledTransactionMvpView {

    @Inject
    lateinit var mPresenter: ScheduledTransactionMvpContract<ScheduledTransactionMvpView>

    private lateinit var exit: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_scheduled_transaction, container, false)
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
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): ScheduledTransactionFragment {
            val fragment = ScheduledTransactionFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
