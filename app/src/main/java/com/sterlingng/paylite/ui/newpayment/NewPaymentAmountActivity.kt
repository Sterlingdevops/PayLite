package com.sterlingng.paylite.ui.newpayment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.data.manager.DataManager
import com.sterlingng.paylite.rx.SchedulerProvider
import com.sterlingng.paylite.ui.base.BaseActivity
import com.sterlingng.paylite.ui.base.BasePresenter
import com.sterlingng.paylite.ui.base.MvpPresenter
import com.sterlingng.paylite.ui.base.MvpView
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import com.tsongkha.spinnerdatepicker.DatePicker
import com.tsongkha.spinnerdatepicker.DatePickerDialog
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import io.reactivex.disposables.CompositeDisposable
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

interface NewPaymentAmountMvpContract<V : NewPaymentAmountMvpView> : MvpPresenter<V>

interface NewPaymentAmountMvpView : MvpView

class NewPaymentAmountPresenter<V : NewPaymentAmountMvpView> @Inject
constructor(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(dataManager, schedulerProvider, compositeDisposable), NewPaymentAmountMvpContract<V>

class NewPaymentAmountActivity : BaseActivity(), NewPaymentAmountMvpView, DatePickerDialog.OnDateSetListener, FilterBottomSheetFragment.OnFilterItemSelected {

    @Inject
    lateinit var mPresenter: NewPaymentAmountMvpContract<NewPaymentAmountMvpView>

    private var now = Calendar.getInstance()
    private var type: Int = -1

    private lateinit var mScheduleReferenceTextView: TextView

    private lateinit var mSetStartDateTextView: TextView
    private lateinit var mSetEndDateTextView: TextView

    private lateinit var mStartDateTextView: TextView
    private lateinit var mEndDateTextView: TextView

    private lateinit var mScheduleRepeatSwitch: Switch
    private lateinit var mSetRepeatTextView: TextView

    private lateinit var mScheduleTextView: TextView
    private lateinit var mRepeatTextView: TextView

    private lateinit var exit: ImageView
    private lateinit var next: Button

    private lateinit var mAmountReferenceTextView: TextView
    private lateinit var mAmountTextView: TextView

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_payment_amount)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        next = findViewById(R.id.next)

        mAmountReferenceTextView = findViewById(R.id.reference)
        mAmountTextView = findViewById(R.id.amount)

        mScheduleReferenceTextView = findViewById(R.id.schedule_payment_ref)
        mScheduleRepeatSwitch = findViewById(R.id.schedule_switch)
        mScheduleTextView = findViewById(R.id.schedule_payment)

        mSetStartDateTextView = findViewById(R.id.set_start_date)
        mStartDateTextView = findViewById(R.id.start_date)

        mSetEndDateTextView = findViewById(R.id.set_end_date)
        mEndDateTextView = findViewById(R.id.end_date)

        mSetRepeatTextView = findViewById(R.id.set_repeat)
        mRepeatTextView = findViewById(R.id.repeat)
    }

    override fun setUp() {
        exit.setOnClickListener {
            onBackPressed()
        }

        next.setOnClickListener {

        }

        mSetStartDateTextView.visibility = View.GONE
        mSetEndDateTextView.visibility = View.GONE
        mStartDateTextView.visibility = View.GONE
        mSetRepeatTextView.visibility = View.GONE
        mEndDateTextView.visibility = View.GONE
        mRepeatTextView.visibility = View.GONE

        val simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.US)
        mStartDateTextView.text = simpleDateFormat.format(Date())
        mEndDateTextView.text = simpleDateFormat.format(Date())

        mScheduleTextView.setOnClickListener {
            if (mScheduleRepeatSwitch.isChecked) {
                mSetStartDateTextView.visibility = View.VISIBLE
                mSetEndDateTextView.visibility = View.VISIBLE
                mStartDateTextView.visibility = View.VISIBLE
                mSetRepeatTextView.visibility = View.VISIBLE
                mEndDateTextView.visibility = View.VISIBLE
                mRepeatTextView.visibility = View.VISIBLE
            } else {
                mSetStartDateTextView.visibility = View.GONE
                mSetEndDateTextView.visibility = View.GONE
                mSetRepeatTextView.visibility = View.GONE
                mStartDateTextView.visibility = View.GONE
                mEndDateTextView.visibility = View.GONE
                mRepeatTextView.visibility = View.GONE
            }
        }

        mScheduleReferenceTextView.setOnClickListener {
            if (mScheduleRepeatSwitch.isChecked) {
                mSetStartDateTextView.visibility = View.VISIBLE
                mSetEndDateTextView.visibility = View.VISIBLE
                mStartDateTextView.visibility = View.VISIBLE
                mSetRepeatTextView.visibility = View.VISIBLE
                mEndDateTextView.visibility = View.VISIBLE
                mRepeatTextView.visibility = View.VISIBLE
            } else {
                mSetStartDateTextView.visibility = View.GONE
                mSetEndDateTextView.visibility = View.GONE
                mSetRepeatTextView.visibility = View.GONE
                mStartDateTextView.visibility = View.GONE
                mEndDateTextView.visibility = View.GONE
                mRepeatTextView.visibility = View.GONE
            }
        }

        mSetRepeatTextView.setOnClickListener {
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.title = "Repeat Payment"
            filterBottomSheetFragment.items = listOf("Never", "Daily", "Weekly", "Monthly", "Yearly")
            filterBottomSheetFragment.show(supportFragmentManager, "filter")
        }

        mSetStartDateTextView.setOnClickListener {
            showDatePicker("Payment Date", 0)
            hideKeyboard()
        }

        mSetEndDateTextView.setOnClickListener {
            showDatePicker("End Date", 1)
            hideKeyboard()
        }

        mScheduleRepeatSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mSetStartDateTextView.visibility = View.VISIBLE
                mSetEndDateTextView.visibility = View.VISIBLE
                mStartDateTextView.visibility = View.VISIBLE
                mSetRepeatTextView.visibility = View.VISIBLE
                mEndDateTextView.visibility = View.VISIBLE
                mRepeatTextView.visibility = View.VISIBLE
            } else {
                mSetStartDateTextView.visibility = View.GONE
                mSetEndDateTextView.visibility = View.GONE
                mSetRepeatTextView.visibility = View.GONE
                mStartDateTextView.visibility = View.GONE
                mEndDateTextView.visibility = View.GONE
                mRepeatTextView.visibility = View.GONE
            }
        }
    }

    private fun showDatePicker(date: String, type: Int) {
        this.type = type
        SpinnerDatePickerDialogBuilder()
                .context(this@NewPaymentAmountActivity)
                .callback(this@NewPaymentAmountActivity)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .defaultDate(now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH))
                .minDate(now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH))
                .showDaySpinner(true)
                .build()
                .show()
    }

    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = "$year/${monthOfYear + 1}/$dayOfMonth"
        val df = SimpleDateFormat("yyyy/MM/dd", Locale.US)
        val today = df.parse(date)
        val simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.US)
        when (type) {
            0 -> {
                mStartDateTextView.text = simpleDateFormat.format(today)
            }
            1 -> {
                mEndDateTextView.text = simpleDateFormat.format(today)
            }
        }
    }

    override fun onFilterItemSelected(dialog: Dialog, selector: Int, s: String) {
        mRepeatTextView.text = s
        dialog.dismiss()
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, NewPaymentAmountActivity::class.java)
        }
    }
}