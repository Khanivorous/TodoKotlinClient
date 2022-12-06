package com.khanivorous.todokotlin

import app.getxray.xray.junit.customjunitxml.annotations.Requirement
import app.getxray.xray.junit.customjunitxml.annotations.XrayTest
import com.khanivorous.todokotlin.model.Todo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Tag("Integration")
@TestPropertySource("classpath:application-integration-test.properties")
class TodoKotlinIntegrationTest {

    @Autowired
    private val testRestTemplate: TestRestTemplate? = null

    @LocalServerPort
    var localServerPort = 0
    private var baseUrl: String? = null

    @BeforeEach
    fun setUp() {
        baseUrl = "http://localhost:$localServerPort"
    }

    @XrayTest(
        key = "KHAN-1",
        summary = "Get Todo by id",
        description = "This gets the Todo response and checks the id matches"
    )
    @Requirement("KHAN-45", "KHAN-46")
    @Test
    fun todoById() {

        val response = testRestTemplate!!.getForEntity("$baseUrl/todo/1", Todo::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.statusCode)
        Assertions.assertEquals(1, response.body!!.id)
    }
}