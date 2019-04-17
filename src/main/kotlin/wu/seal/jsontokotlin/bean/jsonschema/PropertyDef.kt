package wu.seal.jsontokotlin.bean.jsonschema

import java.util.*

/**
 * Created by kezhenxu94 at 2019/4/16 13:07.
 *
 * Represents the definition in JSON Schema
 *
 * @author kezhenxu94 (kezhenxu94 at 163 dot com)
 */
interface PropertyDef

open class ObjectPropertyDef(
  val description: String? = "",
  val type: String? = "",
  val properties: Map<String, PropertyDef> = emptyMap(),
  val required: Array<String> = emptyArray()
) : PropertyDef

open class IntPropertyDef : ObjectPropertyDef()

open class NumberPropertyDef : ObjectPropertyDef()

open class BoolPropertyDef : ObjectPropertyDef()

open class StringPropertyDef : ObjectPropertyDef()

open class EnumPropertyDef : ArrayList<String>(), PropertyDef

open class ArrayPropertyDef(
  val items: PropertyDef
) : ObjectPropertyDef(), PropertyDef
