package android.bignerdranch.com.controller

import android.app.Activity
import android.bignerdranch.com.R
import android.bignerdranch.com.model.Crime
import android.bignerdranch.com.model.CrimeLab
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.*

class CrimeFragment : Fragment() {
    lateinit var crime: Crime
    private lateinit var crimeTitleTextView: TextView
    private lateinit var crimeDateButton: Button
    private lateinit var crimeTimeButton: Button
    private lateinit var crimeSolvedCheckBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val crimeId = arguments!!.getSerializable(ARG_CRIME_ID) as UUID
        crime = CrimeLab.getInstance(activity!!)[crimeId]!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime, container, false)
        crimeTitleTextView = view.findViewById(R.id.crimeTitle) as TextView
        crimeTitleTextView.text = crime.title
        crimeTitleTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                crime.title = s.toString()
            }
        })

        crimeDateButton = view.findViewById(R.id.crime_date) as Button
        crimeTimeButton = view.findViewById(R.id.crime_time) as Button
        updateDateAndTime()

        crimeDateButton.setOnClickListener {
            val dialog = DatePickerFragment.newInstance(crime.date)
            with(dialog) {
                setTargetFragment(this@CrimeFragment, REQUEST_DATE)
                show(this@CrimeFragment.fragmentManager!!, DIALOG_DATE)
            }
        }

        crimeTimeButton.setOnClickListener {
            val dialog = TimePickerFragment.newInstance(crime.date)
            with(dialog) {
                setTargetFragment(this@CrimeFragment, REQUEST_DATE)
                show(this@CrimeFragment.fragmentManager!!, DIALOG_TIME)
            }
        }

        crimeSolvedCheckBox = view.findViewById(R.id.crime_solved) as CheckBox
        crimeSolvedCheckBox.isChecked = crime.solved
        crimeSolvedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            crime.solved = isChecked
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK)
            return
        when (requestCode) {
            REQUEST_DATE -> {
                crime.date = data?.getSerializableExtra(DatePickerFragment.EXTRA_DATE) as Date
                updateDateAndTime()
            }
        }
    }

    private fun updateDateAndTime() {
        crimeDateButton.text = SimpleDateFormat("EEEE, MMM d, yyyy", Locale.US).format(crime.date)
        crimeTimeButton.text = SimpleDateFormat("HH:mm", Locale.US).format(crime.date)
    }

    companion object {
        const val ARG_CRIME_ID = "crime_id"
        const val DIALOG_DATE = "DialogDate"
        const val DIALOG_TIME = "DialogTime"
        const val REQUEST_DATE = 0

        fun newInstance(crimeId: UUID): CrimeFragment {
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeId)
            return CrimeFragment().apply { arguments = args }
        }
    }
}
