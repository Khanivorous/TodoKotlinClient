package com.khanivorous.todokotlin.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.DefaultUriBuilderFactory

@Configuration
class RestTemplateConfiguration {

    @Value("\${todo.baseurl}")
    private val todoBaseUrl: String? = null

    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()
        restTemplate.uriTemplateHandler = DefaultUriBuilderFactory(todoBaseUrl!!)
        return restTemplate
    }

}