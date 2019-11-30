import com.theapache64.j2k.JsonToKotlinBuilder
import wu.seal.jsontokotlin.*
import wu.seal.jsontokotlin.test.TestConfig

fun main() {

    val json1 = """{ "programmers": [
                { "isFirstName": "Brett", "lastName":"McLaughlin", "email": "aaaa" },
                { "firstName": "Jason", "lastName":"Hunter", "email": "bbbb" },
                { "firstName": "Elliotte", "lastName":"Harold", "email": "cccc" }
                ],
                "authors": [
                { "firstName": "Isaac", "lastName": "Asimov", "genre": "science fiction" },
                { "firstName": "Tad", "lastName": "Williams", "genre": "fantasy" },
                { "firstName": "Frank", "lastName": "Peretti", "genre": "christian fiction" }
                ],
                "musicians": [
                { "firstName": "Eric", "lastName": "Clapton", "instrument": "guitar" },
                { "firstName": "Sergei", "lastName": "Rachmaninoff", "instrument": "piano" }
                ] } """

    val output = JsonToKotlinBuilder()
            .setPropertiesVar(false) // optional, default : false
            .setPropertyTypeStrategy(PropertyTypeStrategy.AutoDeterMineNullableOrNot) // optional, default :  PropertyTypeStrategy.NotNullable
            .setDefaultValueStrategy(DefaultValueStrategy.AvoidNull) // optional, default : DefaultValueStrategy.AvoidNull
            .setAnnotationLib(TargetJsonConverter.MoshiCodeGen) // optional, default: TargetJsonConverter.Gson
            .setComment(true) // optional, default : true
            .setOrderByAlphabetic(true) // optional : default : true
            .setInnerClassModel(true) // optional, default : true
            .setMapType(true)// optional, default : false
            .setCreateAnnotationOnlyWhenNeeded(true) // optional, default : false
            .setIndent(4)// optional, default : 4
            .setParentClassTemplate("android.os.Parcelable") // optional, default : ""
            .setKeepAnnotationOnClass(true) // optional, default : false
            .setKeepAnnotationOnClassAndroidX(true) // optional, default : false
            .setKeepAnnotationAndPropertyInSameLine(true) // optional, default : false
            .setParcelableSupport(true) // optional, default : false
            .setPropertyPrefix("MyPrefix") // optional, default : ""
            .setPropertySuffix("MySuffix") // optional, default : ""
            .setClassSuffix("MyClassSuffix")// optional, default : ""
            .setForceInitDefaultValueWithOriginJsonValue(true) // optional, default : false
            .setForcePrimitiveTypeNonNullable(true) // optional, default : false
            .build(json1, "GlossResponse") // finally, get KotlinClassCode string

    println("json1 ====>\n${output}")
}
