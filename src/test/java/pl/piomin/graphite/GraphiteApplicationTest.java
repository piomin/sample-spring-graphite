package pl.piomin.graphite;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.logging.Logger;

import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;

import pl.piomin.graphite.service.model.Person;

public class GraphiteApplicationTest {

	protected Logger logger = Logger.getLogger(GraphiteApplicationTest.class.getName());

	TestRestTemplate template = new TestRestTemplate();
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
							p = template.postForObject("http://localhost:2222/persons", p, Person.class);
							logger.info(String.format("New person: %s", p));
							
							int ix2 = new Random().nextInt(100000);
							p = template.getForObject("http://localhost:2222/persons/{id}", Person.class, ix2);
							p.setAge(ix%100);
							template.put("http://localhost:2222/persons/update", p);
							logger.info(String.format("Person updated: %s with age=%d", p, ix%100));
							
							int ix3 = new Random().nextInt(100000);
							template.delete("http://localhost:2222/persons/remove/{id}", ix3);
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
