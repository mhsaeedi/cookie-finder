package com.quantcast.cookiefinder.command

import picocli.CommandLine.Command
import picocli.CommandLine.Option
import com.quantcast.cookiefinder.processor.Finder
import com.quantcast.cookiefinder.slicer.Slicer
import java.io.File
import java.time.LocalDate

/**
 * @author : MoMo
 * @since : 14.01.22, Fri
 */
@Command(name = "active-cookie", mixinStandardHelpOptions = true, version = ["find-cookie 1.0"],
	description = ["Prints the most popular cookie in a specific date to STDOUT."])
class Command : Runnable {

	@Option(names = ["-f", "--file"], description = ["Path to the csv file whose cookies to calculate."], required = true)
	lateinit var file: File

	@Option(names = ["-d", "--date"], description = ["Target date in yyyy-mm-dd format, e.g. 2022.12.02"], required = true)
	lateinit var localDate: LocalDate

	val batchSize = 1000

	/**
	 * If file and date be valid starts processing the file
	 * otherwise throw corresponding error and guide user
	 */
	override fun run(): Unit = file.run {
		when {
			!exists() -> throw InvalidFileException("File: $absolutePath not exist!")
			isDirectory -> throw InvalidFileException("File: $absolutePath should be a file but is a directory")
			csv != extension -> throw InvalidFileException("File: $absolutePath is not $csv")
			else -> Finder(Slicer(this@Command)).find().forEach(::println)
		}
	}

	companion object {
		private const val csv = "csv"
	}
}
