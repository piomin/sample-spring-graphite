package pl.piomin.graphite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class GraphiteApplication {

	private static final Logger logger = Logger.getLogger("GraphiteApplication");

	public static void main(String[] args) {
		SpringApplication.run(GraphiteApplication.class, args);
	}

}
