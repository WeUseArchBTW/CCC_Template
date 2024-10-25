package com.github.weusearchbtw.ccc_template.util

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.time.Duration

fun pln(vararg arguments: Any) = println(arguments.joinToString())

class ThrottledLogger(interval: Duration) {
	private val interval = interval.inWholeMilliseconds
	private var lastLoggedAt: Long = 0
	private val lock = ReentrantLock()

	fun log(vararg arguments: Any) {
		val now = System.currentTimeMillis()
		lock.withLock {
			if (now - lastLoggedAt < interval) {
				return
			}

			lastLoggedAt = now
		}
		println(arguments.joinToString())
	}
}
