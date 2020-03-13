package android.bignerdranch.com.controller

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.bignerdranch.com.R
import android.bignerdranch.com.utils.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment() {
    private lateinit var timePicker: TimePicker

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(ARG_TIME) as Date
        val calendar = Calendar.getInstance()
            .apply { time = date }
        val (year, month, day, hour, minute) = calendar

        val dialogView = LayoutInflater.from(activity!!)
            .inflate(R.layout.dialog_time, null)

        timePicker = dialogView.findViewById(R.id.time_picker)
        with (timePicker) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.hour = hour
                this.minute = minute
            } else {
                @Suppress("DEPRECATION")
                currentHour = hour
                @Suppress("DEPRECATION")
                currentMinute = minute
            }
            setIs24HourView(true)
        }

        return AlertDialog.Builder(activity!!)
            .setView(dialogView)
            .setTitle(R.string.time_picker_title)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                @Suppress("DEPRECATION")
                val time: Date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    GregorianCalendar(year, month, day, timePicker.hour, timePicker.minute).time
                } else {
                    GregorianCalendar(
                        year,
                        month,
                        day,
                        timePicker.currentHour,
                        timePicker.currentMinute
                    ).time
                }
                sendResult(Activity.RESULT_OK, time)
            }
            .show()
    }

    @Suppress("SameParameterValue")
    private fun sendResult(resultCode: Int, date: Date) {
        if (targetFragment == null)
            return

        val intent = Intent().apply { putExtra(EXTRA_DATE, date) }
        targetFragment!!.onActivityResult(targetRequestCode, resultCode, intent)
    }

    companion object {
        private const val ARG_TIME = "Time"
        const val EXTRA_DATE = "com.bignerdranch.android.criminalintent.date"

        fun newInstance(date: Date): TimePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_TIME, date)
            }
            return TimePickerFragment().apply {
                arguments = args
            }
        }
    }
}