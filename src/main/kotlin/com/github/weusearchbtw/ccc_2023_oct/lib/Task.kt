package com.github.weusearchbtw.ccc_2023_oct.lib

import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

abstract class Task(val debug: Boolean = false) {
	private val index = this::class.simpleName!!.substringAfter("Task").toInt()

	fun run() {
		if (debug) {
			val input = Files.readAllLines(Path.of("input/${index}_example.txt"))
			val output = computeResult(input)
			println("-----Task $index, example input-----\n$output\n-------------------------------")
			val outputFile = "output/${index}_example.txt"
			val expectedOutput = Files.lines(Path.of(outputFile)).collect(Collectors.joining("\n"))
			println("\n--------------Diff-------------")
			Differ.printDiff(outputFile, output.toString(), expectedOutput)
			return
		}

		for (i in 0..<5) {
			val input = Files.readAllLines(Path.of("input/${index}_$i.txt"))
			val output = computeResult(input)
			Files.writeString(Path.of("output/${index}_$i.txt"), output.toString())
			println("--------Task $index, input $i--------\n$output\n-------------------------------")
		}
	}

	internal abstract fun computeResult(input: Collection<String>): Any
}
