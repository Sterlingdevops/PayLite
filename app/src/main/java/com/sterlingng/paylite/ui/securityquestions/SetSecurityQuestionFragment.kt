package com.sterlingng.paylite.ui.securityquestions

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Question
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.views.NoScrollingLinearLayoutManager
import javax.inject.Inject

class SetSecurityQuestionFragment : BaseFragment(), SetSecurityQuestionMvpView {

    @Inject
    lateinit var mPresenter: SetSecurityQuestionMvpContract<SetSecurityQuestionMvpView>

    @Inject
    lateinit var mLinearLayoutManager: NoScrollingLinearLayoutManager

    lateinit var mRecyclerView: RecyclerView
    private lateinit var mSetSecurityQuestionAdapter: SetSecurityQuestionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_set_security_question, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        mRecyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun setUp(view: View) {
        mSetSecurityQuestionAdapter = SetSecurityQuestionAdapter(baseActivity)
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.adapter = mSetSecurityQuestionAdapter

        mPresenter.loadQuestions()
    }

    override fun initView(mockQuestions: ArrayList<Question>) {
        mSetSecurityQuestionAdapter.add(mockQuestions)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): SetSecurityQuestionFragment {
            val fragment = SetSecurityQuestionFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
