package wu.seal.jsontokotlin.bean.jsonschema

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

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
  val properties: Map<String, JsonSchema> = emptyMap(),
  @SerializedName("items")
  val itemsOfArray: JsonElement? = null,
  val required: Array<String> = emptyArray()
) : PropertyDef


