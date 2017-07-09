package pl.piomin.graphite;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
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

//	@Bean
//	public MetricRegistry metricRegistry() {
//		final MetricRegistry metricRegistry = new MetricRegistry();
//
//		metricRegistry.register("jvm.memory", new MemoryUsageGaugeSet());
//		metricRegistry.register("jvm.thread-states", new ThreadStatesGaugeSet());
//		metricRegistry.register("jvm.garbage-collector", new GarbageCollectorMetricSet());
//
//		return metricRegistry;
//	}

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
//				String n = "testx2";
//				if (value.getName().startsWith("gauge"))
//					n = "testx1";
				Point point = Point.measurement(value.getName()).time(value.getTimestamp().getTime(), TimeUnit.MILLISECONDS)
						.addField("value", value.getValue()).build();
				influxDB.write(point);
				logger.info("write(" + value.getName() + "): " + value.getValue());
				// influxDB.flush();
//				int size = influxDB.query(new Query("SELECT value FROM " + n, dbName)).getResults().size();
				// logger.info("size: " + size);
			}
		};
	}
}
