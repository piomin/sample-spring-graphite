package pl.piomin.graphite;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
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

	protected Logger logger = Logger.getLogger(GraphiteApplicationTest.class.getName());

	@Container
	private static final MySQLContainer MYSQL = new MySQLContainer()
			.withUsername("datagrid")
			.withPassword("datagrid");

	@Container
	private static final GenericContainer INFLUXDB = new GenericContainer("influxdb:2.5.1")
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
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					for (int j = 0; j < 100; j++) {
						try {
							int ix = new Random().nextInt(100000);
							Person p = new Person();
							p.setFirstName("Jan" + ix);
							p.setLastName("Testowy" + ix);
							p.setPesel(new DecimalFormat("0000000").format(ix) + new DecimalFormat("000").format(ix%100));
							p.setAge(ix%100);
							p = template.postForObject("/persons", p, Person.class);
							logger.info(String.format("New person: %s", p));
							
							int ix2 = new Random().nextInt(100000);
							p = template.getForObject("/persons/{id}", Person.class, ix2);
							p.setAge(ix%100);
							template.put("/persons/update", p);
							logger.info(String.format("Person updated: %s with age=%d", p, ix%100));
							
							int ix3 = new Random().nextInt(100000);
							template.delete("/persons/remove/{id}", ix3);
						} catch (Exception e) {
							
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
//		try {
//			Thread.sleep(500000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
	}
}
