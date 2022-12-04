package com.khanivorous.todokotlin

import com.khanivorous.todokotlin.model.Todo
import com.khanivorous.todokotlin.service.TodoClientImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.client.RestTemplate

@ExtendWith(MockitoExtension::class)
class TodoKotlinClientImplUnitTest {
    @Mock
    var restTemplate: RestTemplate? = null
    var serviceUnderTest: TodoClientImpl? = null
    @BeforeEach
    fun setUp() {
        serviceUnderTest = TodoClientImpl(restTemplate!!)
    }

    @Test
    fun todoById() {
            val mockResponse = Todo(
                1,
                1,
                "Test title",
                "Test body"
            )
            `when`(restTemplate!!.getForObject("/posts/1", Todo::class.java))
                .thenReturn(mockResponse)
            val response = serviceUnderTest!!.getTodoById(1)
            Assertions.assertEquals(1, response!!.id)
            Assertions.assertEquals("Test body", response.body)
        }
}