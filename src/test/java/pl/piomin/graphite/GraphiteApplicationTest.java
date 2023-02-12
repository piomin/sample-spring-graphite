package pl.piomin.graphite;

import java.text.DecimalFormat;
import java.util.Random;

import io.micrometer.core.instrument.MeterRegistry;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.piomin.graphite.service.model.Person;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class GraphiteApplicationTest {

	protected final Logger LOGGER = LoggerFactory.getLogger(GraphiteApplicationTest.class);

	@Container
	private static final MySQLContainer MYSQL = new MySQLContainer()
			.withUsername("datagrid")
			.withPassword("datagrid");

	@Container
	private static final GenericContainer INFLUXDB = new GenericContainer("influxdb:2.5.1")
			.withEnv("DOCKER_INFLUXDB_INIT_USERNAME", "test")
			.withEnv("DOCKER_INFLUXDB_INIT_PASSWORD", "test")
			.withExposedPorts(8086);

	@DynamicPropertySource
	static void mysqlProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
		String influxUri = "http://localhost:" + INFLUXDB.getFirstMappedPort();
		registry.add("management.metrics.export.influx.uri", () -> influxUri);
	}

	@Autowired
	TestRestTemplate template;

	Random r = new Random();
	
	@Test
	public void testRest() {
		for (int i = 0; i < 100; i++) {
			try {
				int ix = new Random().nextInt(100000);
				Person p = new Person();
				p.setFirstName("Jan" + ix);
				p.setLastName("Testowy" + ix);
				p.setPesel(new DecimalFormat("0000000").format(ix) + new DecimalFormat("000").format(ix%100));
				p.setAge(ix%100);
				p = template.postForObject("/persons", p, Person.class);
				LOGGER.info("New person: {}", p);

				p = template.getForObject("/persons/{id}", Person.class, p.getId());
				p.setAge(ix%100);
				template.put("/persons/update", p);
				LOGGER.info("Person updated: {} with age={}", p, p.getId());

			} catch (Exception e) {

			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			};

		}

//		InfluxDB influx = InfluxDBFactory.connect("http://localhost:" + INFLUXDB.getFirstMappedPort(), "test", "test");
//		QueryResult queryResult = influx.query(new Query("SELECT * FROM hello3"));
//		System.out.println("QQQ --->" + queryResult);
	}
}
