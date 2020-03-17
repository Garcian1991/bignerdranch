package android.bignerdranch.com.model

import android.bignerdranch.com.model.database.CrimeBaseHelper
import android.bignerdranch.com.model.database.CrimeDBSchema
import android.bignerdranch.com.model.database.CrimeDBSchema.CrimeTable
import android.bignerdranch.com.utils.SingletonHolder
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.util.*
import kotlin.collections.ArrayList

class CrimeLab private constructor(context: Context) {

    private val ctx = context.applicationContext
    private val database = CrimeBaseHelper(ctx).writableDatabase

    val crimes: List<Crime>
        get() = ArrayList()

    fun addCrime(crime: Crime) {
        val values = getContentValues(crime)
        database.insert(CrimeTable.NAME, null, values)
    }

    fun updateCrime(crime: Crime) {
        val uuidString = crime.id.toString()

    }

    operator fun get(id: UUID): Crime? {
        return null
    }

    companion object : SingletonHolder<CrimeLab, Context>(::CrimeLab) {
        private fun getContentValues(crime: Crime): ContentValues =
            ContentValues().apply {
                put(CrimeTable.Cols.UUID, crime.id.toString())
                put(CrimeTable.Cols.TITLE, crime.title)
                put(CrimeTable.Cols.DATE, crime.date.toString())
                put(CrimeTable.Cols.SOLVED, if (crime.solved) 1 else 0)
            }
    }
}