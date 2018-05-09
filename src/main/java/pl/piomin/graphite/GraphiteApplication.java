package pl.piomin.graphite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GraphiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphiteApplication.class, args);
	}
	
//	@Bean
//	public MetricsEndpointMetricReader metricsEndpointMetricReader(final MetricsEndpoint metricsEndpoint) {
//		return new MetricsEndpointMetricReader(metricsEndpoint);
//	}

//	@Bean
//	@ExportMetricWriter
//	GaugeWriter influxMetricsWriter() {
//		InfluxDB influxDB = InfluxDBFactory.connect("http://192.168.99.100:8086", "root", "root");
//		String dbName = "grafana";
//		influxDB.setDatabase(dbName);
//		influxDB.setRetentionPolicy("one_day");
//		influxDB.enableBatch(10, 1000, TimeUnit.MILLISECONDS);
//
//		return new GaugeWriter() {
//
//			@Override
//			public void set(Metric<?> value) {
//				Point point = Point.measurement(value.getName()).time(value.getTimestamp().getTime(), TimeUnit.MILLISECONDS)
//						.addField("value", value.getValue()).build();
//				influxDB.write(point);
//				logger.info("write(" + value.getName() + "): " + value.getValue());
//			}
//		};
//	}
	
}
