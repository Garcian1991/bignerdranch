package android.bignerdranch.com.model

import android.bignerdranch.com.utils.SingletonHolder
import android.content.Context
import java.util.*
import kotlin.collections.ArrayList

class CrimeLab private constructor(context: Context) {

    fun addCrime(crime: Crime) {
        _crimes.add(crime)
    }

    private var _crimes = ArrayList<Crime>()
    val crimes: List<Crime>
        get() = _crimes

    operator fun get(id: UUID): Crime? {
        return crimes.firstOrNull { crime ->
            crime.id == id
        }
    }

    companion object : SingletonHolder<CrimeLab, Context>(::CrimeLab)
}