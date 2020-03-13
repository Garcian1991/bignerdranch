package android.bignerdranch.com.controller

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.bignerdranch.com.R
import android.bignerdranch.com.utils.*
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment() {
    private lateinit var datePicker: DatePicker

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(ARG_DATE) as Date
        val calendar = Calendar.getInstance()
            .apply { time = date }
        val (year, month, day, hour, minute) = calendar

        val dialogView = LayoutInflater.from(activity!!)
            .inflate(R.layout.dialog_date, null)

        datePicker = dialogView.findViewById(R.id.dialog_date_picker)
        datePicker.init(year, month, day, null)

        return AlertDialog.Builder(activity!!)
            .setView(dialogView)
            .setTitle(R.string.date_picker_title)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                @Suppress("NAME_SHADOWING")
                val date = GregorianCalendar(
                    datePicker.year,
                    datePicker.month,
                    datePicker.dayOfMonth,
                    hour,
                    minute
                ).time
                sendResult(Activity.RESULT_OK, date)
            }
            .create()
    }

    @Suppress("SameParameterValue")
    private fun sendResult(resultCode: Int, date: Date) {
        if (targetFragment == null)
            return

        val intent = Intent().apply { putExtra(EXTRA_DATE, date) }
        targetFragment!!.onActivityResult(targetRequestCode, resultCode, intent)
    }

    companion object {
        private const val ARG_DATE = "date"
        const val EXTRA_DATE = "com.bignerdranch.android.criminalintent.date"

        fun newInstance(date: Date): DatePickerFragment {
            val args = Bundle()
            args.putSerializable(ARG_DATE, date)
            return DatePickerFragment().apply { arguments = args }
        }
    }
}