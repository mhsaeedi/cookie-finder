package com.quantcast.cookiefinder.command

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.File
import kotlin.test.assertEquals

/**
 * @author : Mohammad <mohammad.saeedi></mohammad.saeedi>@visual-meta.com>
 * @since : 18.01.22, Tue
 */
@ExtendWith(MockKExtension::class)
internal class CommandTest {
	@Test
	fun `assure throws InvalidFileException when file not exist`() {
		val command = Command()
		val absolutePath = "/this/path/must/not/exist/file.csv"
		command.file = File(absolutePath)
		val e = assertThrows(InvalidFileException::class.java) { command.run() }
		assertEquals("File: $absolutePath not exist!", e.message)
	}

	@Test
	fun `assure throws InvalidFileException when is directory`() {
		val command = Command()
		val file = mockk<File>()
		every { file.exists() } returns true
		every { file.isDirectory } returns true
		val absolutePath = "/some/path"
		every { file.absolutePath } returns absolutePath
		command.file = file
		val e = assertThrows(InvalidFileException::class.java) { command.run() }
		assertEquals("File: $absolutePath should be a file but is a directory", e.message)
	}

	@Test
	fun `assure throws InvalidFileException when is not csv`() {
		val command = Command()
		val file = mockk<File>()
		every { file.exists() } returns true
		every { file.isDirectory } returns false
		val absolutePath = "/some/path.sth"
		every { file.absolutePath } returns absolutePath
		every { file.extension } returns "sth"
		command.file = file
		val e = assertThrows(InvalidFileException::class.java) { command.run() }
		assertEquals("File: $absolutePath is not csv", e.message)
	}


}
