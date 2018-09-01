package com.sterlingng.paylite.ui.splitcontacts

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.data.model.ContactItem
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SplitContactFragment : BaseFragment(), SplitContactMvpView, SplitContactsAdapter.OnRetryClicked {

    @Inject
    lateinit var mPresenter: SplitContactMvpContract<SplitContactMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    private lateinit var mAddContactTextView: TextView
    private lateinit var mSplitCostTextView: TextView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mBalanceTextView: TextView
    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mSplitContactsAdapter: SplitContactsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_split_contact, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)

        mBalanceTextView = view.findViewById(R.id.balance)
        mSplitCostTextView = view.findViewById(R.id.title)
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mAddContactTextView = view.findViewById(R.id.add_contact)
    }

    override fun setUp(view: View) {
        arguments?.getString(AMOUNT).let {
            mSplitCostTextView.text = baseActivity.resources.getString(R.string.split_ngn, it)
        }

        mSplitContactsAdapter = SplitContactsAdapter(baseActivity)
        mSplitContactsAdapter.mRecyclerViewClickListener = this
        mSplitContactsAdapter.onRetryClickedListener = this
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.adapter = mSplitContactsAdapter

        mSplitContactsAdapter.add(ContactItem("", 0))
        mSplitContactsAdapter.add(ContactItem("", 0))

        mAddContactTextView.setOnClickListener {
            mSplitContactsAdapter.add(ContactItem("", 0))
        }

        next.setOnClickListener {

        }

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    override fun onRetryClicked() {

    }

    companion object {

        private const val AMOUNT = "SplitContactFragment.AMOUNT"

        fun newInstance(amount: String): SplitContactFragment {
            val fragment = SplitContactFragment()
            val args = Bundle()
            args.putString(AMOUNT, amount)
            fragment.arguments = args
            return fragment
        }
    }
}

interface SplitContactMvpView : MvpView

interface SplitContactMvpContract<V : SplitContactMvpView> : MvpPresenter<V>

class SplitContactPresenter<V : SplitContactMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), SplitContactMvpContract<V>