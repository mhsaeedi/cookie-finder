package com.quantcast.cookiefinder.processor

import java.util.concurrent.Callable

/**
 * @author : MoMo
 * @since : 14.01.22, Fri
 *
 **/
class AsyncWorker(private val workload: Collection<String>) : Callable<Map<String, Int>> {
	/**
	 * Goes through a list of cookies and counts how many times a cookie repeat
	 */
	override fun call(): Map<String, Int> =
		workload.map { it.cookie() }.filter { it.isNotBlank() }.groupingBy { it }.eachCount()


}


