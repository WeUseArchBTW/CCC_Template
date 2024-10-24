package com.github.weusearchbtw.ccc_template.tasks

import com.github.weusearchbtw.ccc_template.lib.Task
import com.github.weusearchbtw.ccc_template.util.skip
import com.github.weusearchbtw.ccc_template.util.timesWindowedBy

object Task2 : Task(debug = true) {
	override fun computeResult(input: List<String>): String {
		return input.skip(1).timesWindowedBy(
			times = input.first().toInt(),
			parseWindowSize = { 1 }
		) { window ->
			val value = window.first()
			println(value)

			value.split(" ").sumOf { string -> string.toInt() }
		}
	}
}
