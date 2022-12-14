package pl.grzegorz.circuitservice;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class BaselineIntegrationTest {

    private static final String POSTGRES_IMAGE = "postgres:14:1";

    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(POSTGRES_IMAGE);

    @BeforeAll
    public static void setup() {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    public static void containerConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
}