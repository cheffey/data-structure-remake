package remake.tree.bi_search

import org.junit.Test
import kotlin.random.Random

class BinarySearchTreeTest {
    @Test
    fun verifyDST() {
        // val tree = buildTree()
        val tree = buildRandomTree()
        assert(tree.isVerified())
    }

    @Test
    fun remove() {
        // val tree = buildTree()
        val tree = buildRandomTree()
        println(tree)
        val values = tree.values()
        tree.remove(values.random())
        println(tree)
        tree.remove(values.random())
        println(tree)
        assert(tree.isVerified())
    }

    private fun buildTree(): BinarySearchTree {
        val tree = BinarySearchTree()
        val treeNodes = listOf(11, 4, 63, 45, 54, 7, 523, 130)
        for (i in treeNodes) {
            tree.insert(i)
        }
        return tree
    }

    private fun buildRandomTree(): BinarySearchTree {
        val tree = BinarySearchTree()
        val values = (1..8).map { Random.nextInt(100) }.toHashSet()
        for (value in values) {
            tree.insert(value)
        }
        return tree
    }
}