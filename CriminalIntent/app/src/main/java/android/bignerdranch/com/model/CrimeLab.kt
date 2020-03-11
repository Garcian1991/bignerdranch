package android.bignerdranch.com.model

import android.bignerdranch.com.utils.SingletonHolder
import android.content.Context
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

class CrimeLab private constructor(context: Context) {

    private var _crimes = LinkedHashMap<UUID, Crime>()
    val crimes: List<Crime>
        get() = ArrayList<Crime>(_crimes.values)

    init {
        for (i in 0..99) {
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.solved = i % 2 == 0 // Every other one
            _crimes[crime.id] = crime
        }
    }

    operator fun get(id: UUID): Crime? {
        return if (_crimes.containsKey(id))
            _crimes[id]
        else
            null
    }

    companion object : SingletonHolder<CrimeLab, Context>(::CrimeLab)
}