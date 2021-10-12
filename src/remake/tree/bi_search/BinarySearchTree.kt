package remake.tree.bi_search

import remake.tree.bi_search.model.Node
import remake.util.Math.pow
import java.lang.StringBuilder
import java.util.function.Consumer
import java.util.function.Supplier

/**
 * Created by Chef.Xie on 2021-10-12
 */
class BinarySearchTree {
    var root: Node<Int>? = null

    fun contains(target: Int): Boolean {
        val rootModifier = NodeModifier({ root }, { root = it })
        return findFrom0(target, rootModifier).get() != null
    }

    fun insert(target: Int) {
        val root0 = root
        if (root0 == null) {
            root = Node(target)
            return
        }
        insertFrom0(root0, target)
    }

    fun remove(target: Int) {
        val rootModifier = NodeModifier({ root }, { root = it })
        val targetModifier = findFrom0(target, rootModifier)
        val targetNode = targetModifier.get()
        if (targetNode == null) throw RuntimeException("target: $target, is NOT found")
        val mergedNode = merge(targetNode.right, targetNode.left)
        targetModifier.set(mergedNode)
    }

    private fun merge(node1: Node<Int>?, node2: Node<Int>?): Node<Int>? {
        if (node1 == null) return node2
        if (node2 == null) return node1
        val (bigger, smaller) = if (node1.value > node2.value) Pair(node1, node2) else Pair(node2, node1)
        smaller.right = merge(bigger, smaller.right)
        return smaller
    }

    fun isVerified(): Boolean {
        return verify0(root, ValueRange(null, null))
    }

    fun values(): List<Int> {
        val result = mutableListOf<Node<Int>>()
        var currentLayer = listOfNotNull(root)
        val splitNextLayer={ node: Node<Int>?-> listOfNotNull(node?.left, node?.right)}
        while (currentLayer.isNotEmpty()) {
            result += currentLayer
            currentLayer = currentLayer.flatMap(splitNextLayer)
        }
        return result.map { it.value }
    }

    private fun verify0(node: Node<Int>?, valueRange: ValueRange): Boolean {
        if (node == null) {
            return true
        }
        val minExcluded = valueRange.minExcluded
        val maxExcluded = valueRange.maxExcluded
        if (minExcluded != null && minExcluded >= node.value) {
            return false
        }
        if (maxExcluded != null && maxExcluded <= node.value) {
            return false
        }
        return verify0(node.left, ValueRange(node.value, minExcluded))
            && verify0(node.right, ValueRange(maxExcluded, node.value))
    }

    private fun insertFrom0(node: Node<Int>, target: Int) {
        when {
            node.value == target -> throw RuntimeException("duplicateNode $target")
            node.value < target -> {
                val right = node.right
                if (right == null) node.right = Node(target)
                else insertFrom0(right, target)
            }
            else -> {
                val left = node.left
                if (left == null) node.left = Node(target)
                else insertFrom0(left, target)
            }
        }
    }

    private fun findFrom0(target: Int, modifier: NodeModifier<Int>): NodeModifier<Int> {
        val node = modifier.get()
        if (node == null) return modifier
        return when {
            node.value == target -> modifier
            node.value < target -> findFrom0(target, NodeModifier({ node.right }, { node.right = it }))
            else -> findFrom0(target, NodeModifier({ node.left }, { node.left = it }))
        }
    }

    override fun toString(): String {
        val lines = mutableListOf<List<String>>()
        var currentNodes = listOf(root)
        while (currentNodes.filterNotNull().isNotEmpty()) {
            val line = currentNodes.map {
                when {
                    it == null -> "[null]"
                    it.value < 10 -> "[   ${it.value}]"
                    it.value < 100 -> "[  ${it.value}]"
                    it.value < 1000 -> "[ ${it.value}]"
                    else -> "[${it.value}]"
                }
            }
            lines.add(line)
            currentNodes = currentNodes.flatMap { listOf(it?.left, it?.right) }
        }
        var intervalLength = pow(2, lines.size + 1)
        val builder = StringBuilder()
        for (line in lines) {
            val headLength = intervalLength / 2 - 4
            val head = " ".repeat(headLength)
            val interval = " ".repeat(intervalLength - 6)
            builder.append(head).append(line.reduce { a, b -> "$a$interval$b" }).append('\n')
            intervalLength /= 2
        }
        return builder.toString()
    }
}

class ValueRange(var maxExcluded: Int?, var minExcluded: Int?)

class NodeModifier<T>(private val getter: Supplier<Node<T>?>, private val setter: Consumer<Node<T>?>) {
    fun get(): Node<T>? = getter.get()
    fun set(newValue: Node<T>?) = setter.accept(newValue)
}
