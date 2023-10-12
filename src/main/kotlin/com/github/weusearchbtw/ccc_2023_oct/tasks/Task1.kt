package com.github.weusearchbtw.ccc_2023_oct.tasks

import com.github.weusearchbtw.ccc_2023_oct.lib.Task

object Task1 : Task(debug = true) {
	override fun computeResult(input: Collection<String>): String {
		return input.joinToString("\n")
	}
}
