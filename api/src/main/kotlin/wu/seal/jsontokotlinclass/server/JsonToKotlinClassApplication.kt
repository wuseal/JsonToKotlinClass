package wu.seal.jsontokotlinclass.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JsonToKotlinClassApplication

fun main(args: Array<String>) {
	runApplication<JsonToKotlinClassApplication>(*args)
}
