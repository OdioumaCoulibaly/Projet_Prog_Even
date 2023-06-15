package org.example;

import io.micrometer.core.instrument.Counter;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/*
public class PrometheusJavaApplication {

    static Producteur producteur = new Producteur();

    public static void main(String[] args) throws IOException {
        // Créer un registre pour stocker les métriques Prometheus
        PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

        // Créer une métrique de type Counter pour le nombre de productions
        Counter nombreDeProductionCounter = Counter.builder("nombre_de_production")
                .description("Nombre total de productions")
                .register(registry);

        // Point d'entrée principal du programme
        Producteur.startProcteurs();
        Consommateur.startConsumers();

        // Incrémenter le compteur de productions
        int cpt = producteur.getNbrProduction();
        nombreDeProductionCounter.increment(cpt);

        // Configurer le serveur HTTP pour exposer les métriques
        int port = 8888;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/metrics", new MetricsHandler(registry));
        server.start();
    }

    static class MetricsHandler implements HttpHandler {
        private final PrometheusMeterRegistry registry;

        public MetricsHandler(PrometheusMeterRegistry registry) {
            this.registry = registry;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = registry.scrape();
            exchange.getResponseHeaders().set("Content-Type", "text/plain; version=0.0.4; charset=utf-8");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}

 */

public class PrometheusJavaApplication {

    static Producteur producteur = new Producteur();

    public static void main(String[] args) throws IOException {
        // Créer un registre pour stocker les métriques Prometheus
        PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

        // Créer une métrique de type Counter pour le nombre de productions
        Counter nombreDeProductionCounter = Counter.builder("nombre_de_production_total")
                .description("Nombre total de productions")
                .register(registry);

        // Point d'entrée principal du programme
        Producteur.startProcteurs();
        Consommateur.startConsumers();

        // Incrémenter le compteur de productions
        int cpt = producteur.getNbrProduction();
        nombreDeProductionCounter.increment(42);

        // Configurer le serveur HTTP pour exposer les métriques
        int port = 8888;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/metrics", new MetricsHandler(registry));
        server.start();
    }

    static class MetricsHandler implements HttpHandler {
        private final PrometheusMeterRegistry registry;

        public MetricsHandler(PrometheusMeterRegistry registry) {
            this.registry = registry;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = registry.scrape();
            exchange.getResponseHeaders().set("Content-Type", "text/plain; version=0.0.4; charset=utf-8");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
