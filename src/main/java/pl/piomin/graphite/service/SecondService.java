package pl.piomin.graphite.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;

@Service
public class SecondService {

    private final CounterService counterService;

    @Autowired
    public SecondService(CounterService counterService) {
        this.counterService = counterService;
    }

    public void exampleMethod() {
    	Random r = new Random();
    	for (int i = 0; i < 1000000; i++) {
    		this.counterService.increment("hello3");
    		try {
				Thread.sleep(r.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }

}
