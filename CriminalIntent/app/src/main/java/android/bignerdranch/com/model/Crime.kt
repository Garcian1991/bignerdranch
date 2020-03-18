package android.bignerdranch.com.model

import java.util.*

data class Crime (
    var title: String = "",
    var date: Date = Date(),
    var solved: Boolean = false,
    val id: UUID = UUID.randomUUID()
)