package com.khanivorous.todokotlin

import com.khanivorous.todokotlin.model.Todo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.web.client.RestTemplate
import java.io.IOException


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Tag("Application")
@TestPropertySource("classpath:application-test.properties")
internal class TodoKotlinApplicationTests {

    @Autowired
    var restTemplate: RestTemplate? = null

    @Autowired
    private val testRestTemplate: TestRestTemplate? = null

    @LocalServerPort
    var localServerPort = 0

    private var baseUrl: String? = null

    private var mockRestServiceServer: MockRestServiceServer? = null

    @BeforeEach
    fun setUp() {
        baseUrl = "http://localhost:$localServerPort"
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate!!)
    }

    @AfterEach
    fun tearDown() {
        mockRestServiceServer!!.reset()
    }

    @Throws(IOException::class)
    @Test
    fun todoById() {

        val dummyResponse: String = this::class.java.classLoader.getResource("todo/todoResponse.json")!!.readText()

        mockRestServiceServer!!.expect(MockRestRequestMatchers.requestTo("https://fakeuri/posts/1"))
            .andRespond(MockRestResponseCreators.withSuccess(dummyResponse, MediaType.APPLICATION_JSON))

        val response = testRestTemplate!!.getForEntity("$baseUrl/todo/1", Todo::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(1, response.body!!.id)
        assertEquals("test body id 1", response.body!!.body)
    }
}