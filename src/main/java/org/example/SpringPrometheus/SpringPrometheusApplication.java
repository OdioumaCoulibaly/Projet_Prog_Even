package org.example.SpringPrometheus;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
@SpringBootApplication
public class SpringPrometheusApplication {

	static Producteur producteur = new Producteur();

	public static String sendMetrics() {
		// Code pour envoyer les métriques à Prometheus
		Integer cpt = producteur.getNbrProduction();


		// Créer une métrique de type Gauge pour le nombre de productions
		Gauge.builder("nombre de production", () -> cpt.doubleValue())
				.description("Nombre total de productions")
				.register(prometheusMeterRegistry());

		// Retourner les métriques sous forme de texte Prometheus
		return prometheusMeterRegistry().scrape();
	}

	public static void main(String[] args) {

		SpringApplication.run(SpringPrometheusApplication.class, args);

		// Point d'entrée principal du programme
		Producteur.startProcteurs();
		Consommateur.startConsumers();
		sendMetrics();
	}


	@Bean
	public static PrometheusMeterRegistry prometheusMeterRegistry() {
		return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
	}

}
 */


@SpringBootApplication
public class SpringPrometheusApplication {

	static Producteur producteur = new Producteur();

	public static String sendMetrics() {
		// Code pour envoyer les métriques à Prometheus
		Integer cpt = producteur.getNbrProduction();

		// Créer une métrique de type Gauge pour le nombre de productions
		PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
		Gauge.builder("nombre_de_production", () -> cpt.doubleValue())
				.description("Nombre total de productions")
				.register(registry);

		// Retourner les métriques sous forme de texte Prometheus
		return registry.scrape();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringPrometheusApplication.class, args);

		// Point d'entrée principal du programme
		Producteur.startProcteurs();
		Consommateur.startConsumers();
		sendMetrics();
	}

	@Bean
	public PrometheusMeterRegistry prometheusMeterRegistry() {
		return new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
	}
}
