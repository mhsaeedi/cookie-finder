package com.quantcast.cookiefinder.processor

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertContains
import kotlin.test.assertEquals

/**
 * @author : Mohammad <mohammad.saeedi></mohammad.saeedi>@visual-meta.com>
 * @since : 18.01.22, Tue
 */
@ExtendWith(MockKExtension::class)
internal class FindUtilTest {

	@Test
	fun `assure merge then sort works as expected`() {

		val result = arrayListOf(
			mapOf("a" to 3),
			mapOf("a" to 2),
			mapOf("b" to 2),
			mapOf("c" to 1),
			mapOf("d" to 0),
		).mergeThenSort()

		assertEquals(result[0].first, "a")
		assertEquals(result[0].second, 5)

		assertEquals(result[1].first, "b")
		assertEquals(result[1].second, 2)

		assertEquals(result[2].first, "c")
		assertEquals(result[2].second, 1)

		assertEquals(result[3].first, "d")
		assertEquals(result[3].second, 0)
	}

	@Test
	fun `assure get only one result, when there is only one max`() =
		arrayListOf(
			mapOf("a" to 3),
			mapOf("a" to 2),
			mapOf("b" to 2),
			mapOf("c" to 1),
			mapOf("d" to 0),
		).mergeThenSort().getTopOnes()
			.also { assertEquals(1, it.size) }
			.apply { HashSet(this) }
			.run { assertContains(this, "a") }

	@Test
	fun `assure get multiple results, when there are more than one max`() =
		arrayListOf(
			mapOf("c" to 5),
			mapOf("a" to 3),
			mapOf("b" to 5),
			mapOf("a" to 2),
			mapOf("d" to 0),
		).mergeThenSort().getTopOnes()
			.also { assertEquals(3, it.size) }
			.apply { HashSet(this) }
			.run {
				assertContains(this, "a")
				assertContains(this, "b")
				assertContains(this, "c")
			}

	@Test
	fun `assure get empty list, when there are no result`() =
		arrayListOf<Map<String, Int>>()
			.mergeThenSort().getTopOnes()
			.run { assertEquals(0, size) }

	@Test
	fun `assure get cookie part, when entry has comma`() =
		"cookie,some date".cookie().run { assertEquals("cookie", this) }

	@Test
	fun `assure get empty string, when entry has not comma`() =
		"cookie some date".cookie().run { assert(this.isBlank()) }

}
