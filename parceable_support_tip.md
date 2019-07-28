#### Use Annotation

##### 1.add in gradle file

```
android {
    ...

    //Using Kotlin experimental characteristics
    //You only need this for Kotlin < 1.3.40    
    androidExtensions {
        experimental = true
    }
}
```

##### 2. Marking Data Classes with Annotations and Implementing Parcelable Interface

```
@SuppressLint("ParcelCreator")
@Parcelize
data class User(val name: String, val age: Int) : Parcelable
```
