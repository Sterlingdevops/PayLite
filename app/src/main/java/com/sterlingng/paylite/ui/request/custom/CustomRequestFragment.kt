package com.sterlingng.paylite.ui.request.custom

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
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class CustomRequestFragment : BaseFragment(), CustomRequestMvpView {

    @Inject
    lateinit var mPresenter: CustomRequestMvpContract<CustomRequestMvpView>

    private lateinit var exit: ImageView
    private lateinit var next: Button

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next)
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        next.setOnClickListener {

        }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_custom_request, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    companion object {

        fun newInstance(): CustomRequestFragment {
            val fragment = CustomRequestFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}

interface CustomRequestMvpView : MvpView

interface CustomRequestMvpContract<V : CustomRequestMvpView> : MvpPresenter<V>

class CustomRequestPresenter<V : CustomRequestMvpView>
@Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), CustomRequestMvpContract<V>
