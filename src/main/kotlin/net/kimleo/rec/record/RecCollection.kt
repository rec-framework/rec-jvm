package net.kimleo.rec.record

import net.kimleo.rec.accessor.AccessorFactory
import net.kimleo.rec.orElse

class RecCollection(val records: List<Record>, val type: RecType): Iterable<Record> {
    override fun iterator(): Iterator<Record> {
        return records.iterator()
    }

    val accessor = type.accessor

    fun select(vararg keys: String): RecCollection {
        checkKeyExists(*keys)
        val newType = newType(keys)
        val newRecords = arrayListOf<Record>()
        for (record in records) {
            val fields = arrayListOf<String>()
            val accessor = accessor.of(record)
            for (key in keys) {
                fields.add(accessor.get(key).orEmpty())
            }
            newRecords.add(Record(fields.map(::Field)))
        }

        return RecCollection(newRecords, newType)
    }

    fun where(key: String, pattern: String): RecCollection {
        val filtered = records.filter { rec ->
            accessor.of(rec).get(key)?.contains(pattern).orElse { false }
        }
        return RecCollection(filtered, type)
    }

    fun where(key: String, pattern: Regex): RecCollection {
        val filtered = records.filter { rec ->
            accessor.of(rec).get(key)?.contains(pattern).orElse { false }
        }
        return RecCollection(filtered, type)
    }

    private fun checkKeyExists(vararg keys: String) {
        for (key in keys) {
            assert(key in accessor.fieldMap.keys)
        }
    }


    private fun newType(keys: Array<out String>): RecType {
        return object: RecType {
            override val name = "image of ${type.name}"
            override val parseConfig = type.parseConfig
            override val key = type.key
            override val format = keys.joinToString(type.parseConfig.delimiter.toString())
            override val accessor = AccessorFactory(Record(keys.map(::Field)))
        }
    }

    fun isUnique(): Boolean {
        return records.toSet().size == records.size
    }
}