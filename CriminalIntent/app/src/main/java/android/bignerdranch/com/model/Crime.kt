package android.bignerdranch.com.model

import java.util.*

data class Crime(
    var title: String = "",
    var solved: Boolean = false,
    var date: Date = Date(),
    val id: UUID = UUID.randomUUID()
)