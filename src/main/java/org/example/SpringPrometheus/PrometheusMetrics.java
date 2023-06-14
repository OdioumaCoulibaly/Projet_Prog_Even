package org.example.SpringPrometheus;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.Counter;
import org.springframework.context.annotation.Bean;
/*
public static class PrometheusMetrics {

    @Bean
    PrometheusMeterRegistry prometheusMeterRegistry() {
        return new PrometheusMeterRegistry();
    }

    // Compteurs

    @Bean
    Counter recordsCounter() {
        return Counter
                .builder("records_consumed_total")
                .description("Nombre total d'enregistrements consommés")
                .register(prometheusMeterRegistry());
    }

    @Bean
    Counter recordsProducedCounter() {
        return Counter
                .builder("records_produced_total")
                .description("Nombre total d'enregistrements produits")
                .register(prometheusMeterRegistry());
    }
}
*/
