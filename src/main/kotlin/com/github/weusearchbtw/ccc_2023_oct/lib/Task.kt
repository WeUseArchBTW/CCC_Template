package com.github.weusearchbtw.ccc_2023_oct.lib

import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

abstract class Task(val debug: Boolean = false) {
	private val index = this::class.simpleName!!.substringAfter("Task").toInt()

	fun run() {

		runSubTask(index, "example")

		if (debug) {
			val input = Files.readAllLines(Path.of("input/level${index}_example.in"))
			val output = computeResult(input)
			println("-----Task $index, example input-----\n$output\n-------------------------------")
			val outputFile = "output/level${index}_example.out"
			val expectedOutput = Files.lines(Path.of(outputFile)).collect(Collectors.joining("\n"))
			println("\n--------------Diff-------------")
			Differ.printDiff(outputFile, output.toString(), expectedOutput)
			return
		}

		for (i in 0..<5) {
			runSubTask(index, i)
		}
	}

	private fun runSubTask(level: Int, subTask: String) {
		val input = Files.readAllLines(Path.of("input/level${level}_$subTask.in"))
		val output = computeResult(input)
		Files.writeString(Path.of("output/level${level}_$subTask.out"), output.toString())
		println("--------Task $level, input $subTask--------\n$output\n-------------------------------")
	}
	private fun runSubTask(level: Int, subTask: Int) = runSubTask(level, subTask.toString())

	internal abstract fun computeResult(input: Collection<String>): Any
}
