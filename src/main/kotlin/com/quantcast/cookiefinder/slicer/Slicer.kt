package com.quantcast.cookiefinder.slicer

import com.quantcast.cookiefinder.command.Command
import java.nio.charset.StandardCharsets

/**
 * @author : MoMo
 * @since : 14.01.22, Fri
 *
 **/
class Slicer(private val command: Command) {

	/**
	 * Reads a cookie file (Only required part using binary search)
	 * and partitions result into smaller batches
	 */
	fun slice(): List<List<String>> = command.run {
		file.readLines(StandardCharsets.UTF_8) // read file
			.toCollection(ArrayList())
			.apply { this.removeAt(0) } // remove header
			.cutRequiredPart(localDate) // Cut required segment using binary search
			.chunked(batchSize) // Partition by batch size
	}

}




