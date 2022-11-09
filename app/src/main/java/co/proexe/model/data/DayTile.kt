package co.proexe.model.data

import androidx.annotation.StringRes

class DayTile(
    val timestamp: Long,
    @StringRes
    val dayLabel: Int,
    var isSelected: Boolean = false
)