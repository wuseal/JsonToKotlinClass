# JsonToKoltinBuilder - Java/Kotlin Library

We also have a `Java/Kotlin` library. With this you can convert `JSON` to `Kotlin` code programmatically.

## Installation

Download `jar` file from [here](library/output/JsonToKotlinClassLibrary-3.5.1-alpha01.jar) and add it to your project.


If your project is not in `Kotlin`, don't forgot to add kotlin runtime dependency.

```groovy
// replace `1.3.61` with latest kotlin runtime version
implementation group: 'org.jetbrains.kotlin', name: 'kotlin-stdlib', version: '1.3.61'  
```

## Usage

```kotlin
val actualOutput = JsonToKotlinBuilder()
    .setPackageName("com.my.package.name")
    .enableVarProperties(false) // optional, default : false
    .setPropertyTypeStrategy(PropertyTypeStrategy.AutoDeterMineNullableOrNot) // optional, default :  PropertyTypeStrategy.NotNullable
    .setDefaultValueStrategy(DefaultValueStrategy.AvoidNull) // optional, default : DefaultValueStrategy.AvoidNull
    .setAnnotationLib(TargetJsonConverter.MoshiCodeGen) // optional, default: TargetJsonConverter.None
    .enableComments(true) // optional, default : false
    .enableOrderByAlphabetic(true) // optional : default : false
    .enableInnerClassModel(true) // optional, default : false
    .enabelMapType(true)// optional, default : false
    .enableCreateAnnotationOnlyWhenNeeded(true) // optional, default : false
    .setIndent(4)// optional, default : 4
    .setParentClassTemplate("android.os.Parcelable") // optional, default : ""
    .enableKeepAnnotationOnClass(true) // optional, default : false
    .enableKeepAnnotationOnClassAndroidX(true) // optional, default : false
    .enableAnnotationAndPropertyInSameLine(true) // optional, default : false
    .enableParcelableSupport(true) // optional, default : false
    .setPropertyPrefix("MyPrefix") // optional, default : ""
    .setPropertySuffix("MySuffix") // optional, default : ""
    .setClassSuffix("MyClassSuffix")// optional, default : ""
    .enableForceInitDefaultValueWithOriginJsonValue(true) // optional, default : false
    .enableForcePrimitiveTypeNonNullable(true) // optional, default : false
    .build(json1, "GlossResponse") // finally, get KotlinClassCode string
```

## Demo

**Code**

```kotlin

fun main() {


    val json1 = """{ "programmers": [
                { "isFirstName": "Brett", "lastName":"McLaughlin", "email": "aaaa" },
                { "firstName": "Jason", "lastName":"Hunter", "email": "bbbb" },
                { "firstName": "Elliotte", "lastName":"Harold", "email": "cccc" }
                ],
                "authors": [
                { "firstName": null, "lastName": "Asimov", "genre": "science fiction" },
                { "firstName": "Tad", "lastName": "Williams", "genre": "fantasy" },
                { "firstName": "Frank", "lastName": "Peretti", "genre": "christian fiction" }
                ],
                "musicians": [
                { "firstName": "Eric", "lastName": "Clapton", "instrument": "guitar" },
                { "firstName": "Sergei", "lastName": "Rachmaninoff", "instrument": "piano" }
                ] } """

    val output = JsonToKotlinBuilder()
            .build(json1, "GlossResponse") 

    println(output)
}
```

**Output**

```kotlin
data class GlossResponse(
    val programmers: List<Programmer>,
    val authors: List<Author>,
    val musicians: List<Musician>
)

data class Programmer(
    val isFirstName: String,
    val lastName: String,
    val email: String,
    val firstName: String
)

data class Author(
    val lastName: String,
    val genre: String,
    val firstName: String?
)

data class Musician(
    val firstName: String,
    val lastName: String,
    val instrument: String
)
```

## Custom Annotation

To set custom annotation, you can call the `JsonToKotlinBuilder#setCustomAnnotation` method.

```kotlin
val output = JsonToKotlinBuilder()
    .setCustomAnnotation(
        "import kotlinx.serialization.SerialName\n" +
                "import kotlinx.serialization.Serializable" + "\n" + "import kotlinx.serialization.Optional",
        "@Serializable",
        "@Optional\n@SerialName(\"%s\")"
    )
    .build(input, "MyResponse")
```