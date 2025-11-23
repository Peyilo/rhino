package com.script.compat

open class LruCache<K, V>(private val maxSize: Int) {

    private val map: LinkedHashMap<K, V> =
        object : LinkedHashMap<K, V>(0, 0.75f, true) {
            override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
                return size > maxSize
            }
        }

    private var currentSize = 0

    protected open fun sizeOf(key: K, value: V): Int = 1

    @Synchronized
    operator fun get(key: K): V? = map[key]

    @Synchronized
    fun put(key: K, value: V) {
        map[key] = value
    }

    @Synchronized
    fun remove(key: K): V? {
        val old = map.remove(key)
        if (old != null) currentSize -= sizeOf(key, old)
        return old
    }

    @Synchronized
    fun trimToSize(maxSize: Int) {
        while (currentSize > maxSize && map.isNotEmpty()) {
            val toEvict = map.entries.iterator().next()
            remove(toEvict.key)
        }
    }
}