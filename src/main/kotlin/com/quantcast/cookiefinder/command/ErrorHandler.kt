package com.quantcast.cookiefinder.command

import mu.KotlinLogging
import picocli.CommandLine
import picocli.CommandLine.IExecutionExceptionHandler
import picocli.CommandLine.ParseResult


/**
 * @author : MoMo
 * @since : 14.01.22, Fri
 *
 **/
class ErrorHandler : IExecutionExceptionHandler {

	/**
	 * Handle errors
	 */
	override fun handleExecutionException(ex: Exception, cmd: CommandLine, parseResult: ParseResult): Int =
		when (ex) {
			is InvalidFileException -> helpUser(cmd, ex) // when we know what happened
			else -> printError(cmd, ex) // if an unexpected error happens
		}

}

class InvalidFileException(message: String) : RuntimeException(message)

private val log = KotlinLogging.logger { }

/**
 * Unexpected error
 * log the error and notify user of an unexpected error
 */
private fun printError(cmd: CommandLine, e: Exception): Int = with(cmd) {
	err.println("An unexpected error happened. Please contact support@quantcast.com")
		.also { log.error { "${e::class.java.simpleName} happened." } }
		.run { -1 }
}

/**
 * User error, help user to correct usage
 */
private fun helpUser(cmd: CommandLine, ex: Exception): Int = with(cmd) {
	err.println(ex.message)
		.run { helpFactory.create(commandSpec, colorScheme) }
		.run { synopsisHeading().plus(synopsis(synopsisHeadingLength())) }
		.also { err.println(it) }
		.also { err.printf("Try '%s --help' for more information.%n", commandSpec.qualifiedName()) }
		.run { if (null != exitCodeExceptionMapper) exitCodeExceptionMapper.getExitCode(ex) else commandSpec.exitCodeOnInvalidInput() }
}
