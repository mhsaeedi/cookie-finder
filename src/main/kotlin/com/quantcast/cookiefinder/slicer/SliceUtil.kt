package com.quantcast.cookiefinder.slicer

import java.time.*
import java.time.format.DateTimeFormatter

/**
 * @author : MoMo
 * @since : 18.01.22, Tue
 *
 **/
/**
 * Find first and last occurrences of a date using binary search and returns
 * that part
 */
internal fun List<String>.cutRequiredPart(localDate: LocalDate): Collection<String> {
	val fromIndex = fromIndex(this, localDate, 0, size)
	val toIndex = toIndex(this, localDate, 0, size)
	return when {
		fromIndex < 0 && toIndex < 0 -> emptyList()
		else -> subList(fromIndex, toIndex)
	}
}

/**
 * First occurrence
 * Recursive
 * Binary Search
 */
private fun fromIndex(arr: List<String>, target: LocalDate, l: Int, r: Int): Int {
	if (l == r && (l < arr.size && l >= 0 && arr[l].toLocalDate() == target)) return l
	if (l == r) return -1
	val mid = l + (r - l) / 2
	return when {
		arr[mid].toLocalDate().isAfter(target) -> fromIndex(arr, target, mid + 1, r)
		else -> fromIndex(arr, target, l, mid)
	}

}


/**
 * Last occurrence
 * Recursive
 * Binary Search
 */
private fun toIndex(arr: List<String>, target: LocalDate, l: Int, r: Int): Int {
	if (l == r && (l <= arr.size && l > 0 && arr[l - 1].toLocalDate() == target)) return l
	if (l == r) return -1
	val mid = l + (r - l) / 2
	return when {
		arr[mid].toLocalDate().isAfter(target) || arr[mid].toLocalDate() == target -> toIndex(arr, target, mid + 1, r)
		else -> toIndex(arr, target, l, mid)
	}
}

/**
 * String to date
 */
private fun String.toLocalDate() =
	LocalDate.parse(split(",")[1],DateTimeFormatter.ISO_DATE_TIME)


