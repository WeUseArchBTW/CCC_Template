package com.github.weusearchbtw.ccc_template.tasks

import com.github.weusearchbtw.ccc_template.lib.Task
import com.github.weusearchbtw.ccc_template.util.firstTimesWindowedBy

object Task1 : Task(debug = true) {
	override fun computeResult(input: List<String>): String {
		return input.firstTimesWindowedBy(
			parseWindowSize = { it.split(" ")[1].toInt() + 1 },
		) { window ->
			println(window)

			window.last()
		}
	}
}
