package com.sterlingng.paylite.ui.banktransfers.newbanktransfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import javax.inject.Inject

class NewBankTransferFragment : BaseFragment(), NewBankTransferMvpView {

    @Inject
    lateinit var mPresenter: NewBankTransferMvpContract<NewBankTransferMvpView>

    private lateinit var mSaveRecipientSwitch: Switch
    private lateinit var mSaveRecipientTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_bank_transfer, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun setUp(view: View) {
        mSaveRecipientTextView.setOnClickListener {
            mSaveRecipientSwitch.toggle()
        }
    }

    override fun bindViews(view: View) {
        mSaveRecipientTextView = view.findViewById(R.id.save_recipient)
        mSaveRecipientSwitch = view.findViewById(R.id.save_recipient_switch)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        fun newInstance(): NewBankTransferFragment {
            val fragment = NewBankTransferFragment()
            val args = Bundle()

            fragment.arguments = args
            return fragment
        }
    }
}
