package pl.piomin.graphite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class FirstService {

	// TODO - fixme
//    private final GaugeService gaugeService;

    @Autowired
//    public FirstService(GaugeService gaugeService) {
//        this.gaugeService = gaugeService;
//    }

    public void exampleMethod() {
    	Random r = new Random();
    	for (int i = 0; i < 1000; i++) {
//    		this.gaugeService.submit("firstservice", r.nextDouble()*100);
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
    }

}
