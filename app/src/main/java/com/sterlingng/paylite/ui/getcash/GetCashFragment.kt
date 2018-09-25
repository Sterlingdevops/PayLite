package com.sterlingng.paylite.ui.getcash

import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class GetCashFragment : BaseFragment(), GetCashMvpView {

    @Inject
    lateinit var mPresenter: GetCashMvpContract<GetCashMvpView>

    private lateinit var mBalanceTextView: TextView

    private lateinit var mSelfTextView: TextView
    private lateinit var mSelfCheckBox: CheckBox

    private lateinit var mOthersTextView: TextView
    private lateinit var mOthersCheckBox: CheckBox

    private lateinit var mSelfNestedScrollView: NestedScrollView
    private lateinit var mOthersNestedScrollView: NestedScrollView

    private var others: Boolean = false

    lateinit var exit: ImageView
    lateinit var next: Button

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        next.setOnClickListener {

        }

        others = false
        mSelfCheckBox.isChecked = true
        mOthersCheckBox.isChecked = false
        mOthersNestedScrollView.visibility = View.GONE
        mSelfNestedScrollView.visibility = View.VISIBLE

        mSelfTextView.setOnClickListener {
            others = false
            mSelfCheckBox.isChecked = true
            mOthersCheckBox.isChecked = false
            mOthersNestedScrollView.visibility = View.GONE
            mSelfNestedScrollView.visibility = View.VISIBLE
        }

        mOthersTextView.setOnClickListener {
            others = true
            mSelfCheckBox.isChecked = false
            mOthersCheckBox.isChecked = true
            mOthersNestedScrollView.visibility = View.VISIBLE
            mSelfNestedScrollView.visibility = View.GONE
        }
    }

    override fun bindViews(view: View) {
        mBalanceTextView = view.findViewById(R.id.balance)

        mSelfTextView = view.findViewById(R.id.self)
        mSelfCheckBox = view.findViewById(R.id.self_checkBox)

        mOthersTextView = view.findViewById(R.id.others)
        mOthersCheckBox = view.findViewById(R.id.others_checkBox)

        next = view.findViewById(R.id.next)
        exit = view.findViewById(R.id.exit)

        mSelfNestedScrollView = view.findViewById(R.id.self_scrollview)
        mOthersNestedScrollView = view.findViewById(R.id.others_scrollview)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_get_cash, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    companion object {

        fun newInstance(): GetCashFragment {
            val fragment = GetCashFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}

interface GetCashMvpView : MvpView

interface GetCashMvpContract<V : GetCashMvpView> : MvpPresenter<V>

class GetCashPresenter<V : GetCashMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), GetCashMvpContract<V>
