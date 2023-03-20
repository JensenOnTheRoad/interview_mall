package demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.Gson;
import demo.SpringBootBaseTest.DockerMysqlDataSourceInitializer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;

@SpringBootTest
@Transactional
@ContextConfiguration(initializers = DockerMysqlDataSourceInitializer.class)
@AutoConfigureMockMvc(addFilters = false)
public abstract class SpringBootBaseTest {
  private static final MySQLContainer<?> MYSQL_DB;
  public static MockHttpSession session = new MockHttpSession();

  static {
    MYSQL_DB = new MySQLContainer<>("mysql:8.0.28").withReuse(true);
    MYSQL_DB.start();
  }

  @Autowired private MockMvc mockMvc;
  @Autowired private Gson gson;

  public static String simpleFormat(LocalDateTime localDateTime) {
    return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }

  public MockMvc getMockMvc() {
    return mockMvc;
  }

  public Gson getGson() {
    return gson;
  }

  @Test
  void test() {
    assertTrue(MYSQL_DB.isRunning());
  }

  // This class adds the DB properties to Testcontainers.
  public static class DockerMysqlDataSourceInitializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
      TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
          applicationContext,
          "oss.active=false",
          "spring.datasource.url=" + MYSQL_DB.getJdbcUrl(),
          "spring.datasource.username=" + MYSQL_DB.getUsername(),
          "spring.datasource.password=" + MYSQL_DB.getPassword(),
          "spring.datasource.driver-class-name=" + MYSQL_DB.getDriverClassName());
    }
  }
}
