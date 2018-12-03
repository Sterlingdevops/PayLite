package mr.robot.scheduleview

import android.app.Dialog
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tsongkha.spinnerdatepicker.DatePicker
import com.tsongkha.spinnerdatepicker.DatePickerDialog
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

class ScheduleView(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs),
        DatePickerDialog.OnDateSetListener, FilterBottomSheetFragment.OnFilterItemSelected {

    private var now: Calendar = Calendar.getInstance()
    private var mStartDate: String = ""
    private var mEndDate: String = ""

    private var dateFormat: String?
    private var select = Select.StartDate

    private var mFrequencyTitleTextView: TextView
    private var mStartDateTitleTextView: TextView
    private var mEndDateTitleTextView: TextView

    private var mFrequencyTextView: TextView
    private var mStartDateTextView: TextView
    private var mEndDateTextView: TextView

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ScheduleView, 0, 0)
        val frequencyTitle = a.getString(R.styleable.ScheduleView_frequencyText)
        val startDateTitle = a.getString(R.styleable.ScheduleView_startText)
        val endDateTitle = a.getString(R.styleable.ScheduleView_endText)
        dateFormat = a.getString(R.styleable.ScheduleView_dateFormat)

        LayoutInflater.from(context).inflate(R.layout.schedule_view, this, true)
        val root = this[0] as ConstraintLayout

        mStartDateTitleTextView = root[0] as TextView
        mStartDateTitleTextView.text = startDateTitle

        mEndDateTitleTextView = root[1] as TextView
        mEndDateTitleTextView.text = endDateTitle

        mFrequencyTitleTextView = root[2] as TextView
        mFrequencyTitleTextView.text = frequencyTitle

        val simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.US)

        mStartDateTextView = root[3] as TextView
        mStartDateTextView.text = simpleDateFormat.format(Date())

        mEndDateTextView = root[4] as TextView
        mEndDateTextView.text = simpleDateFormat.format(Date())

        mFrequencyTextView = root[5] as TextView

        // set end date views
        mEndDateTitleTextView.visibility = View.GONE
        mEndDateTextView.visibility = View.GONE

        mStartDateTitleTextView.setOnClickListener {
            showDatePicker(Select.StartDate)
        }

        mEndDateTitleTextView.setOnClickListener {
            showDatePicker(Select.EndDate)
        }

        mFrequencyTitleTextView.setOnClickListener {
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.title = "Repeat Payment"
            filterBottomSheetFragment.items = listOf("Never", "Daily", "Weekly", "Monthly", "Yearly")
            if (context is AppCompatActivity)
                filterBottomSheetFragment.show(context.supportFragmentManager, "Filter")
        }

        a.recycle()
    }

    val endDate: String
        get() {
            return mEndDateTextView.text.toString()
        }

    val startDate: String
        get() {
            return mStartDateTextView.text.toString()
        }

    val frequency: String
        get() {
            return mFrequencyTextView.text.toString()
        }

    private operator fun ViewGroup.get(position: Int): View = getChildAt(position)

    private fun showDatePicker(type: Select) {
        select = type
        SpinnerDatePickerDialogBuilder()
                .context(context)
                .callback(this@ScheduleView)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .defaultDate(when (select) {
                    Select.StartDate -> if (mStartDate.isEmpty()) now.get(Calendar.YEAR) else mStartDate.split("/")[0].toInt()
                    Select.EndDate -> if (mEndDate.isEmpty()) now.get(Calendar.YEAR) else mEndDate.split("/")[0].toInt()
                }, when (select) {
                    Select.StartDate -> if (mStartDate.isEmpty()) now.get(Calendar.MONTH) else mStartDate.split("/")[1].toInt() - 1
                    Select.EndDate -> if (mEndDate.isEmpty()) now.get(Calendar.MONTH) else mEndDate.split("/")[1].toInt() - 1
                }, when (select) {
                    Select.StartDate -> if (mStartDate.isEmpty()) now.get(Calendar.DAY_OF_MONTH) else mStartDate.split("/")[2].toInt()
                    Select.EndDate -> if (mEndDate.isEmpty()) now.get(Calendar.DAY_OF_MONTH) else mEndDate.split("/")[2].toInt()
                })
                .minDate(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
                .showDaySpinner(true)
                .build()
                .show()
    }

    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = "$year/${monthOfYear + 1}/$dayOfMonth"
        val df = SimpleDateFormat("yyyy/MM/dd", Locale.US)
        val today = df.parse(date)
        val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.US)
        when (select) {
            Select.StartDate -> {
                mStartDateTextView.text = simpleDateFormat.format(today); mStartDate = date
            }
            Select.EndDate -> {
                mEndDateTextView.text = simpleDateFormat.format(today); mEndDate = date
            }
        }
    }

    override fun onFilterItemSelected(dialog: Dialog, s: String) {
        mFrequencyTextView.text = s
        when (s.toLowerCase()) {
            "never" ->{ mEndDateTitleTextView.visibility = View.GONE; mEndDateTextView.visibility = View.GONE; }
            "daily" -> { mEndDateTitleTextView.visibility = View.VISIBLE; mEndDateTextView.visibility = View.VISIBLE; }
            "weekly" -> { mEndDateTitleTextView.visibility = View.VISIBLE; mEndDateTextView.visibility = View.VISIBLE; }
            "monthly" -> { mEndDateTitleTextView.visibility = View.VISIBLE; mEndDateTextView.visibility = View.VISIBLE; }
            "yearly" -> { mEndDateTitleTextView.visibility = View.VISIBLE; mEndDateTextView.visibility = View.VISIBLE; }
        }
        dialog.dismiss()
    }

    enum class Select {
        StartDate, EndDate
    }
}