package extensions

import extensions.chen.biao.KeepAnnotationSupport
import extensions.jose.han.ParcelableAnnotationSupport
import extensions.ted.zeng.PropertyAnnotationLineSupport
import extensions.wu.seal.*
import extensions.xu.rui.PrimitiveTypeNonNullableSupport

/**
 * extension collect, all extensions will be hold by this class's extensions property
 */
object ExtensionsCollector {
    /**
     * all extensions
     */
    val extensions = listOf(
            KeepAnnotationSupport,
            KeepAnnotationSupportForAndroidX,
            PropertyAnnotationLineSupport,
            ParcelableAnnotationSupport,
            PropertyPrefixSupport,
            PropertySuffixSupport,
            ClassNameSuffixSupport,
            PrimitiveTypeNonNullableSupport,
            ForceInitDefaultValueWithOriginJsonValueSupport,
            DisableDataClassSupport
    )
}
