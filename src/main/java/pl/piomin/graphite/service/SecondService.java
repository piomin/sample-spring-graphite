package pl.piomin.graphite.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SecondService {

	private Counter counter;

	public SecondService(MeterRegistry meterRegistry) {
		counter = meterRegistry.counter("hello3", "hello");
	}

	public void exampleMethod() {
    	Random r = new Random();
    	for (int i = 0; i < 1000; i++) {
    		this.counter.increment();
    		try {
				Thread.sleep(r.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }

}
