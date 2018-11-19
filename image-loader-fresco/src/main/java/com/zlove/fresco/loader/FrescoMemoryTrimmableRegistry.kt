package com.zlove.fresco.loader

import com.facebook.common.memory.MemoryTrimType
import com.facebook.common.memory.MemoryTrimmable
import com.facebook.common.memory.MemoryTrimmableRegistry
import java.util.*

class FrescoMemoryTrimmableRegistry private constructor(): MemoryTrimmableRegistry {

    private val memoryTrimmableSet = HashSet<MemoryTrimmable>()
    private val mutex = Any()

    companion object {
        private var instance: FrescoMemoryTrimmableRegistry? = null
        get() {
            if (field == null) {
                field = FrescoMemoryTrimmableRegistry()
            }
            return field
        }
        fun get(): FrescoMemoryTrimmableRegistry {
            return instance!!
        }
    }

    override fun registerMemoryTrimmable(trimmable: MemoryTrimmable?) {
        if (null != trimmable) {
            synchronized(mutex) {
                memoryTrimmableSet.add(trimmable)
            }
        }
    }

    override fun unregisterMemoryTrimmable(trimmable: MemoryTrimmable?) {
        if (null != trimmable) {
            synchronized(mutex) {
                memoryTrimmableSet.remove(trimmable)
            }
        }
    }

    fun trimMemory(trimType: MemoryTrimType) {
        synchronized(mutex) {
            for (memoryTrimmable in memoryTrimmableSet) {
                memoryTrimmable.trim(trimType)
            }
        }
    }

}
