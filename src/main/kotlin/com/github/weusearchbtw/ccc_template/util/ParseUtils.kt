package com.github.weusearchbtw.ccc_template.util

/**
 * Runs this[0] times
 * Windows by [parseWindowSize]
 */
fun List<String>.firstTimesWindowedBy(parseWindowSize: (String) -> Int = { it.toInt() }, block: (Collection<String>) -> Any)
	= skip(1).timesWindowedBy(times = first().toInt(), parseWindowSize = parseWindowSize, block = block)

fun List<String>.timesWindowedBy(times: Int, parseWindowSize: (String) -> Int, block: (Collection<String>) -> Any): String {
	var index = 0
	return (0..<times).joinToString(separator = "\n") { no ->
		val windowSize = parseWindowSize(this[index++])
		val window = subList(index, index + windowSize)
		index += window.size

		block(window).toString()
	}
}
