package com.github.weusearchbtw.ccc_template.lib

import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

abstract class Task(val debug: Boolean = false) {
	private val index = this::class.simpleName!!.substringAfter("Task").toInt()

	fun run() {
		if (debug) {
			runSubTask(index, "example")

			val expectedOutputFile = "expected/level${index}_example.out"
			val outputFile = "output/level${index}_example.out"
			val expectedOutputFallback = Files.lines(Path.of(expectedOutputFile)).collect(Collectors.joining("\n"))

			println("\n--------------Diff-------------")
			Differ.printDiff(expectedOutputFile, outputFile, expectedOutputFallback)
			return
		}

		for (i in 1.. 5) {
			runSubTask(index, i)
		}
	}

	private fun runSubTask(level: Int, subTask: String) {
		val input = Files.readAllLines(Path.of("input/level${level}_$subTask.in"))
		println("--------Task $level, input $subTask--------")
		val output = computeResult(input)
		Files.writeString(Path.of("output/level${level}_$subTask.out"), output.toString())
		println("$output\n-------------------------------")
	}
	private fun runSubTask(level: Int, subTask: Int) = runSubTask(level, subTask.toString())

	internal abstract fun computeResult(input: List<String>): Any
}
