package com.sterlingng.paylite.ui.splitamount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class SplitAmountFragment : BaseFragment(), SplitAmountMvpView {

    @Inject
    lateinit var mPresenter: SplitAmountMvpContract<SplitAmountMvpView>

    private lateinit var mSplitAmountEditText: EditText
    private lateinit var mUnequalTextView: TextView
    private lateinit var mUnequalCheckBox: CheckBox
    private lateinit var mEqualTextView: TextView
    private lateinit var mEqualCheckBox: CheckBox
    private var equal: Boolean = false
    lateinit var exit: ImageView
    lateinit var next: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_split_amount, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)

        mEqualCheckBox = view.findViewById(R.id.equal_checkBox)
        mUnequalCheckBox = view.findViewById(R.id.unequal_checkBox)

        mEqualTextView = view.findViewById(R.id.equal_split)
        mUnequalTextView = view.findViewById(R.id.unequal_split)

        mSplitAmountEditText = view.findViewById(R.id.split_amount)
    }

    override fun setUp(view: View) {
        next.setOnClickListener {
            if (mSplitAmountEditText.text.isEmpty()) {
                show("SplitAmount number is required", true)
                return@setOnClickListener
            }
        }

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mEqualTextView.setOnClickListener {
            equal = true
            mEqualCheckBox.isChecked = true
            mUnequalCheckBox.isChecked = false
        }

        mUnequalTextView.setOnClickListener {
            equal = false
            mEqualCheckBox.isChecked = false
            mUnequalCheckBox.isChecked = true
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): SplitAmountFragment {
            val fragment = SplitAmountFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}

interface SplitAmountMvpView : MvpView

interface SplitAmountMvpContract<V : SplitAmountMvpView> : MvpPresenter<V>

class SplitAmountPresenter<V : SplitAmountMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), SplitAmountMvpContract<V>