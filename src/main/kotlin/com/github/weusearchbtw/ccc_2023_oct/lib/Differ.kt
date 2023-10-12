package com.github.weusearchbtw.ccc_2023_oct.lib

import org.eclipse.jgit.diff.DiffFormatter
import org.eclipse.jgit.diff.HistogramDiff
import org.eclipse.jgit.diff.RawText
import org.eclipse.jgit.diff.RawTextComparator
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.nio.file.Path
import kotlin.collections.plus


private const val TEMP_OUTPUT_FILE = "/tmp/ccc_output.txt"

object Differ {

	// a newline will be added to the end of both strings to avoid "no newline at end of file" errors
	private const val NEWLINE = '\n'

	fun printDiff(outputFile: String, output: String, expected: String) {
		// hacky workaround because for some reason piping into stdin doesn't work....
		Files.writeString(Path.of(TEMP_OUTPUT_FILE), output + NEWLINE)
		val process = ProcessBuilder(
				"git",
				"diff",
				"--color=always",
				"--no-index",
				TEMP_OUTPUT_FILE,
				outputFile,
			)
			.redirectOutput(ProcessBuilder.Redirect.INHERIT)
			.redirectError(ProcessBuilder.Redirect.INHERIT)
			.start()
			.onExit()
			.join()
		// git diff returns 1 if there are differences and 0 if there are none
		if (process.exitValue() == 1 || process.exitValue() == 0) {
			return
		}

		// fallback for non-linux systems
		val out = ByteArrayOutputStream()
		val rt1 = RawText(expected.toByteArray(Charsets.UTF_8) + NEWLINE.toByte())
		val rt2 = RawText(output.toByteArray(Charsets.UTF_8) + NEWLINE.toByte())
		val diffList = HistogramDiff().diff(RawTextComparator.DEFAULT, rt1, rt2)
		DiffFormatter(out).format(diffList, rt1, rt2)
		println(out.toString(Charsets.UTF_8.name()))
	}
}
