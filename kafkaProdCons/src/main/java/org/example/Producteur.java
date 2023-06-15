package org.example;

import com.sun.net.httpserver.HttpServer;
import io.micrometer.core.instrument.Counter;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.Gauge;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.Random;

/*
public class Producteur {

    private static int nbrProduction = 0;
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String C_TO_F_TOPIC = "Temperature-Celsius";

    public Producteur(){

    }


    public int getNbrProduction() {
        return nbrProduction;
    }

    public static void setNbrProduction(int nbrProductionParams) {
        nbrProduction = nbrProductionParams;
    }

    public static void incrementNbrProduction(){
        nbrProduction++;
    }


    private static long  startTime = System.currentTimeMillis() + 5000; // Enregistrer le temps de début

    // Votre script Java à exécuter ici

    private static long endTime,executionTime; // Enregistrer le temps de fin



    // Méthode pour démarrer les producteurs de température Celsius

    public static void startProcteurs() {
        // Producteur 1
        Thread producer1Thread = new Thread(() -> {
            // Configuration des propriétés du producteur
            Properties props = new Properties();
            props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.DoubleSerializer");

            // Création d'un producteur Kafka avec les propriétés configurées
            KafkaProducer<String, Double> producer = new KafkaProducer<>(props);

            Random random = new Random();
            Prometheus prometheus = new Prometheus();

            while (true) {
                // Génération d'une température aléatoire en Celsius
                double temperatureCelsius = random.nextDouble() * 100;

                // Création d'un enregistrement de producteur avec la température en Celsius comme clé et valeur
                ProducerRecord<String, Double> record = new ProducerRecord<>(C_TO_F_TOPIC, Double.toString(temperatureCelsius), temperatureCelsius);
                // Envoi de l'enregistrement au topic approprié
                producer.send(record);


                incrementNbrProduction();

                if(startTime<System.currentTimeMillis()){

                    try {
                        prometheus.metrics();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    setNbrProduction(0);
                    startTime = System.currentTimeMillis() + 5000;  // Enregistrer le temps de début
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Producteur 2 (similaire au producteur 1)
        Thread producer2Thread = new Thread(() -> {
            Properties props = new Properties();
            props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.DoubleSerializer");

            KafkaProducer<String, Double> producer = new KafkaProducer<>(props);

            Random random = new Random();
            while (true) {
                double temperatureCelsius = random.nextDouble() * 100;
                ProducerRecord<String, Double> record = new ProducerRecord<>(C_TO_F_TOPIC, Double.toString(temperatureCelsius), temperatureCelsius);
                producer.send(record);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Démarrage des threads des producteurs
        producer1Thread.start();
        producer2Thread.start();
    }


}

 */

public class Producteur {

    private int nbrProduction = 0;
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String C_TO_F_TOPIC = "Temperature-Celsius";

    private long startTime = System.currentTimeMillis() + 5000;
    private long endTime, executionTime;

    public int getNbrProduction() {
        return nbrProduction;
    }


    public void startProcteurs() {
        // Producteur 1
        Thread producer1Thread = new Thread(() -> {
            // Configuration des propriétés du producteur
            Properties props = new Properties();
            props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.DoubleSerializer");

            // Création d'un producteur Kafka avec les propriétés configurées
            KafkaProducer<String, Double> producer = new KafkaProducer<>(props);

            Random random = new Random();

            while (true) {
                // Génération d'une température aléatoire en Celsius
                double temperatureCelsius = random.nextDouble() * 100;

                // Création d'un enregistrement de producteur avec la température en Celsius comme clé et valeur
                ProducerRecord<String, Double> record = new ProducerRecord<>(C_TO_F_TOPIC, Double.toString(temperatureCelsius), temperatureCelsius);
                // Envoi de l'enregistrement au topic approprié
                producer.send(record);

              // incrementNbrProduction(variableGauge);
/*
                if (startTime < System.currentTimeMillis()) {
                    try {
                        Prometheus.metrics();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    setNbrProduction(0);
                    startTime = System.currentTimeMillis() + 5000;
                }
*/
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Producteur 2 (similaire au producteur 1)
        Thread producer2Thread = new Thread(() -> {
            Properties props = new Properties();
            props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.DoubleSerializer");

            KafkaProducer<String, Double> producer = new KafkaProducer<>(props);

            Random random = new Random();
            while (true) {
                double temperatureCelsius = random.nextDouble() * 100;
                ProducerRecord<String, Double> record = new ProducerRecord<>(C_TO_F_TOPIC, Double.toString(temperatureCelsius), temperatureCelsius);
                producer.send(record);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Démarrage des threads des producteurs
        producer1Thread.start();
        producer2Thread.start();
    }

    private synchronized void setNbrProduction(int nbrProductionParams) {
        nbrProduction = nbrProductionParams;
    }
    synchronized void incrementNbrProduction(Gauge variableGauge) {
        nbrProduction++;
        variableGauge.set(nbrProduction);

        if (startTime < System.currentTimeMillis()) {
            setNbrProduction(0);
            startTime = System.currentTimeMillis() + 5000;
        }
    }

}
