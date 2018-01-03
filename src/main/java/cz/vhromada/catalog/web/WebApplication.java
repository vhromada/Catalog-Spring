package cz.vhromada.catalog.web;

import cz.vhromada.catalog.CatalogConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * A class represents Spring boot application.
 *
 * @author Vladimir Hromada
 */
@SpringBootApplication
@Import(CatalogConfiguration.class)
public class WebApplication implements WebMvcConfigurer {

    /**
     * Main method.
     *
     * @param args the command line arguments
     */
    //CHECKSTYLE.OFF: UncommentedMain
    public static void main(final String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
    //CHECKSTYLE.OFF: UncommentedMain

}
