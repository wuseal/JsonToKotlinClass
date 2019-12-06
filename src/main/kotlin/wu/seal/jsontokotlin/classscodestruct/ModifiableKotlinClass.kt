package wu.seal.jsontokotlin.classscodestruct

interface ModifiableKotlinClass : KotlinClass {

    override val modifiable: Boolean
        get() = true


}