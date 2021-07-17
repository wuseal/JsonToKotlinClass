Enable this extension and type the classes' names into the input area, you can make the classes you typed non-nullable. 

In the text area, the classes should split with ','.

Such as
```
Int,Double,Boolean,Long,List
```
In particular, the ``List<Any>`` only need to type ``List``

e.g.
<br>
json:
```json
{
  "a": 1,
  "b": true,
  "c": "hhhh",
  "d": [
    "a","b","c","d"
  ],
  "e": [
    1,2,3,4
  ]
}
```

It can convert to the following kotlin data class
```kotlin
data class Test(
    val a: Int,
    val b: Boolean,
    val c: String?,
    val d: List<String?>,
    val e: List<Int>
)
```