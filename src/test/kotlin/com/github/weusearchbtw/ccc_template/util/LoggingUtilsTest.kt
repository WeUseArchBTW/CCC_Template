package com.github.weusearchbtw.ccc_template.util

import kotlinx.datetime.Clock
import kotlin.concurrent.thread
import kotlin.time.Duration.Companion.seconds

fun main() {
	val logger = ThrottledLogger(1.seconds)
	for (i in 1..10) {
		thread {
			var count = 0
			while (true) {
				logger.log("Thread", i, "at ${Clock.System.now()} - ${count++}")
				count++
			}
		}
	}
}
