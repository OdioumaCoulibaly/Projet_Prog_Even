package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.Gauge;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class PrometheusCons {

    public static void metrics() throws IOException {

        Consommateur consommateur = new Consommateur();

        // Créer un registre pour stocker les métriques Prometheus en dehors de la boucle
        PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

        // Créer une métrique de type Gauge pour le nombre de productions
        Gauge variableGauge = Gauge.build()
                .name("temperature_en_celcus")
                .help("Temperature en celcus")
                .register(registry.getPrometheusRegistry());

        // Créer une métrique de type Gauge pour tmp en Fahrenheit
        Gauge variableGaugeTemp = Gauge.build()
                .name("temperature_en_fahrenheit")
                .help("Temperature en Fahrenheit")
                .register(registry.getPrometheusRegistry());

        // Configurer le serveur HTTP pour exposer les métriques en dehors de la boucle
        int port = 8888;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/metricsprod", new MetricsHandler(registry));
        server.start();

        // Boucle infinie pour mettre à jour la valeur du Gauge
        while (true) {

            //producteur.incrementNbrProduction(variableGauge);
            double value = consommateur.getCelcus();
            variableGauge.set(value);

            double valueTemp = consommateur.getTemperature();
            variableGaugeTemp.set(valueTemp);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Classe MetricsHandler qui implémente l'interface HttpHandler
    static class MetricsHandler implements HttpHandler {
        private final PrometheusMeterRegistry registry;

        // Constructeur qui prend une instance de PrometheusMeterRegistry en paramètre
        public MetricsHandler(PrometheusMeterRegistry registry) {
            this.registry = registry;
        }

        // Méthode handle() qui gère les requêtes HTTP
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            // Obtenir les métriques sous forme de chaîne de caractères
            String response = registry.scrape();

            // Définir le type de contenu de la réponse comme texte brut au format Prometheus
            exchange.getResponseHeaders().set("Content-Type", "text/plain; version=0.0.4; charset=utf-8");

            // Envoyer la réponse HTTP avec le code de statut 200 (OK)
            // et la longueur de la réponse en octets
            exchange.sendResponseHeaders(200, response.getBytes().length);

            // Écrire la réponse dans le corps de la réponse HTTP
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
