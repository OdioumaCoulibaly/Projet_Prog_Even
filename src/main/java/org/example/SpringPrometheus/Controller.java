package org.example.SpringPrometheus;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
@RestController
public class Controller {
    Producteur producteur = new Producteur();

//    @GetMapping("/metrics")
    public String sendMetrics() {
        // Code pour envoyer les métriques à Prometheus
        Integer cpt = producteur.getNbrProduction();

        // Récupérer le registre de métriques de Micrometer
        PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

        // Créer une métrique de type Gauge pour le nombre de productions
        Gauge.builder("nombre de production", () -> cpt.doubleValue())
                .description("Nombre total de productions")
                .register(registry);

        // Retourner les métriques sous forme de texte Prometheus
        return registry.scrape();
    }

}
*/