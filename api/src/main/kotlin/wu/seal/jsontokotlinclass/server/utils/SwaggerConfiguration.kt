package wu.seal.jsontokotlinclass.server.utils

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfiguration {
    @Bean
    open fun api(): Docket {

        val apiInfo = ApiInfoBuilder()
                .title("JsonToKotlin Api")
                .description("This is a simple API to access J2K services online.")
                .build()

        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("wu.seal.jsontokotlinclass.server"))
                .paths(PathSelectors.any())
                .build()
    }


}