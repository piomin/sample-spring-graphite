package pl.piomin.graphite;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.endpoint.MetricsEndpoint;
import org.springframework.boot.actuate.endpoint.MetricsEndpointMetricReader;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.actuate.metrics.writer.GaugeWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GraphiteApplication {

	private static final Logger logger = Logger.getLogger("GraphiteApplication");

	public static void main(String[] args) {
		SpringApplication.run(GraphiteApplication.class, args);
	}
	
	@Bean
	public MetricsEndpointMetricReader metricsEndpointMetricReader(final MetricsEndpoint metricsEndpoint) {
		return new MetricsEndpointMetricReader(metricsEndpoint);
	}

	@Bean
	@ExportMetricWriter
	GaugeWriter influxMetricsWriter() {
		InfluxDB influxDB = InfluxDBFactory.connect("http://192.168.99.100:8086", "root", "root");
		String dbName = "grafana";
		influxDB.setDatabase(dbName);
		influxDB.setRetentionPolicy("one_day");
		influxDB.enableBatch(10, 1000, TimeUnit.MILLISECONDS);

		return new GaugeWriter() {

			@Override
			public void set(Metric<?> value) {
				Point point = Point.measurement(value.getName()).time(value.getTimestamp().getTime(), TimeUnit.MILLISECONDS)
						.addField("value", value.getValue()).build();
				influxDB.write(point);
				logger.info("write(" + value.getName() + "): " + value.getValue());
			}
		};
	}
}
