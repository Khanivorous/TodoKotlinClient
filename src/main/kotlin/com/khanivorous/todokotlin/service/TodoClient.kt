package com.khanivorous.todokotlin.service

import com.khanivorous.todokotlin.model.Todo
import org.springframework.stereotype.Service

@Service
interface TodoClient {
    fun getTodoById(id: Int): Todo?
}