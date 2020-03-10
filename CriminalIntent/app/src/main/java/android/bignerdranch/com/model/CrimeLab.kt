package android.bignerdranch.com.model

import android.bignerdranch.com.utils.SingletonHolder
import android.content.Context
import java.util.*
import kotlin.collections.ArrayList

class CrimeLab private constructor(context: Context) {

    private var _crimes = ArrayList<Crime>()
    val crimes: List<Crime>
        get() = _crimes

    init {
        for (i in 0..99) {
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.solved = i % 2 == 0 // Every other one
            _crimes.add(crime)
        }
    }

    operator fun get(id: UUID): Crime? {
        return crimes.firstOrNull { crime ->
            crime.id == id
        }
    }

    companion object : SingletonHolder<CrimeLab, Context>(::CrimeLab)
}