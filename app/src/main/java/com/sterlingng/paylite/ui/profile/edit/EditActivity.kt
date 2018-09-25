package com.sterlingng.paylite.ui.profile.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.i18n.phonenumbers.AsYouTypeFormatter
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.lamudi.phonefield.PhoneInputLayout
import com.sterlingng.paylite.R
import com.sterlingng.paylite.ui.base.BaseActivity
import com.tsongkha.spinnerdatepicker.DatePicker
import com.tsongkha.spinnerdatepicker.DatePickerDialog
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EditActivity : BaseActivity(), EditMvpView, DatePickerDialog.OnDateSetListener {

    @Inject
    lateinit var mPresenter: EditMvpContract<EditMvpView>

    private lateinit var exit: ImageView
    private lateinit var dob: EditText
    private var now = Calendar.getInstance()
    private lateinit var doneTextView: TextView
    private lateinit var phoneInputLayout: PhoneInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        activityComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun setUp() {
        phoneInputLayout.setHint(R.string.phone)
        phoneInputLayout.setDefaultCountry("NG")
        phoneInputLayout.editText?.addTextChangedListener(PhoneNumberTextWatcher())
        phoneInputLayout.editText?.setRawInputType(InputType.TYPE_CLASS_NUMBER)

        exit.setOnClickListener {
            onBackPressed()
        }

        dob.setOnClickListener {
            showDatePicker("Date of Birth")
            hideKeyboard()
        }
    }

    override fun bindViews() {
        exit = findViewById(R.id.exit)
        dob = findViewById(R.id.input_dob)
        doneTextView = findViewById(R.id.done)
        phoneInputLayout = findViewById(R.id.phone_input_layout)
    }

    override fun recyclerViewItemClicked(v: View, position: Int) {

    }

    private fun showDatePicker(date: String) {
        SpinnerDatePickerDialogBuilder()
                .context(this@EditActivity)
                .callback(this@EditActivity)
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
        dob.setText(simpleDateFormat.format(today))
    }

    companion object {

        fun getStartIntent(context: Context): Intent {
            return Intent(context, EditActivity::class.java)
        }
    }

    private val phoneUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()
    private val asYouTypeFormatter: AsYouTypeFormatter = phoneUtil.getAsYouTypeFormatter("US")

    inner class PhoneNumberTextWatcher : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (!phoneInputLayout.editText?.text?.contains("+")!!) {
                asYouTypeFormatter.clear()
                asYouTypeFormatter.inputDigit('+')
                phoneInputLayout.editText.text.forEach { it ->
                    phoneInputLayout.editText.setText(asYouTypeFormatter.inputDigit(it))
                }
                phoneInputLayout.editText?.setSelection(phoneInputLayout.editText?.length()!!)
            }

            if (phoneInputLayout.isValid) {
                phoneInputLayout.setError(null)
            } else {
                phoneInputLayout.setError(getString(R.string.invalid_phone_number))
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    }
}
