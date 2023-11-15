package no.telenor.kt.boot3.configurable.mdc.util

import sun.misc.Unsafe

private val UNSAFE = Unsafe::class.java.getDeclaredField("theUnsafe").let {
	it.isAccessible = true
	it.get(null) as Unsafe
}

@Suppress("UNCHECKED_CAST")
internal fun <T> getField(name: String, from: Any): T =
	UNSAFE.getObject(from, UNSAFE.objectFieldOffset(from::class.java.getDeclaredField(name))) as T
