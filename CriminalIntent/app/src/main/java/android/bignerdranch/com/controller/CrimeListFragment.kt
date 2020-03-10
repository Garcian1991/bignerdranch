package android.bignerdranch.com.controller

import android.bignerdranch.com.R
import android.bignerdranch.com.model.Crime
import android.bignerdranch.com.model.CrimeLab
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat
import java.text.SimpleDateFormat

class CrimeListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView
    private lateinit var crimeAdapter: CrimeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycle_view)
        crimeRecyclerView.layoutManager = LinearLayoutManager(activity)

        return view
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        val crimeLab = CrimeLab.getInstance(activity!!)
        val crimes = crimeLab.crimes

        if (::crimeAdapter.isInitialized) {
            crimeAdapter.notifyDataSetChanged()
        } else {
            crimeAdapter = CrimeAdapter(crimes)
            crimeRecyclerView.adapter = crimeAdapter
        }
    }

    private inner class CrimeAdapter(private val crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            return CrimeHolder(activity!!.layoutInflater, parent)
        }

        override fun getItemCount(): Int {
            return crimes.size
        }

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.bind(crime)
        }
    }

    private inner class CrimeHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        inflater.inflate(R.layout.list_item_crime, parent, false)
    ),
        View.OnClickListener
    {
        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        private val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)
        private lateinit var crime: Crime

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime) {
            this@CrimeHolder.crime = crime
            titleTextView.text = crime.title
            val pattern = """EEEE, MMM d, yyyy."""
            dateTextView.text = SimpleDateFormat(pattern).format(crime.date)
            solvedImageView.visibility = if (crime.solved) View.VISIBLE else View.GONE
        }

        override fun onClick(view: View?) {
            val intent = CrimeActivity.newIntent(activity!!, crime.id)
            startActivity(intent)
        }
    }
}