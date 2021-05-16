# Descirption
Project contains tests on different levels:
- simple unit tests
- integration tests with MockMvc
- api tests with RestAssured
- testing of form submission with Selenium Webdriver

Pages: 
http://localhost:8080/users and http://localhost:8080/roles (not implemented)

API:
http://localhost:8080/api/users
http://localhost:8080/api/users?email={e-mail address}

# Run
Runs with H2 in memory database. 
Add application.properties to resources folders of main and test.

spring.datasource.url=jdbc:h2:mem:db;DB_CLOSE_DELAY=1
spring.datasource.username=sa
spring.datasource.password=sa
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update
