package com.github.weusearchbtw.ccc_template.tasks

import com.github.weusearchbtw.ccc_template.lib.Task

object Task1 : Task(debug = true) {
	override fun computeResult(input: Collection<String>): String {
		return input.joinToString("\n")
	}
}
