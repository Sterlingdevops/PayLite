package com.sterlingng.paylite.ui.donate

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

    private lateinit var setDateTextView: TextView
    private lateinit var setRepeatTextView: TextView
    private lateinit var repeatTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var dateView: View
    private lateinit var repeatView: View
    private lateinit var switch: Switch
    private lateinit var schedule: TextView
    private lateinit var ref: TextView

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donate)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        next = findViewById(R.id.next)

        ref = findViewById(R.id.ref)
        schedule = findViewById(R.id.schedule)
        switch = findViewById(R.id.schedule_switch)

        setDateTextView = findViewById(R.id.set_date)
        dateTextView = findViewById(R.id.date)
        dateView = findViewById(R.id.set_date_view)

        setRepeatTextView = findViewById(R.id.set_repeat)
        repeatTextView = findViewById(R.id.repeat)
        repeatView = findViewById(R.id.set_repeat_view)
    }

    override fun setUp() {
        setDateTextView.visibility = View.GONE
        dateTextView.visibility = View.GONE
        dateView.visibility = View.GONE
        setRepeatTextView.visibility = View.GONE
        repeatTextView.visibility = View.GONE
        repeatView.visibility = View.GONE

        val simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.US)
        dateTextView.text = simpleDateFormat.format(Date())

        exit.setOnClickListener {
            onBackPressed()
        }

        next.setOnClickListener {
            val intent = ConfirmActivity.getStartIntent(this)
            startActivity(intent)
        }

        schedule.setOnClickListener {
            if (switch.isChecked) {
                setDateTextView.visibility = View.VISIBLE
                dateTextView.visibility = View.VISIBLE
                dateView.visibility = View.VISIBLE
                setRepeatTextView.visibility = View.VISIBLE
                repeatTextView.visibility = View.VISIBLE
                repeatView.visibility = View.VISIBLE
            } else {
                setDateTextView.visibility = View.GONE
                dateTextView.visibility = View.GONE
                dateView.visibility = View.GONE
                setRepeatTextView.visibility = View.GONE
                repeatTextView.visibility = View.GONE
                repeatView.visibility = View.GONE
            }
        }

        ref.setOnClickListener {
            if (switch.isChecked) {
                setDateTextView.visibility = View.VISIBLE
                dateTextView.visibility = View.VISIBLE
                dateView.visibility = View.VISIBLE
                setRepeatTextView.visibility = View.VISIBLE
                repeatTextView.visibility = View.VISIBLE
                repeatView.visibility = View.VISIBLE
            } else {
                setDateTextView.visibility = View.GONE
                dateTextView.visibility = View.GONE
                dateView.visibility = View.GONE
                setRepeatTextView.visibility = View.GONE
                repeatTextView.visibility = View.GONE
                repeatView.visibility = View.GONE
            }
        }

        setRepeatTextView.setOnClickListener {
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.title = "Repeat Payment"
            filterBottomSheetFragment.items = listOf("Never", "Daily", "Weekly", "Monthly", "Yearly")
            filterBottomSheetFragment.show(supportFragmentManager, "filter")
        }

        setDateTextView.setOnClickListener {
            showDatePicker("Payment Date")
            hideKeyboard()
        }

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setDateTextView.visibility = View.VISIBLE
                dateTextView.visibility = View.VISIBLE
                dateView.visibility = View.VISIBLE
                setRepeatTextView.visibility = View.VISIBLE
                repeatTextView.visibility = View.VISIBLE
                repeatView.visibility = View.VISIBLE
            } else {
                setDateTextView.visibility = View.GONE
                dateTextView.visibility = View.GONE
                dateView.visibility = View.GONE
                setRepeatTextView.visibility = View.GONE
                repeatTextView.visibility = View.GONE
                repeatView.visibility = View.GONE
            }
        }
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
        dateTextView.text = simpleDateFormat.format(today)
    }

    override fun onFilterItemSelected(dialog: Dialog, selector: Int, s: String) {
        repeatTextView.text = s
        dialog.dismiss()
    }

    override fun recyclerViewListClicked(v: View, position: Int) {

    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, DonateActivity::class.java)
        }
    }
}
