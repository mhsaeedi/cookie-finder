package com.quantcast.cookiefinder.processor

import mu.KotlinLogging

/**
 * @author : MoMo
 * @since : 18.01.22, Tue
 *
 **/

/**
 * Merge multiple results into one
 */
internal fun List<Map<String, Int>>.mergeThenSort() =
	flatMap { it.asSequence() } // join all lists into one
		.groupBy({ it.key }, { it.value }) // Group by cookie
		.mapValues { (_, values) -> values.sum() } // Count of cookie as value
		.toList()
		.sortedByDescending { (_, value) -> value } // Sort by count

/**
 * Gets the most active cookies
 */
internal fun List<Pair<String, Int>>.getTopOnes(): List<String> = when {
	isEmpty() -> emptyList()
	else -> get(0).second.run {
		takeWhile { this == it.second }.map { it.first }  // Take the first ones with same count
	}
}

private const val comma = ","
private val logger = KotlinLogging.logger { }

/**
 * Get cookie part from a line
 * e.g. in line "4sMM2LxV07bPJzwf,2018-12-07T23:30:00+00:00"
 * returns "4sMM2LxV07bPJzwf"
 */
internal fun String.cookie(): String = split(comma).run {
	when {
		size != 2 -> "".also { logger.error { "Invalid entry: $this" } }
		else -> this[0]
	}
}


