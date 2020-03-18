package android.bignerdranch.com.model.database

import android.bignerdranch.com.model.Crime
import android.bignerdranch.com.model.database.CrimeDBSchema.CrimeTable
import android.database.Cursor
import android.database.CursorWrapper
import java.util.*

class CrimeCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {
    val crime: Crime?
        get() {
            val uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID))
            val title = getString(getColumnIndex(CrimeTable.Cols.TITLE))
            val date = getLong(getColumnIndex(CrimeTable.Cols.DATE))
            val solved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED))

            return Crime(title, Date(date), (solved != 0), UUID.fromString(uuidString))
        }
}