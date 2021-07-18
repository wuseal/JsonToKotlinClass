#### Use Annotation

In Kotlin [1.4.20-M2](https://github.com/JetBrains/kotlin/releases/tag/v1.4.20-M2) JetBrains deprecated Kotlin Android Extensions compiler plugin  and used Kotlin Parcelize plugin

##### 1.add in gradle file

```
//apply plugin: 'kotlin-android-extensions'
apply plugin: 'kotlin-parcelize'
android{
   ...
}
```

##### 2. Marking Data Classes with Annotations and Implementing Parcelable Interface

```
@Parcelize
data class User(val name: String, val age: Int) : Parcelable
```