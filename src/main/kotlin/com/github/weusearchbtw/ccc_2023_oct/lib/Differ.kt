package com.github.weusearchbtw.ccc_2023_oct.lib

import org.eclipse.jgit.diff.DiffFormatter
import org.eclipse.jgit.diff.HistogramDiff
import org.eclipse.jgit.diff.RawText
import org.eclipse.jgit.diff.RawTextComparator
import java.io.ByteArrayOutputStream
import kotlin.collections.plus

object Differ {

	// a newline will be added to the end of both strings to avoid "no newline at end of file" errors
	private const val NEWLINE = '\n'

	fun printDiff(expectedOutputFile: String, outputFile: String, expected: String) {
		val process = ProcessBuilder(
				"git",
				"diff",
				"--color=always",
				"--no-index",
				outputFile,
				expectedOutputFile,
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
		val rt2 = RawText(outputFile.toByteArray(Charsets.UTF_8) + NEWLINE.toByte())
		val diffList = HistogramDiff().diff(RawTextComparator.DEFAULT, rt1, rt2)
		DiffFormatter(out).format(diffList, rt1, rt2)
		println(out.toString(Charsets.UTF_8.name()))
	}
}
