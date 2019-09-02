package wu.seal.jsontokotlin.ui

import javax.swing.text.AttributeSet
import javax.swing.text.PlainDocument

/**
 * Created by ted on 2019/8/21 11:08.
 */
class NamingConventionDocument(maxLength: Int) : PlainDocument() {
    constructor() : this(252)

    private val maxLength: Int = if (maxLength > 252 || maxLength <= 0) 252 else maxLength
    override fun insertString(offs: Int, str: String?, a: AttributeSet?) {
        str ?: return
        val take = maxLength - length
        if (take <= 0) return
        super.insertString(
                offs,
                str.filter { it.isLetterOrDigit() || it in listOf('_', '$') }.take(take),
                a
        )
    }
}