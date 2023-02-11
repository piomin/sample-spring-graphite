package pl.piomin.graphite.service;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class FirstService {

	private Gauge gauge;
	private double t = 0;
	Random r = new Random();

	public FirstService(MeterRegistry meterRegistry) {

		this.gauge = Gauge.builder("firstservice", () -> r())
				.tags("test")
				.register(meterRegistry);
	}

	public void exampleMethod() {
    	Random r = new Random();
    	for (int i = 0; i < 1000; i++) {
			r();
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }

	private double r() {
		return r.nextDouble();
	}

}
