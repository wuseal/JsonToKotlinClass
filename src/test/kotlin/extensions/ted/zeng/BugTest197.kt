package extensions.ted.zeng

import com.winterbe.expekt.should
import org.junit.Test

/**
 * Created by ted on 2019-07-26 20:00.
 */
class BugTest197 {
    private fun getClassName(name: String): String {
        return name.let { if (it.first().isDigit() || it.contains('$')) "`$it`" else it }
    }

    @Test
    fun getClassName() {
        getClassName("abcd").should.equal("abcd")
        getClassName("abc\$d").should.equal("`abc\$d`")
        getClassName("1abcd").should.equal("`1abcd`")
        getClassName("abcd1").should.equal("abcd1")
        getClassName("abc_d1").should.equal("abc_d1")
    }

    @Test
    fun firstClassNameCharTest() {
        val chars = listOf('a', '_', '1', 'I', 'V', 'X', 'L', 'C', 'D', 'M', '@',
                'β', '$', '+', ';', '.', '\\',
                '/', ' ', '\t', '\n', '#', '￥',
                '<', ',', '>', '-', '*', '&', '|',
                ':', ';', '"', '\'', '∑', '☑', '⒈',
                'Ⅰ', 'I', '你', 'Д', 'ㅙ', 'す', 'ú',
                'Φ', 'Ⅱ', 'Ⅲ', '❶', 'α', 'δ', 'À', 'û')
        val (positive, negative) = chars.partition { it.isLetterOrDigit() || it in listOf('_', '$')  }
        println(positive)
        println(negative)
    }

}
