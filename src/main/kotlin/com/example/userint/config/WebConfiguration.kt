package com.example.userint.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfiguration : WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**").allowedOrigins("*").allowedHeaders("*")
            .allowedMethods(
                RequestMethod.DELETE.name, RequestMethod.GET.name, RequestMethod.OPTIONS.name,
                RequestMethod.PATCH.name, RequestMethod.POST.name, RequestMethod.PUT.name
            )
            .maxAge(3600L)
    }
}
