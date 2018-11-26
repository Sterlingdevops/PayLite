package com.sterlingng.paylite.ui.editprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.User
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.views.LargeLabelEditText
import javax.inject.Inject

class EditProfileFragment : BaseFragment(), EditMvpView {

    @Inject
    lateinit var mPresenter: EditMvpContract<EditMvpView>

    private lateinit var exit: ImageView
    private lateinit var mDoneTextView: TextView
    private lateinit var mDobTextView: LargeLabelEditText
    private lateinit var mGenderTextView: LargeLabelEditText
    private lateinit var mLastNameTextView: LargeLabelEditText
    private lateinit var mFirstNameTextView: LargeLabelEditText
    private lateinit var mEmailNameTextView: LargeLabelEditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
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

        mDoneTextView.setOnClickListener {
            val data = HashMap<String, Any>()
            data["FirstName"] = mFirstNameTextView.text
            data["LastName"] = mLastNameTextView.text
            data["Email"] = mEmailNameTextView.text
            data["Latitude"] = "1"
            data["Longitude"] = "1"
            mPresenter.updateUserDetails(data)
        }
    }

    override fun onUpdateUserDetailsSuccessful() {
        show("Account details updated", true)
        baseActivity.onBackPressed()
    }

    override fun logout() {
        baseActivity.logout()
    }

    override fun onUpdateUserDetailsFailed() {
        show("Failed to update user details", true)
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        mDobTextView = view.findViewById(R.id.dob)
        mDoneTextView = view.findViewById(R.id.done)
        mGenderTextView = view.findViewById(R.id.gender)
        mEmailNameTextView = view.findViewById(R.id.email)
        mLastNameTextView = view.findViewById(R.id.last_name)
        mFirstNameTextView = view.findViewById(R.id.first_name)
    }

    override fun initView(user: User) {
        mFirstNameTextView.text = user.firstName
        mLastNameTextView.text = user.lastName
        mEmailNameTextView.text = user.email
        mGenderTextView.text = user.gender
        mDobTextView.text = user.dob
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {
        fun newInstance(): EditProfileFragment {
            val fragment = EditProfileFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
