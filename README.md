# Application Overview
This is a simple spring client application written in `Kotlin`.
It uses RestTemplate to do a client call to the https://jsonplaceholder.typicode.com endpoint
and returns the Todo object at a given id.

This project is designed to showcase a few simple ways of testing Spring applications that use RestTemplate.

## Running the application
If you want to run the app locally using docker, first, build the application jar using `gradle bootJar`, then
run the [docker-compose](docker-compose.yml) file with `docker compose up`.

You can access the swagger ui at http://localhost:8080/swagger-ui/index.html where you can do simple get requests.

## Tests

You will notice the tests that spin up the actual application use two different property files with 2 different urls set. This is to demo how we can write various tests for various environments easily within spring.

- [TodoKotlinIntegrationTest](src/test/kotlin/com/khanivorous/todokotlin/TodoKotlinIntegrationTest.kt)
  - This spins up the application and tests without any mocks
  - Takes the url properties from [application-integration-test.properties](src/test/resources/application-integration-test.properties)
- [TodoKotlinApplicationTests](src/test/kotlin/com/khanivorous/todokotlin/TodoKotlinApplicationTests.kt)
  - This spins up the application and mocks the rest response it receives using springs `MockRestServiceServer` 
  - Takes the url properties from [application-test.properties](src/test/resources/application-test.properties)
- [TodoKotlinClientImplMockServerTest](src/test/kotlin/com/khanivorous/todokotlin/TodoKotlinClientImplMockServerTest.kt)
  - This is a unit test of the TodoClientImpl class. The application does _not_ spin up
  - This uses springs `MockRestServiceServer` to mock the rest response from the RestTemplate call
- [TodoKotlinClientImplUnitTest](src/test/kotlin/com/khanivorous/todokotlin/TodoKotlinClientImplUnitTest.kt)
  - This is a unit test of the TodoClientImpl class. The application does _not_ spin up
  - This just uses Mockito to Mock the response from the RestTemplate call

## Xray Jira Junit reports

An `xray/report` file is created after each test run. To avoid uploading test results for _all_ tests and only selected tests,
we can run a particular test task. 

- In this case, the [TodoKotlinIntegrationTest](src/test/kotlin/com/khanivorous/todokotlin/TodoKotlinIntegrationTest.kt) contains a method annotated with `@XrayTest(key = "KHAN-41")`.
When we run the integration test task `tasks.register<Test>("integrationTests")` in [build.gradle.kts](build.gradle.kts), this produces and `xray-report` file with only the results from that
test task.

- We can then run [upload-xray.sh](upload-xray.sh) which will upload the test results to xray jira and create a new test execution with those results. We can then choose to create a pipeline that runs this test and this script, allowing us to separate the tests we _want_ to tag for xray jira.

For xray reference see also:
- [xray jira extension github](https://github.com/Xray-App/xray-junit-extensions)
- [xray jira extension example](https://github.com/Xray-App/tutorial-java-junit5-selenium)
- [xray junit tutorial](https://docs.getxray.app/display/XRAYCLOUD/Testing+web+applications+using+Selenium+and+JUnit5+in+Java)
- [xray authentication](https://docs.getxray.app/display/XRAYCLOUD/Authentication+-+REST+v2)
- [xray upload test results](https://docs.getxray.app/display/XRAYCLOUD/Import+Execution+Results+-+REST+v2#ImportExecutionResultsRESTv2-JUnitXMLresults)