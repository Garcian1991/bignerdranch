package android.bignerdranch.com.controller

import android.bignerdranch.com.R
import android.bignerdranch.com.model.Crime
import android.bignerdranch.com.model.CrimeLab
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import java.util.*

class CrimePagerActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var crimes: List<Crime>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_pager)

        val crimeId = intent.getSerializableExtra(EXTRA_CRIME_ID)

        viewPager = findViewById(R.id.crime_view_pager)
        crimes = CrimeLab.getInstance(this).crimes
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return crimes.size
            }

            override fun createFragment(position: Int): Fragment {
                val crime = crimes[position]
                return CrimeFragment.newInstance(crime.id)
            }
        }

        viewPager.currentItem = crimes.indexOfFirst { crime ->  crime.id == crimeId }
            .let { index -> if (index > 0) index else 0 }

    }

    companion object {
        private const val EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id"

        fun newIntent(packageContext: Context, crimeID: UUID) : Intent {
            val intent = Intent(packageContext, CrimePagerActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeID)
            return intent
        }
    }
}