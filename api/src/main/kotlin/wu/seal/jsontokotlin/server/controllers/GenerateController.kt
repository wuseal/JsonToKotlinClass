package wu.seal.jsontokotlin.server.controllers

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import wu.seal.jsontokotlin.DefaultValueStrategy
import wu.seal.jsontokotlin.PropertyTypeStrategy
import wu.seal.jsontokotlin.TargetJsonConverter
import wu.seal.jsontokotlin.library.JsonToKotlinBuilder
import wu.seal.jsontokotlin.server.routes.generate.GenerateRequest
import wu.seal.jsontokotlin.server.routes.generate.GenerateResponse

@Controller
class GenerateController {

    @PostMapping("/api/generate")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    fun generate(@RequestBody request: GenerateRequest): GenerateResponse {

        val builder = JsonToKotlinBuilder()

        // Integrating REST request params with builder class

        if (request.annotationLib != null) {
            builder.setAnnotationLib(TargetJsonConverter.valueOf(request.annotationLib))
        }

        if (request.classSuffix != null) {
            builder.setClassSuffix(request.classSuffix)
        }

        if (request.defaultValueStrategy != null) {
            builder.setDefaultValueStrategy(DefaultValueStrategy.valueOf(request.defaultValueStrategy))
        }

        if (request.indent != null) {
            builder.setIndent(request.indent)
        }

        if (request.isCommentsEnabled != null) {
            builder.enableComments(request.isCommentsEnabled)
        }

        if (request.isCreateAnnotationOnlyWhenNeededEnabled != null) {
            builder.enableCreateAnnotationOnlyWhenNeeded(request.isCreateAnnotationOnlyWhenNeededEnabled)
        }

        if (request.isEnableVarProperties != null) {
            builder.enableVarProperties(request.isEnableVarProperties)
        }

        if (request.isForceInitDefaultValueWithOriginJsonValueEnabled != null) {
            builder.enableForceInitDefaultValueWithOriginJsonValue(request.isForceInitDefaultValueWithOriginJsonValueEnabled)
        }

        if (request.isForcePrimitiveTypeNonNullableEnabled != null) {
            builder.enableForcePrimitiveTypeNonNullable(request.isForcePrimitiveTypeNonNullableEnabled)
        }

        if (request.isInnerClassModelEnabled != null) {
            builder.enableInnerClassModel(request.isInnerClassModelEnabled)
        }

        if (request.isKeepAnnotationOnClassAndroidXEnabled != null) {
            builder.enableKeepAnnotationOnClassAndroidX(request.isKeepAnnotationOnClassAndroidXEnabled)
        }

        if (request.isKeepAnnotationOnClassEnabled != null) {
            builder.enableKeepAnnotationOnClass(request.isKeepAnnotationOnClassEnabled)
        }

        if (request.isMapTypeEnabled != null) {
            builder.enableMapType(request.isMapTypeEnabled)
        }

        if (request.isOrderByAlphabeticEnabled != null) {
            builder.enableOrderByAlphabetic(request.isOrderByAlphabeticEnabled)
        }

        if (request.isParcelableSupportEnabled != null) {
            builder.enableParcelableSupport(request.isParcelableSupportEnabled)
        }

        if (request.isPropertyAndAnnotationInSameLineEnabled != null) {
            builder.enableAnnotationAndPropertyInSameLine(request.isPropertyAndAnnotationInSameLineEnabled)
        }

        if (request.packageName != null) {
            builder.setPackageName(request.packageName)
        }

        if (request.parentClassTemplate != null) {
            builder.setParentClassTemplate(request.parentClassTemplate)
        }

        if (request.propertyPrefix != null) {
            builder.setPropertyPrefix(request.propertyPrefix)
        }

        if (request.propertySuffix != null) {
            builder.setPropertySuffix(request.propertyPrefix)
        }

        if (request.propertyTypeStrategy != null) {
            builder.setPropertyTypeStrategy(PropertyTypeStrategy.valueOf(request.propertyTypeStrategy))
        }

        val json = builder.build(request.json, request.className)
        return GenerateResponse(
                GenerateResponse.Data(json),
                false,
                -1,
                "OK"
        )
    }
}
