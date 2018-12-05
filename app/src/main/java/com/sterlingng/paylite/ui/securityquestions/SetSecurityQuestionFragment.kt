package com.sterlingng.paylite.ui.securityquestions

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.Question
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.confirm.ConfirmFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import com.sterlingng.views.NoScrollingLinearLayoutManager
import javax.inject.Inject

class SetSecurityQuestionFragment : BaseFragment(), SetSecurityQuestionMvpView, ConfirmFragment.OnPinValidated {

    @Inject
    lateinit var mPresenter: SetSecurityQuestionMvpContract<SetSecurityQuestionMvpView>

    @Inject
    lateinit var mLinearLayoutManager: NoScrollingLinearLayoutManager

    private lateinit var exit: ImageView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSetSecurityQuestionButton: Button
    private lateinit var mSetSecurityQuestionAdapter: SetSecurityQuestionAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_set_security_question, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mRecyclerView = view.findViewById(R.id.recyclerView)
        mSetSecurityQuestionButton = view.findViewById(R.id.next)
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        mSetSecurityQuestionAdapter = SetSecurityQuestionAdapter(baseActivity)
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.adapter = mSetSecurityQuestionAdapter

        mPresenter.loadQuestions()

        mSetSecurityQuestionButton.setOnClickListener {
            val confirmFragment = ConfirmFragment.newInstance()
            confirmFragment.onPinValidatedListener = this
            confirmFragment.show(baseActivity.supportFragmentManager, "confirm")
        }
    }

    override fun initView(mockQuestions: ArrayList<Question>) {
        mSetSecurityQuestionAdapter.add(mockQuestions)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    override fun onPinCorrect() {
        mSetSecurityQuestionAdapter.questions.forEach {
            val data = HashMap<String, Any>()
            data["QuestionAnswer"] = it.answer
            data["QuestionCode"] = it.question[0].toString().toInt()
            mPresenter.saveQuestions(data)
        }
    }

    override fun logout() {
        baseActivity.logout()
    }

    override fun onPinIncorrect() {
        show("The PIN entered is incorrect", true)
    }

    override fun onSaveQuestionsFailed() {
        show("An error occurred in communicating with the server", true)
    }

    override fun onSaveQuestionsSuccessful() {
        show("Security Questions successfully setup", true)
        (baseActivity as DashboardActivity).mNavController.clearStack()
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
