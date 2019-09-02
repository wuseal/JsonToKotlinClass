package wu.seal.jsontokotlin.utils

/**
 * Created by Seal.Wu on 2019-08-20
 * Description: Set ignore the String Case
 */
class IgnoreCaseStringSet(override val size: Int = 4) : MutableSet<String> {

    private val stringSet = mutableSetOf<String>()

    override fun add(element: String): Boolean {
        return stringSet.add(element.toLowerCase())
    }

    override fun addAll(elements: Collection<String>): Boolean {
        return stringSet.addAll(elements.map { it.toLowerCase() })
    }

    override fun clear() {
        stringSet.clear()
    }

    override fun contains(element: String): Boolean {
        return stringSet.contains(element.toLowerCase())
    }

    override fun containsAll(elements: Collection<String>): Boolean {
        return stringSet.containsAll(elements.map { it.toLowerCase() })
    }

    override fun isEmpty(): Boolean {
        return stringSet.isEmpty()
    }

    override fun iterator(): MutableIterator<String> {
        return stringSet.iterator()
    }

    override fun remove(element: String): Boolean {
        return stringSet.remove(element.toLowerCase())
    }

    override fun removeAll(elements: Collection<String>): Boolean {
        return stringSet.removeAll(elements.map { it.toLowerCase() })
    }

    override fun retainAll(elements: Collection<String>): Boolean {
        return stringSet.retainAll(elements.map { it.toLowerCase() })
    }

}