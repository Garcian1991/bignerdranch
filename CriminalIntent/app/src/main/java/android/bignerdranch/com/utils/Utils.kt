package android.bignerdranch.com.utils

import java.util.*

operator fun Calendar.component1() = get(Calendar.YEAR)
operator fun Calendar.component2() = get(Calendar.MONTH)
operator fun Calendar.component3() = get(Calendar.DAY_OF_MONTH)
operator fun Calendar.component4() = get(Calendar.HOUR_OF_DAY)
operator fun Calendar.component5() = get(Calendar.MINUTE)
operator fun Calendar.component6() = get(Calendar.SECOND)