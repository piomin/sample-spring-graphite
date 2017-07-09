package pl.piomin.graphite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import pl.piomin.graphite.service.FirstService;
import pl.piomin.graphite.service.SecondService;

@Component
public class Start implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private FirstService service1;
	@Autowired
	private SecondService service2;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		System.out.println("Hello");
//		service1.exampleMethod();
//		service2.exampleMethod();
	}
	
}