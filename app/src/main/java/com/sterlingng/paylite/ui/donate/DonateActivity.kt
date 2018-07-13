package com.sterlingng.paylite.ui.donate

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.Group
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.confirm.ConfirmActivity
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DonateActivity : BaseActivity(), DonateMvpView, DatePickerDialog.OnDateSetListener, FilterBottomSheetFragment.OnFilterItemSelected {

    @Inject
    lateinit var mPresenter: DonateMvpContract<DonateMvpView>

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private var now = Calendar.getInstance()

    private lateinit var mScheduleRepeatSwitch: Switch
    private lateinit var mReferenceTextView: TextView
    private lateinit var mSetRepeatTextView: TextView
    private lateinit var mScheduleTextView: TextView
    private lateinit var mSetDateTextView: TextView
    private lateinit var mRepeatTextView: TextView
    private lateinit var mDateTextView: TextView
    private lateinit var mRepeatView: View
    private lateinit var mDateView: View

    private lateinit var mBeneficiaryTextInputEditText: TextInputEditText
    private lateinit var mRecipientNameTextInputLayout: TextInputLayout
    private lateinit var mBeneficiaryTextInputLayout: TextInputLayout
    private lateinit var mPasswordTextInputLayout: TextInputLayout
    private lateinit var mBeneficiaryTextView: TextView
    private lateinit var mTitleTextView: TextView
    private lateinit var mScheduleGroup: Group

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun setUp() {
        val type = intent?.getIntExtra(donateType, 0)
        when (type) {
            0 -> { //give
                mRecipientNameTextInputLayout.visibility = View.GONE
                mBeneficiaryTextInputLayout.visibility = View.GONE
                mPasswordTextInputLayout.visibility = View.GONE

                mBeneficiaryTextView.visibility = View.VISIBLE
                mScheduleGroup.visibility = View.VISIBLE
                mTitleTextView.text = "Give"
            }
            1 -> { //send money
                mRecipientNameTextInputLayout.visibility = View.VISIBLE
                mBeneficiaryTextInputLayout.visibility = View.VISIBLE
                mPasswordTextInputLayout.visibility = View.GONE

                mTitleTextView.text = intent.getStringExtra(donateTitle)
                mBeneficiaryTextView.visibility = View.GONE
                mScheduleGroup.visibility = View.VISIBLE
            }
            2 -> {
                mRecipientNameTextInputLayout.visibility = View.VISIBLE
                mBeneficiaryTextInputLayout.visibility = View.VISIBLE
                mPasswordTextInputLayout.visibility = View.VISIBLE
                mBeneficiaryTextInputLayout.hint = "Recipients Phone number"
                mBeneficiaryTextInputEditText.inputType = InputType.TYPE_CLASS_NUMBER

                mTitleTextView.text = intent.getStringExtra(donateTitle)
                mBeneficiaryTextView.visibility = View.GONE
                mScheduleGroup.visibility = View.GONE
            }
        }

        mSetDateTextView.visibility = View.GONE
        mDateTextView.visibility = View.GONE
        mDateView.visibility = View.GONE
        mSetRepeatTextView.visibility = View.GONE
        mRepeatTextView.visibility = View.GONE
        mRepeatView.visibility = View.GONE

        val simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.US)
        mDateTextView.text = simpleDateFormat.format(Date())

        exit.setOnClickListener {
            onBackPressed()
        }

        next.setOnClickListener {
            val intent = ConfirmActivity.getStartIntent(this)
            startActivity(intent)
        }

        mScheduleTextView.setOnClickListener {
            if (mScheduleRepeatSwitch.isChecked) {
                mSetDateTextView.visibility = View.VISIBLE
                mDateTextView.visibility = View.VISIBLE
                mDateView.visibility = View.VISIBLE
                mSetRepeatTextView.visibility = View.VISIBLE
                mRepeatTextView.visibility = View.VISIBLE
                mRepeatView.visibility = View.VISIBLE
            } else {
                mSetDateTextView.visibility = View.GONE
                mDateTextView.visibility = View.GONE
                mDateView.visibility = View.GONE
                mSetRepeatTextView.visibility = View.GONE
                mRepeatTextView.visibility = View.GONE
                mRepeatView.visibility = View.GONE
            }
        }

        mReferenceTextView.setOnClickListener {
            if (mScheduleRepeatSwitch.isChecked) {
                mSetDateTextView.visibility = View.VISIBLE
                mDateTextView.visibility = View.VISIBLE
                mDateView.visibility = View.VISIBLE
                mSetRepeatTextView.visibility = View.VISIBLE
                mRepeatTextView.visibility = View.VISIBLE
                mRepeatView.visibility = View.VISIBLE
            } else {
                mSetDateTextView.visibility = View.GONE
                mDateTextView.visibility = View.GONE
                mDateView.visibility = View.GONE
                mSetRepeatTextView.visibility = View.GONE
                mRepeatTextView.visibility = View.GONE
                mRepeatView.visibility = View.GONE
            }
        }

        mSetRepeatTextView.setOnClickListener {
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.title = "Repeat Payment"
            filterBottomSheetFragment.items = listOf("Never", "Daily", "Weekly", "Monthly", "Yearly")
            filterBottomSheetFragment.show(supportFragmentManager, "filter")
        }

        mSetDateTextView.setOnClickListener {
            showDatePicker("Payment Date")
            hideKeyboard()
        }

        mScheduleRepeatSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mSetDateTextView.visibility = View.VISIBLE
                mDateTextView.visibility = View.VISIBLE
                mDateView.visibility = View.VISIBLE
                mSetRepeatTextView.visibility = View.VISIBLE
                mRepeatTextView.visibility = View.VISIBLE
                mRepeatView.visibility = View.VISIBLE
            } else {
                mSetDateTextView.visibility = View.GONE
                mDateTextView.visibility = View.GONE
                mDateView.visibility = View.GONE
                mSetRepeatTextView.visibility = View.GONE
                mRepeatTextView.visibility = View.GONE
                mRepeatView.visibility = View.GONE
            }
        }
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        next = findViewById(R.id.next)

        mReferenceTextView = findViewById(R.id.ref)
        mScheduleTextView = findViewById(R.id.schedule)
        mScheduleRepeatSwitch = findViewById(R.id.schedule_switch)

        mSetDateTextView = findViewById(R.id.set_date)
        mDateTextView = findViewById(R.id.date)
        mDateView = findViewById(R.id.set_date_view)

        mSetRepeatTextView = findViewById(R.id.set_repeat)
        mRepeatTextView = findViewById(R.id.repeat)
        mRepeatView = findViewById(R.id.set_repeat_view)

        mRecipientNameTextInputLayout = findViewById(R.id.textInputLayout4)
        mBeneficiaryTextInputLayout = findViewById(R.id.textInputLayout3)
        mBeneficiaryTextInputEditText = findViewById(R.id.beneficiary)
        mPasswordTextInputLayout = findViewById(R.id.textInputLayout5)
        mBeneficiaryTextView = findViewById(R.id.beneficiary_text)
        mScheduleGroup = findViewById(R.id.schedule_group)
        mTitleTextView = findViewById(R.id.title)
    }

    private fun showDatePicker(date: String) {
        val dpd = DatePickerDialog.newInstance(this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        )
        dpd.minDate = now
        dpd.showYearPickerFirst(true)
        dpd.setTitle(date)
        dpd.isCancelable = true
        dpd.version = DatePickerDialog.Version.VERSION_2
        dpd.show(fragmentManager, "Date Picker Dialog")
    }

    override fun onDateSet(view: DatePickerDialog, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = "$year/${monthOfYear + 1}/$dayOfMonth"
        val df = SimpleDateFormat("yyyy/MM/dd", Locale.US)
        val today = df.parse(date)
        val simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.US)
        mDateTextView.text = simpleDateFormat.format(today)
    }

    override fun onFilterItemSelected(dialog: Dialog, selector: Int, s: String) {
        mRepeatTextView.text = s
        dialog.dismiss()
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        const val donateTitle = "DonateActivity.TITLE"
        const val donateType = "DonateActivity.TYPE"

        fun getStartIntent(context: Context): Intent {
            return Intent(context, DonateActivity::class.java)
        }
    }
}
