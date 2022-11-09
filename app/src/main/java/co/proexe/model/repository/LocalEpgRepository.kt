package co.proexe.model.repository

import co.proexe.model.data.TvProgramme
import co.proexe.model.data.TvProgrammeCategory
import java.util.*
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class LocalEpgRepository @Inject constructor() {

    suspend fun getProgrammesForDateTime(
        dateTime: Date,
        amount: Int = PROGRAMMES_LIST_SIZE
    ): List<TvProgramme> {
        delay(100)
        return generateProgrammes(dateTime, amount)
    }

    private fun generateProgrammes(dateTime: Date, amount: Int = PROGRAMMES_LIST_SIZE) =
        (1..amount)
            .map { index ->
                index to getStartEndTimes(dateTime, index)
            }
            .map { (index, startEndTimePair) ->
                TvProgramme(
                    title = TITLES[index % TITLES.size],
                    imageUrl = IMAGE_URLS[index % IMAGE_URLS.size],
                    type = TYPES[index % TYPES.size],
                    category = TvProgrammeCategory.values()[index % TvProgrammeCategory.values().size],
                    isFavourite = getIsFavouriteBasedOnIndex(index),
                    startTime = startEndTimePair.first,
                    endTime = startEndTimePair.second,
                    progressPercent = computeProgress(startEndTimePair, dateTime)
                )
            }

    private fun getIsFavouriteBasedOnIndex(index: Int) = index % 2 == 0

    private fun computeProgress(startEndTimePair: Pair<Date, Date>, currentDateTime: Date) =
        currentDateTime.time
            .minus(startEndTimePair.first.time)
            .div(
                startEndTimePair.second.time
                    .minus(startEndTimePair.first.time)
                    .toDouble()
            )
            .times(100)
            .toInt()

    private fun getStartEndTimes(dateTime: Date, index: Int) =
        createStartTime(dateTime, index) to createEndTime(dateTime, index)

    private fun createStartTime(dateTime: Date, index: Int) =
        createCalendar(dateTime)
            .apply {
                add(Calendar.HOUR, -getHoursBasedOnIndex(index))
                add(Calendar.MINUTE, -getMinutesBasedOnIndex(index))
            }
            .time

    private fun createEndTime(dateTime: Date, index: Int) =
        createCalendar(dateTime)
            .apply {
                add(Calendar.HOUR, getHoursBasedOnIndex(index))
                add(Calendar.MINUTE, getMinutesBasedOnIndex(index))
            }
            .time

    private fun getHoursBasedOnIndex(index: Int) = (index % 2)

    private fun getMinutesBasedOnIndex(index: Int) = (index * 2 + Random.nextInt(30)) % 60

    private fun createCalendar(dateTime: Date): Calendar =
        Calendar.getInstance()
            .apply {
                time = dateTime
            }

    companion object {
        private const val PROGRAMMES_LIST_SIZE = 10

        private val IMAGE_URLS = listOf(
            "https://www.dropbox.com/s/9u7kw2knftcur8v/channel_logo_09.png?dl=1",
            "https://www.dropbox.com/s/3lzgp0nmmku61d6/channel_logo_06.png?dl=1",
            "https://www.dropbox.com/s/mkqh6xp1i6a93vb/channel_logo_08.png?dl=1",
            "https://www.dropbox.com/s/jxyd3rjbph6w1uq/channel_logo_07.png?dl=1",
            "https://www.dropbox.com/s/4i03hp3jidbfsiu/channel_logo_05.png?dl=1",
            "https://www.dropbox.com/s/mfbmcgvi14v37df/channel_logo_04.png?dl=1",
            "https://www.dropbox.com/s/glmjpxc3u2not12/channel_logo_03.png?dl=1",
            "https://www.dropbox.com/s/4vellphu7o8gyob/channel_logo_02.png?dl=1",
            "https://www.dropbox.com/s/zfjplpzz9k1udlv/channel_logo_01.png?dl=1"
        )

        private val TITLES = listOf(
            "Ukryta prawda",
            "Pojedynek",
            "Wikingowie, odc. 5, sezon 8",
            "Harry Potter i insygnia śmierci",
            "Zabójcza broń 3",
            "Skazani na Shawshank",
            "Ojciec chrzestny",
            "Mroczny rycerz",
            "Hobbit"
        )

        private val TYPES = listOf(
            "Serial paradokumentalny",
            "Kryminał",
            "Historyczny",
            "Fantasy",
            "Akcja",
            "Dramat",
            "Dokumentalny",
            "Fabularny",
            "Przygodowy"
        )
    }
}