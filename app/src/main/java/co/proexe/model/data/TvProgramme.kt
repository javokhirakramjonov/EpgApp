package co.proexe.model.data

import co.proexe.model.data.TvProgrammeCategory
import java.util.*

data class TvProgramme(
    val title: String,
    val imageUrl: String,
    val type: String,
    val category: TvProgrammeCategory,
    val isFavourite: Boolean,
    val startTime: Date,
    val endTime: Date,
    val progressPercent: Int
)