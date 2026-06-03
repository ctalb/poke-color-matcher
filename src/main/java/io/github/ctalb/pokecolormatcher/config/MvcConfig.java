package io.github.ctalb.pokecolormatcher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MVC configuration class for serving static resources.
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${images.folder}")
    private String folder;

    /**
     * Maps the /images/** URL pattern to the images folder on disk,
     * allowing the frontend to access Pokémon images via HTTP.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/" + folder + "/**").addResourceLocations("file:" + folder + "/");
    }
}
