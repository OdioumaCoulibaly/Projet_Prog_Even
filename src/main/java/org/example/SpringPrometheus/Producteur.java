package org.example.SpringPrometheus;


import ch.qos.logback.core.net.SyslogOutputStream;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.Counter;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;
import java.util.Random;


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
            while (true) {
                // Génération d'une température aléatoire en Celsius
                double temperatureCelsius = random.nextDouble() * 100;

                // Création d'un enregistrement de producteur avec la température en Celsius comme clé et valeur
                ProducerRecord<String, Double> record = new ProducerRecord<>(C_TO_F_TOPIC, Double.toString(temperatureCelsius), temperatureCelsius);
                // Envoi de l'enregistrement au topic approprié
                producer.send(record);

                incrementNbrProduction();

                if(startTime<System.currentTimeMillis()){

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