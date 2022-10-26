package pl.grzegorz.motorcycleservice;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
@AutoConfigureMockMvc(addFilters = false)
@Transactional
public class BaseIntegrationTest {

    private static final String POSTGRES_IMAGE = "postgres:14:1";

    protected static GenericContainer<?> postgreSQLContainer;

    static {
        postgreSQLContainer = new GenericContainer<>(DockerImageName.parse(POSTGRES_IMAGE))
                .withNetworkAliases("postgres")
                .withExposedPorts(5432)
                .waitingFor(new LogMessageWaitStrategy()
                        .withRegEx(".database system is ready to accept connections.*\\s")
                        .withTimes(2)
                        .withStartupTimeout(Duration.of(60, SECONDS)))
                .withEnv("POSTGRES_USER", "test")
                .withEnv("POSTGRES_PASSWORD", "secret")
                .withEnv("POSTGRES_DB", "test");
                postgreSQLContainer.start();
    }

    @DynamicPropertySource
    public static void containerConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> String.format("jdbc:postgresql://%s:%d/test",
                postgreSQLContainer.getHost(), postgreSQLContainer.getMappedPort(5432)));
    }
}