package com.khanivorous.todokotlin

import com.khanivorous.todokotlin.service.TodoClientImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.web.client.RestTemplate
import java.io.IOException


@Tag("Application")
class TodoKotlinClientImplMockServerTest {

    private val restTemplate = RestTemplate()
    private var mockRestServiceServer: MockRestServiceServer? = null
    var serviceUnderTest: TodoClientImpl? = null

    @BeforeEach
    fun setUp() {
        serviceUnderTest = TodoClientImpl(restTemplate)
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate)
    }

    @AfterEach
    fun tearDown() {
        mockRestServiceServer!!.reset()
    }

    @Throws(IOException::class)
    @Test
    fun todoById() {
        val dummyResponse: String = this::class.java.classLoader.getResource("todo/todoResponse.json")!!.readText()

        mockRestServiceServer!!.expect(MockRestRequestMatchers.requestTo("/posts/1"))
            .andRespond(MockRestResponseCreators.withSuccess(dummyResponse, MediaType.APPLICATION_JSON))

        val response = serviceUnderTest!!.getTodoById(1)

        assertEquals(1, response!!.id)
        assertEquals("test body id 1", response.body)
    }
}