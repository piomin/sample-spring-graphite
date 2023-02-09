package pl.piomin.graphite.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Service;

@Service
public class FirstService {

    private final GaugeService gaugeService;

    @Autowired
    public FirstService(GaugeService gaugeService) {
        this.gaugeService = gaugeService;
    }

    public void exampleMethod() {
    	Random r = new Random();
    	for (int i = 0; i < 1000; i++) {
    		this.gaugeService.submit("firstservice", r.nextDouble()*100);
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }

}
