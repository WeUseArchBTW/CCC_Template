package com.github.weusearchbtw.ccc_template.lib

import org.eclipse.jgit.diff.DiffFormatter
import org.eclipse.jgit.diff.HistogramDiff
import org.eclipse.jgit.diff.RawText
import org.eclipse.jgit.diff.RawTextComparator
import java.io.ByteArrayOutputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors
import kotlin.collections.plus

object Differ {

	// a newline will be added to the end of both strings to avoid "no newline at end of file" errors
	private const val NEWLINE = '\n'

	fun printDiff(expectedOutputFile: String, outputFile: String, expected: String) {
		val process = ProcessBuilder.startPipeline(listOf(
			ProcessBuilder(
				"git",
				"diff",
				"--color=always",
				"--no-index",
				outputFile,
				expectedOutputFile,
			),
			ProcessBuilder(
				"grep",
				"-v",
				"No newline"
			)
				.redirectOutput(ProcessBuilder.Redirect.INHERIT)
				.redirectError(ProcessBuilder.Redirect.INHERIT)
		))
			.last()
			.apply(Process::waitFor)
		// git diff returns 1 if there are differences and 0 if there are none
		if (process.exitValue() == 1 || process.exitValue() == 0) {
			return
		}

		// fallback for non-linux systems
		val output = Files.lines(Path.of(outputFile)).collect(Collectors.joining("\n"))

		val out = ByteArrayOutputStream()
		val rt1 = RawText(expected.toByteArray(Charsets.UTF_8) + NEWLINE.code.toByte())
		val rt2 = RawText(output.toByteArray(Charsets.UTF_8) + NEWLINE.code.toByte())
		val diffList = HistogramDiff().diff(RawTextComparator.DEFAULT, rt1, rt2)
		DiffFormatter(out).format(diffList, rt1, rt2)
		println(out.toString(Charsets.UTF_8.name()))
	}
}
