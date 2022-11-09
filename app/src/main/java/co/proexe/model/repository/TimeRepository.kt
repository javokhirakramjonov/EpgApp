package co.proexe.model.repository

import co.proexe.R
import co.proexe.model.data.DayTile
import java.util.*
import kotlinx.coroutines.delay
import javax.inject.Inject

class TimeRepository @Inject constructor() {

    suspend fun getDayTiles() : List<DayTile> {
        delay(1000)
        return listOf(
            DayTile(addDaysToCurrentCalendar(-2), R.string.day_before_yesterday),
            DayTile(addDaysToCurrentCalendar(-1), R.string.yesterday),
            DayTile(addDaysToCurrentCalendar(0), R.string.today, true),
            DayTile(addDaysToCurrentCalendar(1), R.string.tomorrow),
            DayTile(addDaysToCurrentCalendar(2), R.string.day_after_tomorrow)
        )
    }

    private fun addDaysToCurrentCalendar(amount: Int): Long {
        val cal = getCurrentCalendar()
        cal.add(Calendar.DAY_OF_YEAR, amount)
        return cal.time.time
    }

    private fun getCurrentCalendar() = Calendar.getInstance()
}