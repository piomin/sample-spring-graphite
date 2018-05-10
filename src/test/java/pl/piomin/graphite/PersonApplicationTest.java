package pl.piomin.graphite;

import java.text.DecimalFormat;
import java.util.Random;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;

import pl.piomin.graphite.service.model.Person;

public class PersonApplicationTest {

	private static Logger LOGGER = LoggerFactory.getLogger(PersonApplicationTest.class);

	TestRestTemplate template = new TestRestTemplate();
	Random r = new Random();
	
	@Test
	public void testRest() {
		for (int i = 0; i < 30; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					for (int j = 0; j < 10000; j++) {
						try {
							int ix = new Random().nextInt(100000);
							Person p = new Person();
							p.setFirstName("Jan" + ix);
							p.setLastName("Testowy" + ix);
							p.setPesel(new DecimalFormat("0000000").format(ix) + new DecimalFormat("000").format(ix%100));
							p.setAge(ix%100);
							p = template.postForObject("http://localhost:2222/persons", p, Person.class);
							LOGGER.info("New person: {}", p);
							
							p = template.getForObject("http://localhost:2222/persons/{id}", Person.class, p.getId());
							p.setAge(ix%100);
							template.put("http://localhost:2222/persons", p);
							LOGGER.info("Person updated: {} with age={}", p, ix%100);
							
							template.delete("http://localhost:2222/persons/{id}", p.getId());
						} catch (Exception e) {
							
						}
						int sleep = new Random().nextInt(10000);
						try {
							Thread.sleep(sleep);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
