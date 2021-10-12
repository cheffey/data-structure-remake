package remake.map.hashmap

import java.util.Objects

/**
 * Created by Chef.Xie on 2021-10-12
 */
class CHashMap<K, V> : MutableMap<K, V> {
    private var capacity = 16
    private var table: Array<CMutableEntry<K, V>?> = arrayOfNulls(16)
    override var size = 0

    override val entries: CMutableSet<MutableMap.MutableEntry<K, V>> = CMutableSet(table)
    override val keys: CMutableSet<K>
        get() = TODO("Not yet implemented")
    override val values: MutableCollection<V>
        get() = TODO("Not yet implemented")

    override fun containsKey(key: K): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsValue(value: V): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(key: K): V? {
        val hash = key.hashCode()
        val node = table[(capacity - 1) and hash] ?: return null
        var currentNode: CMutableEntry<K, V> = node
        while (true) {
            if (Objects.equals(currentNode.key, key)) {
                return currentNode.value
            }
            if (currentNode.next == null) {
                return null
            }
            currentNode = currentNode.next!!
        }
    }

    override fun isEmpty() = size == 0

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun put(key: K, value: V): V? {
        val hash = key.hashCode()
        val node = table[(capacity - 1) and hash]
        if (node == null) {
            table[(capacity - 1) and hash] = CMutableEntry(key, value, hash)
            size++
            return null
        }
        var currentNode: CMutableEntry<K, V> = node
        while (true) {
            if (Objects.equals(currentNode.key, key)) {
                val oldValue = currentNode.value
                currentNode.value = value
                return oldValue
            }
            if (currentNode.next == null) {
                currentNode.next = CMutableEntry(key, value, hash)
                size++
                return null
            }
            currentNode = currentNode.next!!
        }
    }

    override fun putAll(from: Map<out K, V>) {
        TODO("Not yet implemented")
    }

    override fun remove(key: K): V? {
        TODO("Not yet implemented")
    }
}

class CMutableEntry<K, V>(override val key: K, override var value: V, val hash: Int) : MutableMap.MutableEntry<K, V> {
    var next: CMutableEntry<K, V>? = null

    override fun setValue(newValue: V): V {
        value = newValue
        TODO()
    }
}