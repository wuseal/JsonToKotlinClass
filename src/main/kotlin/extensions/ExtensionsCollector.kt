package extensions

import extensions.chen.biao.KeepAnnotationSupport
import extensions.jose.han.ParcelableAnnotationSupport
import extensions.ted.zeng.PropertyAnnotationLineSupport
import extensions.wu.seal.*
import extensions.xu.rui.PrimitiveTypeNonNullableSupport
import extensions.nstd.ReplaceConstructorParametersByMemberVariablesSupport
import extensions.yuan.varenyzc.BuildFromJsonObjectSupport
import wu.seal.jsontokotlin.interceptor.AnalyticsSwitchSupport
import extensions.yuan.varenyzc.CamelCaseSupport
import extensions.yuan.varenyzc.NeedNonNullableClassesSupport

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
        ClassNamePrefixSupport,
        ClassNameSuffixSupport,
        PrimitiveTypeNonNullableSupport,
        ForceInitDefaultValueWithOriginJsonValueSupport,
        DisableDataClassSupport,
        ReplaceConstructorParametersByMemberVariablesSupport,
        AnalyticsSwitchSupport,
        CamelCaseSupport,
        BuildFromJsonObjectSupport,
        NeedNonNullableClassesSupport,
        InternalModifierSupport,
        AddGsonExposeAnnotation,
    )
}
