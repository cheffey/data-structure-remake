package remake.map.hashmap

import org.junit.Test
import kotlin.random.Random

/**
 * Created by Chef.Xie on 2021-10-12
 */
class CHashMapTest {
    @Test
    fun sameHashKeysWithHashMap() {
        sameHashKeys(HashMap())
    }

    @Test
    fun sameHashKeysWithCHashMap() {
        sameHashKeys(CHashMap())
    }

    private fun sameHashKeys(testMap: MutableMap<WithDefinedHash, Int>) {
        val mirrorMap = mutableMapOf<WithDefinedHash, Int>()
        for (i in 1..32) {
            val key1 = WithDefinedHash(i)
            val key2 = WithDefinedHash(i)
            val value1 = Random.nextInt()
            val value2 = Random.nextInt()
            testMap[key1] = value1
            testMap[key2] = value2
            mirrorMap[key1] = value1
            mirrorMap[key2] = value2
        }
        for (key in mirrorMap.keys) {
            assert(testMap.get(key) == mirrorMap.get(key))
        }
        assert(testMap.get(WithDefinedHash(10)) == null)
        assert(testMap.size == 64)
    }
}

class WithDefinedHash(private val hash: Int) {
    override fun hashCode(): Int {
        return hash
    }

    override fun toString(): String {
        return "$hash"
    }
}