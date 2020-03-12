package android.bignerdranch.com.controller

import android.bignerdranch.com.R
import android.bignerdranch.com.model.Crime
import android.bignerdranch.com.model.CrimeLab
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
import java.util.*

class CrimeFragment : Fragment() {
    lateinit var crime: Crime
    private lateinit var crimeTitleTextView: TextView
    private lateinit var crimeDateButton: Button
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
        crimeDateButton.apply {
            text = crime.date.toString()
            setOnClickListener {
                val dialog = DatePickerFragment.newInstance(crime.date)
                dialog.show(fragmentManager!!, DIALOG_DATE)
                    .also { setTargetFragment(this@CrimeFragment, REQUEST_DATE) }
            }
        }

        crimeSolvedCheckBox = view.findViewById(R.id.crime_solved) as CheckBox
        crimeSolvedCheckBox.isChecked = crime.solved
        crimeSolvedCheckBox.setOnCheckedChangeListener { _, isChecked ->
            crime.solved = isChecked
        }

        return view
    }

    companion object {
        const val ARG_CRIME_ID = "crime_id"
        const val DIALOG_DATE = "DialogDate"
        const val REQUEST_DATE = 0

        fun newInstance(crimeId: UUID) : CrimeFragment {
            val args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeId)
            return CrimeFragment().apply { arguments = args }
        }
    }
}
