package cz.vhromada.catalog.web

import cz.vhromada.catalog.CatalogConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * A class represents Spring boot application.
 *
 * @author Vladimir Hromada
 */
@SpringBootApplication
@Import(CatalogConfiguration::class)
class WebApplication : WebMvcConfigurer

fun main(args: Array<String>) {
    SpringApplication.run(WebApplication::class.java, *args)
}
