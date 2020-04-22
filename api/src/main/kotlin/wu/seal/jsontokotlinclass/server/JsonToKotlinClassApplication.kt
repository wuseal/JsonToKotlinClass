package wu.seal.jsontokotlinclass.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class JsonToKotlinClassApplication

fun main(args: Array<String>) {
    SpringApplication.run(JsonToKotlinClassApplication::class.java, *args)
}
