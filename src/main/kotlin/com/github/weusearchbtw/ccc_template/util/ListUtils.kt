package com.github.weusearchbtw.ccc_template.util

fun <T> List<T>.skip(count: Int): List<T> = subList(count, size)
