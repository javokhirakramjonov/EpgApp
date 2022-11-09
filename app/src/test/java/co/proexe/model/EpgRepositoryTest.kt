package co.proexe.model

import co.proexe.model.data.TvProgrammeCategory
import co.proexe.model.repository.LocalEpgRepository
import java.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EpgRepositoryTest {

    lateinit var repository: LocalEpgRepository

    @Before
    fun setupBeforeEach() {
        repository = LocalEpgRepository()
    }

    @Test
    fun `getProgrammeForDay should return list of programmes`() {
        val dateTime = getCurrentDateTime()
        runTest {
            assertTrue(repository.getProgrammesForDateTime(dateTime).isNotEmpty())
        }
    }

    @Test
    fun `getProgrammeForDay should return list of programmes with size equal to passed amount`() {
        val dateTime = getCurrentDateTime()
        val amount = 4
        runTest {
            assertTrue(repository.getProgrammesForDateTime(dateTime, amount).size == amount)
        }
    }

    @Test
    fun `getProgrammeForDay should use all distinct values for image url`() {
        val dateTime = getCurrentDateTime()
        val amount = LocalEpgRepository.IMAGE_URLS.size

        runTest {
            val result = repository.getProgrammesForDateTime(dateTime, amount)
            assertTrue(result.distinctBy { it.imageUrl }.size == amount)
        }
    }

    @Test
    fun `getProgrammeForDay should use all distinct values for titles`() {
        val dateTime = getCurrentDateTime()
        val amount = LocalEpgRepository.TITLES.size

        runTest {
            val result = repository.getProgrammesForDateTime(dateTime, amount)
            assertTrue(result.distinctBy { it.title }.size == amount)
        }
    }

    @Test
    fun `getProgrammeForDay should use all distinct values for types`() {
        val dateTime = getCurrentDateTime()
        val amount = LocalEpgRepository.TYPES.size

        runTest {
            val result = repository.getProgrammesForDateTime(dateTime, amount)
            assertTrue(result.distinctBy { it.type }.size == amount)
        }
    }

    @Test
    fun `getProgrammeForDay should use all distinct values for categories`() {
        val dateTime = getCurrentDateTime()
        val amount = TvProgrammeCategory.values().size

        runTest {
            val result = repository.getProgrammesForDateTime(dateTime, amount)
            assertTrue(result.distinctBy { it.category }.size == amount)
        }
    }

    @Test
    fun `getProgrammeForDay should use all distinct values for isFavourited`() {
        val dateTime = getCurrentDateTime()
        val amount = 10

        runTest {
            val result = repository.getProgrammesForDateTime(dateTime, amount)
            assertTrue(result.distinctBy { it.isFavourite }.size == 2)
        }
    }

    @Test
    fun `getProgrammeForDay should return list with all progressPercentages positive`() {
        val dateTime = getCurrentDateTime()
        val amount = 10

        runTest {
            val result = repository.getProgrammesForDateTime(dateTime, amount)
            assertTrue(result.filterNot { it.progressPercent >= 0 }.isEmpty())
        }
    }

    @Test
    fun `getProgrammeForDay should return list with some progressPercentages should not be zero`() {
        val dateTime = getCurrentDateTime()
        val amount = 10

        runTest {
            val result = repository.getProgrammesForDateTime(dateTime, amount)
            assertTrue(result.any { it.progressPercent > 0 })
        }
    }

    @Test
    fun `getProgrammeForDay should return list with all startTimes happening after startTimes`() {
        val dateTime = getCurrentDateTime()
        val amount = 10

        runTest {
            val result = repository.getProgrammesForDateTime(dateTime, amount)
            assertTrue(result.all { it.startTime < it.endTime })
        }
    }

    private fun getCurrentDateTime(): Date = Calendar.getInstance().time
}