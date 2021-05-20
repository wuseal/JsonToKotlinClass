Enable this extension, you can create a kotlin data class with a static fuction that can be converted from jsonobject to instance classes.

e.g.
<br>
json:
```json
{
  "a":1,
  "b":"abc",
  "c":true
}
```

It can convert to the following kotlin data class

```kotlin
data class Test(
    val a: Int?,
    val b: String?,
    val c: Boolean?
) {

    companion object {
        @JvmStatic
        fun buildFromJson(jsonObject: JSONObject?): Test? {

            jsonObject?.run {
                return Test(
                    optInt("a"),
                    optString("b"),
                    optBoolean("c")
                )
            }
            return null
        }
    }
}
```

Then you can use this function to make a instance from a JSONObjcet, for example
```kotlin
val jsonObject: JSONObject = TestApi.rquestTest()
val test: Test = Test.buildFromJson(jsonObject)
```