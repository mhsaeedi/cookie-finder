package com.quantcast.cookiefinder.processor

import com.quantcast.cookiefinder.slicer.Slicer
import java.util.concurrent.Executors

/**
 * @author : MoMo
 * @since : 14.01.22, Fri
 *
 **/
class Finder(private val slicer: Slicer) {

	/**
	 * Asynchronously process file in separate batches
	 * and merge the final result and finds the most active cookie
	 */
	fun find(): Collection<String> =
		slicer.slice() // partition file into smaller batches
			.map { AsyncWorker(it) } // Asynchronously process each batch
			.map { pool.submit(it) }
			.mapNotNull { it.get() }
			.mergeThenSort() // merge result and sort by most active cookies
			.getTopOnes() // Get the most active cookies

	companion object {
		private val pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
	}

}
