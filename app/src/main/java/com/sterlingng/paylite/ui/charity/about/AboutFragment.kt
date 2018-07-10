package com.sterlingng.paylite.ui.charity.about


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import me.chensir.expandabletextview.ExpandableTextView
import javax.inject.Inject

class AboutFragment : BaseFragment(), AboutMvpView {

    @Inject
    lateinit var mPresenter: AboutMvpContract<AboutMvpView>

    private lateinit var mAboutExpandableTextView: ExpandableTextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mAboutExpandableTextView = view.findViewById(R.id.text)
    }

    override fun setUp(view: View) {
        mAboutExpandableTextView.text = getString(R.string.placeholder)
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): AboutFragment {
            val fragment = AboutFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
