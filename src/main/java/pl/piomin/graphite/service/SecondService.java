package pl.piomin.graphite.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SecondService {

// TODO - fixme
//    private final CounterService counterService;

//    public SecondService(CounterService counterService) {
//        this.counterService = counterService;
//    }

    public void exampleMethod() {
    	Random r = new Random();
    	for (int i = 0; i < 1000; i++) {
//    		this.counterService.increment("hello3");
    		try {
				Thread.sleep(r.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }

}
