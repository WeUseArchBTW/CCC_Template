package com.github.weusearchbtw.ccc_template.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.time.Duration

fun pln(vararg arguments: Any) = println(arguments.joinToString())

class ThrottledLogger(private val interval: Duration) {
	private var lastLoggedAt = Instant.DISTANT_PAST
	private val lock = ReentrantLock()

	fun log(vararg arguments: Any) {
		val now = Clock.System.now()
		lock.withLock {
			if (now - lastLoggedAt < interval) {
				return
			}

			lastLoggedAt = now
		}
		println(arguments.joinToString())
	}
}
