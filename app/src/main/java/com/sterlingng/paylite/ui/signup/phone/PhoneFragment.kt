package com.sterlingng.paylite.ui.signup.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import com.sterlingng.paylite.utils.OnChildDidClickNext
import com.sterlingng.views.LargeLabelEditText
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class PhoneFragment : BaseFragment(), PhoneMvpView {

    @Inject
    lateinit var mPresenter: PhoneMvpContract<PhoneMvpView>

    private lateinit var mPhoneEditText: LargeLabelEditText
    lateinit var mDidClickNext: OnChildDidClickNext
    lateinit var exit: ImageView
    lateinit var next: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_phone, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next_phone)
        mPhoneEditText = view.findViewById(R.id.phone)
    }

    override fun setUp(view: View) {
        next.setOnClickListener {
            if (mPhoneEditText.text().isEmpty() || mPhoneEditText.text().length != 10) {
                show("Phone number is required", true)
                return@setOnClickListener
            }
            mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!, mPhoneEditText.text())
        }

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        private const val INDEX = "PhoneFragment.INDEX"

        fun newInstance(index: Int): PhoneFragment {
            val fragment = PhoneFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}

interface PhoneMvpView : MvpView

interface PhoneMvpContract<V : PhoneMvpView> : MvpPresenter<V>

class PhonePresenter<V : PhoneMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), PhoneMvpContract<V>