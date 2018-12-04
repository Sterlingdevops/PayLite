package com.sterlingng.paylite.ui.signup.name


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseFragment
import com.sterlingng.paylite.ui.filter.FilterBottomSheetFragment
import com.sterlingng.paylite.ui.signup.SignUpActivity
import com.sterlingng.paylite.utils.AppConstants.EVENT_FOUR
import com.sterlingng.paylite.utils.AppConstants.EVENT_SEVEN
import com.sterlingng.paylite.utils.AppConstants.ON_BOARDING
import com.sterlingng.paylite.utils.AppUtils.createEvent
import com.sterlingng.paylite.utils.AppUtils.createId
import com.sterlingng.paylite.utils.OnChildDidClickNext
import com.sterlingng.views.ClickToSelectEditText
import com.tsongkha.spinnerdatepicker.DatePicker
import com.tsongkha.spinnerdatepicker.DatePickerDialog
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NameFragment : BaseFragment(), NameMvpView, DatePickerDialog.OnDateSetListener, FilterBottomSheetFragment.OnFilterItemSelected {

    @Inject
    lateinit var mPresenter: NameMvpContract<NameMvpView>

    private lateinit var mGenderEditText: ClickToSelectEditText<String>
    private lateinit var mFirstNameEditText: EditText
    private lateinit var mLastNameEditText: EditText
    private lateinit var mDobEditText: TextView
    private lateinit var exit: ImageView
    private lateinit var next: Button

    private var now: Calendar = Calendar.getInstance()

    lateinit var mDidClickNext: OnChildDidClickNext

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_name, container, false)
        val component = activityComponent
        component.inject(this)
        mPresenter.onAttach(this)
        return view
    }

    override fun bindViews(view: View) {
        exit = view.findViewById(R.id.exit)
        next = view.findViewById(R.id.next_name)
        mDobEditText = view.findViewById(R.id.dob)
        mGenderEditText = view.findViewById(R.id.gender)
        mLastNameEditText = view.findViewById(R.id.last_name)
        mFirstNameEditText = view.findViewById(R.id.first_name)
    }

    override fun setUp(view: View) {
        exit.setOnClickListener {
            baseActivity.onBackPressed()
        }

        next.setOnClickListener {
            if (mFirstNameEditText.text.isEmpty() ||
                    mLastNameEditText.text.isEmpty() ||
                    mDobEditText.text.isEmpty() ||
                    mGenderEditText.text!!.isEmpty()) {
                show("All the fields are required", true)
                return@setOnClickListener
            }

            createEvent(
                    baseActivity,
                    ON_BOARDING,
                    EVENT_FOUR,
                    EVENT_SEVEN,
                    createId(),
                    (baseActivity as SignUpActivity).latitude,
                    (baseActivity as SignUpActivity).longitude,
                    javaClass.simpleName,
                    ""
            )

            mDidClickNext.onNextClick(arguments?.getInt(INDEX)!!,
                    listOf(mFirstNameEditText.text.toString(),
                            mLastNameEditText.text.toString(),
                            mDobEditText.text.toString(),
                            mGenderEditText.text.toString()
                    )
            )
            hideKeyboard()
        }

        mDobEditText.setOnClickListener {
            showDatePicker()
        }

        mGenderEditText.setOnClickListener {
            val filterBottomSheetFragment = FilterBottomSheetFragment.newInstance()
            filterBottomSheetFragment.onFilterItemSelectedListener = this
            filterBottomSheetFragment.selector = 1
            filterBottomSheetFragment.title = "Gender"
            filterBottomSheetFragment.items = listOf("Male", "Female")
            filterBottomSheetFragment.show(childFragmentManager, "filter")
        }
    }

    private fun showDatePicker() {
        SpinnerDatePickerDialogBuilder()
                .context(baseActivity)
                .callback(this@NameFragment)
                .spinnerTheme(R.style.NumberPickerStyle)
                .showTitle(true)
                .defaultDate(now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH))
                .maxDate(now.get(Calendar.YEAR),
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
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        mDobEditText.setText(simpleDateFormat.format(today))
    }

    override fun onFilterItemSelected(dialog: Dialog, selector: Int, s: String) {
        mGenderEditText.setText(s)
        dialog.dismiss()
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    companion object {

        private const val INDEX = "NameFragment.INDEX"

        fun newInstance(index: Int): NameFragment {
            val fragment = NameFragment()
            val args = Bundle()
            args.putInt(INDEX, index)
            fragment.arguments = args
            return fragment
        }
    }
}
