package wu.seal.jsontokotlin.bean.jsonschema

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import thirdparties.RuntimeTypeAdapterFactory

/**
 * Created by kezhenxu94 at 2019/4/17 16:14.
 *
 * @author kezhenxu94 (kezhenxu94 at 163 dot com)
 */
val GSON: Gson = GsonBuilder()
  .registerTypeAdapterFactory(
    RuntimeTypeAdapterFactory.of(PropertyDef::class.java)
      .registerSubtype(ObjectPropertyDef::class.java, "object")
      .registerSubtype(StringPropertyDef::class.java, "string")
      .registerSubtype(EnumPropertyDef::class.java, "enum")
      .registerSubtype(IntPropertyDef::class.java, "integer")
      .registerSubtype(NumberPropertyDef::class.java, "number")
      .registerSubtype(BoolPropertyDef::class.java, "boolean")
      .registerSubtype(ArrayPropertyDef::class.java, "array")
  ).create()
