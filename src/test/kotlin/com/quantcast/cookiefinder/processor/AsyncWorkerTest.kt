package com.quantcast.cookiefinder.processor

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * @author : Mohammad <mohammad.saeedi></mohammad.saeedi>@visual-meta.com>
 * @since : 18.01.22, Tue
 */
@ExtendWith(MockKExtension::class)
internal class AsyncWorkerTest {


	@Test
	fun `assure merge works correct`() {
		val workload = arrayListOf<String>()
		add(workload, 3, "a,time")
		add(workload, 4, "b,time")
		add(workload, 5, "c,time")
		val result = AsyncWorker(workload).call()

		assertEquals(result["a"], 3)
		assertEquals(result["b"], 4)
		assertEquals(result["c"], 5)

		assertNull(result["d"])
	}


	companion object {
		private fun add(w: ArrayList<String>, n: Int, s: String) = repeat((0 until n).count()) { w.add(s) }
	}
}
