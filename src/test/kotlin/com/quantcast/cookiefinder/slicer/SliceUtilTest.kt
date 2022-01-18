package com.quantcast.cookiefinder.slicer

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate
import kotlin.test.assertContains
import kotlin.test.assertEquals

/**
 * @author : Mohammad <mohammad.saeedi></mohammad.saeedi>@visual-meta.com>
 * @since : 18.01.22, Tue
 */
@ExtendWith(MockKExtension::class)
internal class SliceUtilTest {
	@Test
	fun `assure correct sub list when cookies for date exist`() =
		assertSubList("2018-12-09", 5)
			.also { assertSubList("2018-12-08", 4) }
			.also { assertSubList("2018-12-07", 1) }
			.also { assertSubList("2018-12-10", 1) }
			.also { assertSubList("2018-12-06", 0) }
			.also { assertSubList("2018-12-11", 0) }



	companion object {
		private fun assertSubList(date: String, n: Int) = cookies.cutRequiredPart(LocalDate.parse(date))
			.also { assertEquals(n, it.size) }
			.forEach { assertContains(it, date) }


		private val cookies = arrayListOf(
			"AtY0laUfhglK3lC7,2018-12-10T14:19:00+00:00",
			"AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00",
			"AtY0laUfhglK3lC7,2018-12-09T14:19:00+00:00",
			"SAZuXPGUrfbcn5UA,2018-12-09T10:13:00+00:00",
			"5UAVanZf6UtGyKVS,2018-12-09T07:25:00+00:00",
			"AtY0laUfhglK3lC7,2018-12-09T06:19:00+00:00",
			"SAZuXPGUrfbcn5UA,2018-12-08T22:03:00+00:00",
			"SAZuXPGUrfbcn5UA,2018-12-08T22:03:00+00:00",
			"4sMM2LxV07bPJzwf,2018-12-08T21:30:00+00:00",
			"fbcn5UAVanZf6UtG,2018-12-08T09:30:00+00:00",
			"4sMM2LxV07bPJzwf,2018-12-07T23:30:00+00:00",
		)
	}
}
