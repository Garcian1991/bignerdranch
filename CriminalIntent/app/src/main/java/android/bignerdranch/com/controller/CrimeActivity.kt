package android.bignerdranch.com.controller

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import java.util.*

class CrimeActivity : SingleFragmentActivity() {

    override fun createFragment() : Fragment {
        val crimeId = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID
        return CrimeFragment.newInstance(crimeId)
    }

    companion object {
        private const val EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id"

        fun newIntent(packageContext: Context, crimeID: UUID) : Intent {
            val intent = Intent(packageContext, CrimeActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeID)
            return intent
        }
    }

}
