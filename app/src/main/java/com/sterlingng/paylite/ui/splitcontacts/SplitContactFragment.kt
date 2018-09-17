package com.sterlingng.paylite.ui.splitcontacts

import android.Manifest
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.model.ChosenContact
import com.sterlingng.paylite.data.model.ContactItem
import com.sterlingng.paylite.rx.EventBus
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.contacts.ContactsFragment
import com.sterlingng.paylite.ui.dashboard.DashboardActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplitContactFragment : BaseFragment(), SplitContactMvpView {

    @Inject
    lateinit var mPresenter: SplitContactMvpContract<SplitContactMvpView>

    @Inject
    lateinit var eventBus: EventBus

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
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.adapter = mSplitContactsAdapter

        mSplitContactsAdapter.add(ContactItem())
        mSplitContactsAdapter.add(ContactItem())

        mAddContactTextView.setOnClickListener {
            mSplitContactsAdapter.add(ContactItem())
        }

        next.setOnClickListener {

        }

        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        eventBus.observe(ChosenContact::class.java)
                .delay(1L, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mSplitContactsAdapter.contacts = it.contacts
                    mSplitContactsAdapter.notifyDataSetChanged()
                }
    }

    override fun recyclerViewListClicked(v: View, position: Int) {
        Dexter.withActivity(baseActivity)
                .withPermissions(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                        token?.continuePermissionRequest()
                    }

                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        val builder = FragNavTransactionOptions.newBuilder()
                        builder.allowStateLoss = false
                        (baseActivity as DashboardActivity).mNavController
                                .pushFragment(ContactsFragment.newInstance("Contacts", position, mSplitContactsAdapter.contacts), builder.build())
                    }
                }).check()
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