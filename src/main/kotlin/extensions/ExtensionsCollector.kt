package extensions

import extensions.chen.biao.KeepAnnotationSupport
import extensions.ted.zeng.PropertyAnnotationLineSupport
import extensions.wu.seal.ClassNameSuffixSupport
import extensions.wu.seal.PropertyPrefixSupport
import extensions.wu.seal.PropertySuffixSupport

/**
 * extension collect, all extensions will be hold by this class's extensions property
 */
object ExtensionsCollector {
    /**
     * all extensions
     */
    val extensions = listOf(
        PropertyPrefixSupport,
        PropertySuffixSupport,
        KeepAnnotationSupport,
        PropertyAnnotationLineSupport,
        ClassNameSuffixSupport
    )
}
