import com.quantcast.cookiefinder.command.Command
import com.quantcast.cookiefinder.command.ErrorHandler
import picocli.CommandLine
import kotlin.system.exitProcess

/**
 * Finds the most active cookie of a specific date
 * and prints it to stdout
 */
fun main(args: Array<String>): Unit =
	exitProcess(CommandLine(Command())
		.setExecutionExceptionHandler(ErrorHandler())
		.execute(*args))

