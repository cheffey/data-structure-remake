package remake.tree.bi_search.model

/**
 * Created by Chef.Xie on 2021-10-12
 */
open class Node<T>(var value: T) {
    var left: Node<T>? = null
    var right: Node<T>? = null
}