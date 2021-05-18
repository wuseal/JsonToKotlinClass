package wu.seal.jsontokotlin.utils

import java.io.IOException
import java.io.StringWriter
import java.io.Writer

/**
 * Escape character Util
 * Created by Jose.Han on 2020/7/14.
 */
object StringEscapeUtils {
    fun escapeJava(str: String): String? {
        return escapeJavaStyleString(str, false)
    }

    private fun escapeJavaStyleString(str: String?, escapeSingleQuotes: Boolean): String? {
        return if (str == null) {
            null
        } else {
            try {
                val writer = StringWriter(str.length * 2)
                escapeJavaStyleString(writer, str, escapeSingleQuotes)
                writer.toString()
            } catch (var3: IOException) {
                var3.printStackTrace()
                null
            }

        }
    }

    @Throws(IOException::class)
    private fun escapeJavaStyleString(out: Writer?, str: String?, escapeSingleQuote: Boolean) {
        if (out == null) {
            throw IllegalArgumentException("The Writer must not be null")
        } else if (str != null) {
            val sz = str.length

            for (i in 0 until sz) {
                val ch = str[i]
                if (ch.toInt() > 4095) {
                    out.write("\\u" + hex(ch))
                } else if (ch.toInt() > 255) {
                    out.write("\\u0" + hex(ch))
                } else if (ch.toInt() > 127) {
                    out.write("\\u00" + hex(ch))
                } else if (ch < ' ') {
                    when (ch) {
                        '\b' -> {
                            out.write(92)
                            out.write(98)
                        }
                        '\t' -> {
                            out.write(92)
                            out.write(116)
                        }
                        '\n' -> {
                            out.write(92)
                            out.write(110)
                        }
                        '\u000b' -> if (ch.toInt() > 15) {
                            out.write("\\u00" + hex(ch))
                        } else {
                            out.write("\\u000" + hex(ch))
                        }
                        '\r' -> {
                            out.write(92)
                            out.write(114)
                        }
                        else -> if (ch.toInt() > 15) {
                            out.write("\\u00" + hex(ch))
                        } else {
                            out.write("\\u000" + hex(ch))
                        }
                    }
                } else {
                    when (ch) {
                        '"' -> {
                            out.write(92)
                            out.write(34)
                        }
                        '\'' -> {
                            if (escapeSingleQuote) {
                                out.write(92)
                            }

                            out.write(39)
                        }
                        '/' -> {
                            out.write(92)
                            out.write(47)
                        }
                        '\\' -> {
                            out.write(92)
                            out.write(92)
                        }
                        else -> out.write(ch.toInt())
                    }
                }
            }

        }
    }

    private fun hex(ch: Char): String {
        return Integer.toHexString(ch.toInt()).toUpperCase()
    }

    fun unescapeJava(str: String?): String? {
        return if (str == null) {
            null
        } else {
            try {
                val writer = StringWriter(str.length)
                unescapeJava(writer, str)
                writer.toString()
            } catch (var2: IOException) {
                var2.printStackTrace()
                null
            }

        }
    }

    @Throws(IOException::class)
    fun unescapeJava(out: Writer?, str: String?) {
        if (out == null) {
            throw IllegalArgumentException("The Writer must not be null")
        } else if (str != null) {
            val sz = str.length
            val unicode = StringBuffer(4)
            var hadSlash = false
            var inUnicode = false

            for (i in 0 until sz) {
                val ch = str[i]
                if (inUnicode) {
                    unicode.append(ch)
                    if (unicode.length == 4) {
                        try {
                            val value = Integer.parseInt(unicode.toString(), 16)
                            out.write(value.toChar().toInt())
                            unicode.setLength(0)
                            inUnicode = false
                            hadSlash = false
                        } catch (var9: NumberFormatException) {
                            throw RuntimeException("Unable to parse unicode value: $unicode", var9)
                        }

                    }
                } else if (hadSlash) {
                    hadSlash = false
                    when (ch) {
                        '"' -> out.write(34)
                        '\'' -> out.write(39)
                        '\\' -> out.write(92)
                        'b' -> out.write(8)
                        'f' -> out.write(12)
                        'n' -> out.write(10)
                        'r' -> out.write(13)
                        't' -> out.write(9)
                        'u' -> inUnicode = true
                        else -> out.write(ch.toInt())
                    }
                } else if (ch == '\\') {
                    hadSlash = true
                } else {
                    out.write(ch.toInt())
                }
            }

            if (hadSlash) {
                out.write(92)
            }

        }
    }
}