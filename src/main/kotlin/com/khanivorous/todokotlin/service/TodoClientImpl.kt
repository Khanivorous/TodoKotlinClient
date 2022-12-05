package com.khanivorous.todokotlin.service

import com.khanivorous.todokotlin.model.Todo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class TodoClientImpl @Autowired constructor(restTemplate: RestTemplate) : TodoClient {

    private val restTemplate: RestTemplate

    init {
        this.restTemplate = restTemplate
    }

    override fun getTodoById(id: Int): Todo? {
        return restTemplate.getForObject("/posts/$id", Todo::class.java)
    }
}