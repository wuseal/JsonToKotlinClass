package wu.seal.jsontokotlin.model.classscodestruct

interface ModifiableKotlinClass : KotlinClass {

    override val modifiable: Boolean
        get() = true


}