package android.bignerdranch.com.model.database

class CrimeDBSchema {
    companion object CrimeTable {

        const val NAME = "crimes"

        class Cols {
            companion object {
                const val UUID = "uuid"
                const val TITLE = "title"
                const val DATE = "date"
                const val SOLVED = "solved"
            }
        }

    }
}
