package wu.seal.jsontokotlin.library

fun main(args: Array<String>) {

    val x = JsonToKotlinBuilder().build("""
        {
        "name":"Shifar"
        }
    """.trimIndent(), "Person")

    println(x)
}