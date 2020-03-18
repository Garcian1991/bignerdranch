package android.bignerdranch.com.model

import android.bignerdranch.com.model.database.CrimeBaseHelper
import android.bignerdranch.com.model.database.CrimeCursorWrapper
import android.bignerdranch.com.model.database.CrimeDBSchema.CrimeTable
import android.bignerdranch.com.utils.SingletonHolder
import android.content.ContentValues
import android.content.Context
import java.util.*
import kotlin.collections.ArrayList

class CrimeLab private constructor(context: Context) {

    private val ctx = context.applicationContext
    private val database = CrimeBaseHelper(ctx).writableDatabase

    var crimes = ArrayList<String>()
        get() {
            val cursor = queryCrimes(null, null)
            cursor.use {
                it.moveToFirst()
                while (!it.isAfterLast) {
                    field.add(it.crime!!)
                    it.moveToNext()
                }
            }

            return field
        }

    fun addCrime(crime: Crime) {
        val values = getContentValues(crime)
        database.insert(CrimeTable.NAME, null, values)
    }

    fun updateCrime(crime: Crime) {
        val uuidString = crime.id.toString()
        val values = getContentValues(crime)
        database.update(
            CrimeTable.NAME, values,
            "${CrimeTable.Cols.UUID} = ?",
            arrayOf(uuidString)
        )
    }

    private fun queryCrimes(whereClause: String?, whereArgs: Array<String>?): CrimeCursorWrapper {
        val cursor = database.query(
            CrimeTable.NAME,
            null,
            whereClause,
            whereArgs,
            null,
            null,
            null
        )
        return CrimeCursorWrapper(cursor)
    }

    operator fun get(id: UUID): Crime? {
        val cursor = queryCrimes(
            "${CrimeTable.Cols.UUID} = ?",
            arrayOf(id.toString())
        )

        cursor.use { cur ->
            if (cur.count == 0) {
                return null
            }
            cur.moveToFirst()
            return cur.crime
        }
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