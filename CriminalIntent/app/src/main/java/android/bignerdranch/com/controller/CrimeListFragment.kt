package android.bignerdranch.com.controller

import android.bignerdranch.com.R
import android.bignerdranch.com.model.Crime
import android.bignerdranch.com.model.CrimeLab
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CrimeListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView
    private lateinit var crimeAdapter: CrimeAdapter
    private var subtitleVisible = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycle_view)
        crimeRecyclerView.layoutManager = LinearLayoutManager(activity)
        savedInstanceState?.let { subtitleVisible = it.getBoolean(SAVED_SUBTITLE_VISIBLE, false)}
        updateUI()
        return view
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, subtitleVisible)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_crime_list, menu)

        val subtitleItem = menu.findItem(R.id.show_subtitle)
        if (subtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle)
        } else {
            subtitleItem.setTitle(R.string.show_subtitle)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.new_crime -> {
                val crime = Crime()
                CrimeLab.getInstance(activity!!).addCrime(crime)
                val intent = CrimePagerActivity.newIntent(activity!!, crime.id)
                startActivity(intent)
                true
            }
            R.id.show_subtitle -> {
                subtitleVisible = !subtitleVisible
                activity!!.invalidateOptionsMenu()
                updateSubtitle()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun updateSubtitle() {
        val crimeCount = CrimeLab.getInstance(activity!!).crimes.size
        val subtitle =
            if (!subtitleVisible)
                null
            else
                getString(R.string.subtitle_format, crimeCount)

        val activity = activity!! as AppCompatActivity
        activity.supportActionBar!!.subtitle = subtitle
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
        updateSubtitle()
    }

    private inner class CrimeAdapter(private val crimes: List<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {

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
        View.OnClickListener {
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
            dateTextView.text = SimpleDateFormat(pattern, Locale.US).format(crime.date)
            solvedImageView.visibility = if (crime.solved) View.VISIBLE else View.GONE
        }

        override fun onClick(view: View?) {
            val intent = CrimePagerActivity.newIntent(activity!!, crime.id)
            startActivity(intent)
        }
    }

    companion object {
        private const val SAVED_SUBTITLE_VISIBLE = "subtitle"
    }
}