package android.bignerdranch.com.controller

import android.app.Dialog
import android.bignerdranch.com.R
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment() {
    private lateinit var datePicker: DatePicker

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(ARG_DATE) as Date
        val calendar = Calendar.getInstance()
            .apply { time = date }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dialogView = LayoutInflater.from(activity!!)
            .inflate(R.layout.dialog_date, null)

        datePicker = dialogView.findViewById(R.id.dialog_date_picker)
        datePicker.init(year, month, day, null)

        return AlertDialog.Builder(activity!!)
            .setView(dialogView)
            .setTitle(R.string.date_picker_title)
            .setPositiveButton(android.R.string.ok, null)
            .create()
    }

    companion object {
        const val ARG_DATE = "date"

        fun newInstance(date: Date) : DatePickerFragment {
            val args = Bundle()
            args.putSerializable(ARG_DATE, date)
            return DatePickerFragment().apply { arguments = args }
        }
    }
}